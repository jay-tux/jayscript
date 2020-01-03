import java.util.*;

public class ArgCall extends Call 
{
	public ArgCall(List<String> args)
	{
		super(args);
	}
	
	public  void execute(State program)
	{
		if(args.size() < 1)
		{
			throw new JayInterpreterException("Syntax error: `argcall` expects at least 1 argument, " + args.size() + " given.");
		}
		
		Routine r = program.getRoutine(args.get(0));
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
			args.remove(0);
			f.invoke(program, args);
		}
	}
}
