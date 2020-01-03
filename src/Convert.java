import java.util.*;

public class Convert extends Command
{
	public Convert(List<String> args)
	{
		super(args);
	}
	
	public void execute(State program)
	{
		if(args.size() != 4)
		{
			throw new JayInterpreterException("Argument error: `convert` expects 4 arguments, " +
					args.size() + " given.");
		}
		else if(!Variable.isBuiltin(args.get(0)))
		{
			throw new JayInterpreterException("Argument error: expected builtin type, got " + args.get(0) + ".");
		}
		else if(!Variable.isBuiltin(args.get(1)))
		{
			throw new JayInterpreterException("Argument error: expected builtin type, got " + args.get(1) + ".");
		}
		else if(!program.varType(args.get(2)).equals(args.get(0))) 
		{
			throw new JayInterpreterException("Type error: expected __" + args.get(0) + "__, got __" + program.varType(args.get(2)) + "__.");
		}
		else if(!program.varType(args.get(3)).equals(args.get(1))) 
		{
			throw new JayInterpreterException("Type error: expected __" + args.get(1) + "__, got __" + program.varType(args.get(3)) + "__.");
		}
		
		try 
		{
			switch(args.get(0)) 
			{
				case "int":
					fromInt(program);
					break;
					
				case "char":
					fromChar(program);
					break;
					
				case "float":
					fromFloat(program);
					break;
					
				case "string":
					fromString(program);
					break;
			}
		}
		catch(Exception ex)
		{
			throw new JayInterpreterException("Type error: conversion failed.");
		}
	}
	
	public static void fromIf(State program, String to, String in)
	{
		State substate = State.defaultState();
		ArrayList<String> commands = new ArrayList<>();
		String from = program.varType(in);
		commands.add("declare " + from + " var1");
		commands.add("declare " + to + " var2");
		commands.add("set var1 " + program.getVar(in));
		commands.add("convert " + from + " " + to + " var1 var2");
		commands.add("exit");
		substate.enableRethrow();
		substate.setParent(program);
		substate.giveLines(commands);
		substate.runSingle();
	}
	
	private void fromInt(State program) 
	{
		switch(args.get(1))
		{
			case "int":
				program.setVar(args.get(3), program.getVar(args.get(2)));
				break;
				
			case "char":
				char c = (char)Integer.parseInt(program.getVar(args.get(2)).toString());
				program.setVar(args.get(3), c);
				break;
				
			case "float":
				float f = Float.parseFloat(program.getVar(args.get(2)).toString());
				program.setVar(args.get(3), f);
				break;
				
			case "string":
				program.setVar(args.get(3), program.getVar(args.get(2)).toString());
				break;
		}
	}
	
	private void fromChar(State program) 
	{
		switch(args.get(1))
		{
			case "int":
				int i = Integer.parseInt(program.getVar(args.get(2)).toString());
				program.setVar(args.get(3), i);
				break;
				
			case "char":
				program.setVar(args.get(3), program.getVar(args.get(2)));
				break;
				
			case "float":
				float f = Float.parseFloat(program.getVar(args.get(2)).toString());
				program.setVar(args.get(3), f);
				break;
				
			case "string":
				program.setVar(args.get(3), program.getVar(args.get(2)).toString());
				break;
		}
	}
	
	private void fromFloat(State program) 
	{
		switch(args.get(1))
		{
			case "int":
				int i = Integer.parseInt(program.getVar(args.get(2)).toString());
				program.setVar(args.get(3), i);
				break;
				
			case "char":
				char c = (char)Integer.parseInt(program.getVar(args.get(2)).toString());
				program.setVar(args.get(3), c);
				break;
				
			case "float":
				program.setVar(args.get(3), program.getVar(args.get(2)));
				break;
				
			case "string":
				program.setVar(args.get(3), program.getVar(args.get(2)).toString());
				break;
		}
	}
	
	private void fromString(State program) 
	{
		switch(args.get(1))
		{
			case "int":
				int i = Integer.parseInt(program.getVar(args.get(2)).toString());
				program.setVar(args.get(3), i);
				break;
				
			case "char":
				char c = (char)Integer.parseInt(program.getVar(args.get(2)).toString());
				program.setVar(args.get(3), c);
				break;
				
			case "float":
				float f = Float.parseFloat(program.getVar(args.get(2)).toString());
				program.setVar(args.get(3), f);
				break;
				
			case "string":
				program.setVar(args.get(3), program.getVar(args.get(2)));
				break;
		}
	}
}
