import java.util.*;

public class If extends Command
{
	public If(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 3)
		{
			throw new JayInterpreterException("Argument error: `if` expects 3 arguments, " +
					args.size() + " given.");
		}
		
		if(program.varType(args.get(0)).equals(program.varType(args.get(1))))
		{
			if(!program.getVar(args.get(0)).toString().equals(program.getVar(args.get(1)).toString()))
			{
				return;
			}
			if(program.flagSet(args.get(2))) 
			{
				program.setLineCounter(program.getFlagAddress(args.get(2)));
				return;
			}
			
			try
			{
				program.setLineCounter(Integer.parseInt(program.getVar(args.get(2)).toString()) - 1);
			}
			catch(Exception e)
			{
				throw new JayInterpreterException("Type error: expected __int__, not '" + args.get(2) + "'.");
			}
		}
		else
		{
			throw new JayInterpreterException("Type error: mismatching types (got " + 
					program.varType(args.get(0)) + " and " + program.varType(args.get(1)) + ").");
		}
	}

}
