import java.util.*;

public class Dump extends DebugCommand
{
	public Dump(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(!program.optionValue("debug"))
		{
			throw new JayInterpreterException("Syntax error: command __debug::dump__ only accesible from within debug environment.");
		}
		else
		{
			System.out.println("Dumping at line " + program.getLineCounter() + ": ");
			System.out.println("  Enabled options:");
			System.out.println("    " + String.join("\n    ", getEnabled(program.getOpts())));
			System.out.println("  Flags set:");
			System.out.println("    " + String.join("\n    ", collapseFlags(program.getFlags())));
			System.out.println("  Variables used:");
			System.out.println("    " + String.join("\n    ", collapseVars(program.getVars())));
		}
	}
	
	private static ArrayList<String> collapseVars(HashMap<String, Variable> in)
	{
		ArrayList<String> ret = new ArrayList<String>();
		for(Map.Entry<String, Variable> entr : in.entrySet())
		{
			ret.add(entr.getValue().getType() + " " + entr.getKey() + ": " + entr.getValue().getValue().toString());
		}
		return ret;
	}
	
	private static ArrayList<String> collapseFlags(HashMap<String, Integer> in)
	{
		ArrayList<String> ret = new ArrayList<String>();
		for(Map.Entry<String, Integer> entr : in.entrySet())
		{
			ret.add(entr.getKey() + ": " + entr.getValue().toString());
		}
		return ret;
	}
	
	private static ArrayList<String> getEnabled(HashMap<String, Boolean> in)
	{
		ArrayList<String> ret = new ArrayList<String>();
		for(Map.Entry<String, Boolean> entr : in.entrySet())
		{
			if(entr.getValue())
			{
				ret.add(entr.getKey());
			}
		}
		return ret;
	}
}
