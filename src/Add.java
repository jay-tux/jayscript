import java.util.*;

public class Add extends Command
{
	public Add(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 3)
		{
			throw new JayInterpreterException("Argument error: `print` expects 3 arguments, " +
					args.size() + " given.");
		}
		
		if(program.varType(args.get(0)).equals("int"))
		{
			if(program.varType(args.get(1)).equals("int") && program.varType(args.get(2)).equals("int"))
			{
				try
				{
					int i1 = Integer.parseInt(program.getVar(args.get(1)).toString());
					int i2 = Integer.parseInt(program.getVar(args.get(2)).toString());
					program.setVar(args.get(0), i1 + i2);
				}
				catch(Exception e)
				{
					throw new JayInterpreterException("Variable error: can't add " + program.getVar(args.get(1)) + " and " + 
							program.getVar(args.get(2)) + " (expected __int__ and __int__).");
				}
			}
			else
			{
				throw new JayInterpreterException("Type error: expected __int__ and __int__, not '" + args.get(0) + "' and '" + args.get(1) + "'.");
			}
		}
		else if(program.varType(args.get(0)).equals("float"))
		{
			if(program.varType(args.get(1)).equals("float") && program.varType(args.get(2)).equals("float")) 
			{
				float f1 = Float.parseFloat(program.getVar(args.get(1)).toString());
				float f2 = Float.parseFloat(program.getVar(args.get(2)).toString());
				program.setVar(args.get(0), f1 + f2);
			}
			else
			{
				throw new JayInterpreterException("Type error: expected __float__ and __float__, not '" + args.get(0) + "' and '" + args.get(1) + "'.");
			}
		}
		else
		{
			throw new JayInterpreterException("Type error: expected __int__, not '" + args.get(0) + "'.");
		}
		
	}
}
