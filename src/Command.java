import java.util.*;

public abstract class Command 
{
	protected ArrayList<String> args;
	
	protected Command(ArrayList<String> args)
	{
		this.args = args;
	}
	
	public abstract void execute(State program);
}