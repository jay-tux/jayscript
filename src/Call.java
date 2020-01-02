import java.util.*;

public class Call extends Command
{
	public Call(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 1)
		{
			throw new JayInterpreterException("Syntax error: `call` expects 1 argument, " + args.size() + " given.");
		}
		
		Routine r = program.getRoutine(args.get(0));
		if(r == null)
		{
			throw new JayInterpreterException("Name error: routine '" + args.get(0) + "' unknown.");
		}
		else
		{
			r.invoke(program);
		}
	}
}
