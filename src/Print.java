import java.util.*;

public class Print extends Command
{
	public Print(ArrayList<String> args)
	{
		super(args);
	}

	public void execute(State program)
	{
		for(String s : args)
		{
			if(s.startsWith("&"))
			{
				System.out.print(program.getVar(s.substring(1)));
			}
			else
			{
				System.out.print(s + " ");
			}
		}
	}
}
