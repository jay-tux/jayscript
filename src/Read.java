import java.util.*;

public class Read extends Command
{
	public static Scanner lineFeed;
	
	static {
		lineFeed = new Scanner(System.in);
	}
	
	public Read(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 1)
		{
			throw new JayInterpreterException("Argument error: `read` expects 1 argument, " +
					args.size() + " given.");
		}
		program.setVar(args.get(0), lineFeed.nextLine());
	}
}
