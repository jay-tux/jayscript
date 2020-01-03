import java.util.*;

public abstract class Command 
{
	protected List<String> args;
	
	protected Command(List<String> args)
	{
		this.args = args;
	}
	
	public abstract void execute(State program);
}