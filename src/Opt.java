import java.util.*;

public class Opt extends Command
{
	public Opt(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 1)
		{
			throw new JayInterpreterException("Argument error: `opt` expects 1 argument " + 
					args.size() + " given.");
		}
		program.enableOption(args.get(0));
	}
}
