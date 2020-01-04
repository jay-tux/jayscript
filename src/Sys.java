import java.util.*;

public class Sys extends Command
{
	private static List<String> packages;
	private static Map<String, Boolean> imported;
	private static JayInterpreterException notImported;
	static {
		packages = new ArrayList<>();
		imported = new HashMap<>();
		notImported = new JayInterpreterException("Package error: this command requires a package to be imported.");
		packages.add("math");
		packages.add("io");
		packages.add("coll");
		imported.put("math", false);
		imported.put("io", false);
		imported.put("coll", false);
	}
	
	public Sys(List<String> args) 
	{
		super(args);
	}
	
	public static JayInterpreterException getNotImported()
	{
		return notImported;
	}
	
	public static boolean isPackage(String pkg)
	{
		return packages.contains(pkg);
	}
	
	public static boolean isImported(String pkg)
	{
		if(!isPackage(pkg))
		{
			throw new JayInterpreterException("Name error: package \"" + pkg + "\" doesn't exist.");
		}
		
		return imported.get(pkg);
	}
	
	public void execute(State program)
	{
		if(args.size() != 1)
		{
			throw new JayInterpreterException("Syntax error: `system` expects 1 argument, " + args.size() + " given.");
		}
		
		if(!isPackage(args.get(0)))
		{
			throw new JayInterpreterException("Name error: package \"" + args.get(0) + "\" doesn't exist.");
		}
		
		if(imported.get(args.get(0))) 
		{
			throw new JayInterpreterException("Syntax error: package already imported.");
		}
		
		imported.replace(args.get(0), true);
	}
}
