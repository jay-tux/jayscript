import java.util.*;

public class Jump extends Command
{
	public Jump(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 1)
		{
			throw new JayInterpreterException("Argument error: `jump` expects 1 argument, " +
					args.size() + " given.");
		}
		
		if(program.flagSet(args.get(0))) 
		{
			program.setLineCounter(program.getFlagAddress(args.get(0)));
			return;
		}
		
		try
		{
			program.setLineCounter(Integer.parseInt(program.getVar(args.get(0)).toString()) - 1);
		}
		catch(Exception e)
		{
			throw new JayInterpreterException("Type error: expected __int__, not '" + args.get(0) + "'.");
		}
	}
}
