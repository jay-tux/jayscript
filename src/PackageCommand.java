import java.util.*;
public abstract class PackageCommand extends Command
{
	protected String mode;
	
	public PackageCommand(List<String> args, String mode)
	{
		super(args);
		this.mode = mode;
	}
}
