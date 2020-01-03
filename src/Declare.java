import java.util.*;

public class Declare extends Command
{
	public Declare(List<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 2)
		{
			throw new JayInterpreterException("Argument error: `declare` expects 2 argument " + 
					args.size() + " given.");
		}
		program.declare(args.get(0), args.get(1));
	}
}