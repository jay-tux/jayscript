import java.util.*;

public class Function extends Routine
{
	private ArrayList<String> argtypes;
	private ArrayList<String> argnames;
	private State context;
	private String returnType;
	
	public Function(ArrayList<String> args)
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
		boolean reachedEnd = false;
		while(!reachedEnd)
		{
			String nextline = program.currenLine();
			if(nextline.equals("end " + routineName)) 
			{
				commands.add("exit");
				reachedEnd = true;
			}
			else
			{
				commands.add(nextline);
			}
			program.incrementLineCounter();
			//System.out.println(commands);
		}
		program.decrementLineCounter();
		program.addRoutine(routineName, this);
	}

	public void invoke(State program, ArrayList<String> defArgs)
	{
		if(defArgs.size() != argnames.size())
		{
			throw new JayInterpreterException("Syntax error: function " + routineName + " expects " + argnames.size() + 
					" parameters, " + defArgs.size() + "given.");
		}
		
		State substate = State.defaultState();
		context = substate;
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
		substate.giveLines(commands);
		substate.runSingle();
	}
	
	public State invokeSave(State program, ArrayList<String> defArgs)
	{
		invoke(program, defArgs);
		return context;
	}
}
