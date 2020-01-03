import java.util.*;

public class Exit extends Command
{
	public Exit(List<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 0)
		{
			throw new JayInterpreterException("Argument error: `exit` expects 0 arguments, " +
					args.size() + " given.");
		}
		program.finish();
	}
}
