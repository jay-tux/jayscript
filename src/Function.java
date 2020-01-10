import java.util.*;

public class Function extends Routine
{
	private List<String> argtypes;
	private List<String> argnames;
	private Map<String, Integer> flags;
	private State context;
	private String returnType;
	
	public Function(List<String> args)
	{
		super(args);
	}
	
	public String getReturnType() { return returnType; }
	
	public void execute(State program)
	{
		//function <type> <name> {args ...}
		if(args.size() < 2)
		{
			throw new JayInterpreterException("Syntax error: `function` expects at least 2 arguments, " + args.size() + " given.");
		}
		
		routineName = args.get(1);
		returnType = args.get(0);
		argtypes = new ArrayList<>();
		argnames = new ArrayList<>();
		commands = new ArrayList<>();
		flags = new HashMap<String, Integer>();
		
		for(int i = 2; i < args.size(); i++)
		{
			if(!args.get(i).contains(":") || args.get(i).split(":").length != 2)
			{
				throw new JayInterpreterException("Syntax error: defined arguments have to be of the form <type>:<name>.");
			}
			else if(!Variable.isType(args.get(i).split(":")[0]))
			{
				throw new JayInterpreterException("Type error: type " + args.get(i).split(":")[0] + " is unknown.");
			}
			
			argtypes.add(args.get(i).split(":")[0]);
			argnames.add(args.get(i).split(":")[1]);
		}
		
		program.incrementLineCounter();
		int internalCounter = 0;
		boolean reachedEnd = false;
		while(!reachedEnd)
		{
			String nextline = program.currenLine();
			if(nextline.equals("end " + routineName)) 
			{
				commands.add("exit");
				reachedEnd = true;
			}
			else if(nextline.startsWith("@"))
			{
				commands.add(nextline);
				flags.put(nextline.trim().substring(1), internalCounter);
			}
			else
			{
				commands.add(nextline);
			}
			program.incrementLineCounter();
			//System.out.println(commands);
			internalCounter++;
		}
		program.decrementLineCounter();
		program.addRoutine(routineName, this);
	}

	public void invoke(State program, List<String> defArgs)
	{
		if(defArgs.size() != argnames.size())
		{
			throw new JayInterpreterException("Syntax error: function " + routineName + " expects " + argnames.size() + 
					" parameters, " + defArgs.size() + "given.");
		}
		
		State substate = State.defaultState();
		int counter = 0;
		for(String s : defArgs)
		{
			String vartype = program.varType(s);
			String argtype = argtypes.get(counter);
			if(!vartype.equals(argtype)) 
			{ 
				throw new JayInterpreterException("Type error: function " + routineName + " expects __" + argtype + "__ as " + counter +
						"th argument type, __" + vartype + "__ given.");
			}
			
			substate.declare(vartype, argnames.get(counter));
			program.deepCopy(s, argnames.get(counter), substate);
			
			counter++;
		}
		
		substate.setParent(program);
		for(String key : flags.keySet())
		{
			substate.setFlag(key, flags.get(key));
		}
		substate.giveLines(commands);
		substate.runSingle();
		context = substate;
	}
	
	public void invokeColl(State program, Object var, String type)
	{
		if(argnames.size() != 1) { throw new JayInterpreterException("Syntax error: iteration function requires exactly one argument."); }
		if(!argtypes.get(0).equals(type)) 
			{ throw new JayInterpreterException("Type error: iteration function for __" + argtypes.get(0) + "__, got " + type + "."); }
		State substate = State.defaultState();		
		substate.declare(argtypes.get(0), argnames.get(0));
		substate.setVar(argnames.get(0), var);
		for(String key : flags.keySet())
		{
			substate.setFlag(key, flags.get(key));
		}
		substate.giveLines(commands);
		substate.runSingle();
	}
	
	public State invokeSave(State program, List<String> defArgs)
	{
		invoke(program, defArgs);
		return context;
	}
}
