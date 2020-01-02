import java.util.*;

public class Return extends Command
{
	public Return(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 1)
		{
			throw new JayInterpreterException("Syntax error: `return` expects at least 1 argument, " + args.size() + " given.");
		}
		program.setReturn(args.get(0));
		program.finish();
	}
}
