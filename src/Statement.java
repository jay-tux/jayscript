import java.util.*;

public class Statement {
	private String command;
	private ArrayList<String> args;
	
	public static Statement fromLine(String l)
	{
		String line = l.trim();
		if(line.startsWith("~") || line.isBlank() || line.isEmpty() || line.startsWith("@") || line.startsWith("!"))
		{
			return null;
		}
		else
		{
			Statement s = new Statement();
			s.args = Arg.splitArgs(line);
			s.command = s.args.get(0);
			s.args.remove(0);
			return s;
		}
	}
	
	public void execute(State program)
	{
		//System.out.println("Current line: " + program.getLineCounter());
		//System.out.println("Trying to resolve command '" + command + "' with args '" + String.join(", ", args) + "'");
		if(command == null)
		{
			throw new JayInterpreterException("Command is empty.");
		}
		
		Command newC;
		switch(command)
		{
			case "declare":
				newC = new Declare(args);
				break;
			
			case "set":
				newC = new Set(args);
				break;
				
			case "print":
				newC = new Print(args);
				break;
				
			case "exit":
				newC = new Exit(args);
				break;
				
			case "jump":
				newC = new Jump(args);
				break;
				
			case "if":
				newC = new If(args);
				break;
				
			case "else":
				newC = new Else(args);
				break;
				
			case "add":
				newC = new Add(args);
				break;
				
			case "opt":
				newC = new Opt(args);
				break;
				
			case "not":
				newC = new Not(args);
				break;
				
			case "__debug::dump__":
				newC = new Dump(args);
				break;
				
			case "routine":
				newC = new Routine(args);
				break;
				
			case "function":
				newC = new Function(args);
				break;
				
			case "call":
				newC = new Call(args);
				break;
				
			case "argcall":
				newC = new ArgCall(args);
				break;
				
			case "type":
				newC = new Type(args);
				break;
				
			case "println":
				newC = new PrintLn(args);
				break;
				
			case "read":
				newC = new Read(args);
				break;
				
			case "convert":
				newC = new Convert(args);
				break;
				
			case "ifconv":
				newC = new IfConv(args);
				break;
				
			case "keep":
				newC = new Keep(args);
				break;
				
			case "return":
				newC = new Return(args);
				break;
				
			case "sys":
				newC = new Sys(args);
				break;
				
				/* PACKAGES */
					/* math */
			case "math_set":
				newC = new Math(args, "set");
				break;
				
			case "math_print":
				newC = new Math(args, "print");
				break;
				
			case "mathf_set":
				newC = new MathF(args, "set");
				break;
				
			case "mathf_print":
				newC = new MathF(args, "print");
				break;
			
			default:
				throw new JayInterpreterException("Syntax error: " + command + " is unknown.");
		}
		newC.execute(program);
		program.updateCommand(newC);
		program.incrementLineCounter();
	}
}
