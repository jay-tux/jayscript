import java.util.*;

public class IfConv extends If 
{
	public IfConv(List<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		try
		{
			Convert.fromIf(program, args.get(0), args.get(1));
			
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
		catch(JayInterpreterException e)
		{
			//System.out.println("Caught Interpreter error.");
			if(e.getMessage().equals("Type error: expected __int__, not '" + args.get(2) + "'."))
			{
				throw e;
			}
		}
	}
}
