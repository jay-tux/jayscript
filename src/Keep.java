import java.util.*;

public class Keep extends ArgCall
{
	public Keep(List<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() < 2)
		{
			throw new JayInterpreterException("Syntax error: `keep` expects at least 2 arguments, " + args.size() + " given.");
		}
		
		Routine r = program.getRoutine(args.get(1));
		if(r == null)
		{
			throw new JayInterpreterException("Name error: function '" + args.get(0) + "' unknown.");
		}
		else if(!(r instanceof Function))
		{
			throw new JayInterpreterException("Syntax error: `argcall` expects first argument to be a function, not a routine.");
		}
		else
		{
			Function f = (Function)r;
			args.remove(1);
			String returnname = args.get(0);
			args.remove(0);
			State s = f.invokeSave(program, args);
			if(!program.varType(returnname).equals(f.getReturnType()))
			{
				throw new JayInterpreterException("Type error: return type of function doesn't match variable type.");
			}
			s.deepCopy(s.getReturn(), returnname, program);
		}
	}
}
