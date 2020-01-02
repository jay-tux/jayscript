import java.util.*;

public class Else extends Command
{
	public Else(ArrayList<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(!(program.getPrevious() instanceof If))
		{
			throw new JayInterpreterException("Syntax error: else should be right after if.");
		}
		Statement s = Statement.fromLine(String.join(" ", args));
		s.execute(program);
		program.decrementLineCounter();
	}
}
