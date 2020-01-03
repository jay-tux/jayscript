import java.util.*;

public class Set extends Command
{
	public Set(List<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 2)
		{
			throw new JayInterpreterException("Argument error: `set` expects 2 arguments, " +
					args.size() + " given.");
		}
		program.setVar(args.get(0), args.get(1));
	}
}
