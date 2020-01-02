import java.util.*;

public class Routine extends Command 
{
	protected ArrayList<String> commands;
	protected String routineName;
	
	public Routine(ArrayList<String> args)
	{
		super(args);
		commands = new ArrayList<>();
	}
	
	public void execute(State program)
	{
		if(args.size() != 1)
		{
			throw new JayInterpreterException("Syntax error: `routine` expects 1 argument, " + args.size() + " given.");
		}
		
		routineName = args.get(0);
		program.incrementLineCounter();
		boolean reachedEnd = false;
		while(!reachedEnd)
		{
			String nextline = program.currenLine();
			if(nextline.equals("end " + routineName)) 
			{
				commands.add("exit");
				reachedEnd = true;
			}
			else
			{
				commands.add(nextline);
			}
			program.incrementLineCounter();
			//System.out.println(commands);
		}
		program.addRoutine(routineName, this);
	}
	
	public void invoke(State program)
	{
		State substate = State.defaultState();
		substate.setParent(program);
		substate.giveLines(commands);
		substate.runSingle();
	}
}
