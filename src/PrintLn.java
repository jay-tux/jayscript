import java.util.*;

public class PrintLn extends Print 
{
	public PrintLn(ArrayList<String> args) { super(args); }
	
	public void execute(State program)
	{
		super.execute(program);
		System.out.println();
	}
}
