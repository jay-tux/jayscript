import java.util.Arrays;
import java.util.List;

public class Coll extends PackageCommand
{
protected static String[] modes = { "create", "add", "prnt", "rm", "iter", "upd", "ins", "get" };
	
	public Coll(List<String> args, String mode)
	{
		super(args, mode);
		if(!Arrays.asList(modes).contains(mode)) {
			throw new JayInterpreterException("Syntax error: unknown coll mode.");
		}
		if(!Sys.isImported("coll")) { throw Sys.getNotImported(); }
	}
	
	public void execute(State program)
	{
		switch(mode)
		{
			case "create":
				if(args.size() != 2) 
				{ 
					throw new JayInterpreterException("Syntax error: `coll_create` requires exactly 2 arguments, " + args.size() + " given."); 
				}
				Collection.create(args.get(0), args.get(1), program);
				break;
				
			case "add":
				if(args.size() != 2)
				{
					throw new JayInterpreterException("Syntax error: `coll_add` requires exactly 2 arguments, " + args.size() + " given.");
				}
				if(program.varExists(args.get(0)))
				{
					if(program.getVar(args.get(0)) instanceof Collection)
					{
						if(program.varExists(args.get(1)))
						{
							((Collection)program.getVar(args.get(0))).add(program.getVar(args.get(1)));
						}
						else
						{
							((Collection)program.getVar(args.get(0))).add(args.get(1));
						}
					}
					else
					{
						throw new JayInterpreterException("Type error: expected __coll__, got __" + program.varType(args.get(0)) + "__.");
					}
				}
				else
				{
					throw new JayInterpreterException("Name error: variable " + args.get(0) + " doesn't exist.");
				}
				break;
				
			case "prnt":
				if(args.size() != 1)
				{
					throw new JayInterpreterException("Syntax error: `coll_prnt` requires exactly 1 argument, " + args.size() + " given.");
				}
				if(program.varExists(args.get(0)))
				{
					if(program.getVar(args.get(0)) instanceof Collection)
					{
						((Collection)program.getVar(args.get(0))).print();
					}
					else
					{
						throw new JayInterpreterException("Type error: expected __coll__, got __" + program.varType(args.get(0)) + "__.");
					}
				}
				else
				{
					throw new JayInterpreterException("Name error: variable " + args.get(0) + " doesn't exist.");
				}
				break;
				
			case "rm":
				if(args.size() != 2)
				{
					throw new JayInterpreterException("Syntax error: `coll_rm` requires exactly 2 arguments, " + args.size() + " given.");
				}
				if(program.varExists(args.get(0)))
				{
					if(program.getVar(args.get(0)) instanceof Collection)
					{
						int index = -1;
						if(program.varExists(args.get(1)))
						{
							if(program.varType(args.get(1)).equals("int"))
							{
								index = (int)program.getVar(args.get(1));
							}
							else
							{
								throw new JayInterpreterException("Type error: expected __int__, got __" + program.varType(args.get(1)) + "__.");
							}
						}
						else
						{
							try
							{
								index = Integer.parseInt(args.get(0));
							}
							catch(Exception e)
							{
								throw new JayInterpreterException("Type error: expected var or __int__.");
							}
						}
						((Collection)program.getVar(args.get(0))).remove(index);
					}
					else
					{
						throw new JayInterpreterException("Type error: expected __coll__, got __" + program.varType(args.get(0)) + "__.");
					}
				}
				else
				{
					throw new JayInterpreterException("Name error: variable " + args.get(0) + " doesn't exist.");
				}
				break;
		}
	}
}
