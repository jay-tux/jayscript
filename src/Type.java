import java.util.*;

public class Type extends Command
{
	public Type(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(!(args.size() >= 1))
		{
			throw new JayInterpreterException("Syntax error: `type` expects at least 1 argument, " + args.size() + " given.");
		}
		if(args.size() < 2)
		{
			throw new JayInterpreterException("Syntax error: can't define empty type.");
		}
		
		String typename = args.get(0);
		HashMap<String, String> fields = new HashMap<>();
		for(int i = 1; i < args.size(); i++)
		{
			if(!args.get(i).contains(":") || args.get(i).split(":").length != 2)
			{
				throw new JayInterpreterException("Syntax error: field definitions have to be of the form <type>:<name>.");
			}
			else if(!Variable.isType(args.get(i).split(":")[0]))
			{
				throw new JayInterpreterException("Type error: type " + args.get(i).split(":")[0] + " is unknown.");
			}
			
			fields.put(args.get(i).split(":")[1], args.get(i).split(":")[0]);
		}
		Variable.createType(typename, fields);
	}
}
