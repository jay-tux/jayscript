import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Coll extends PackageCommand
{
	protected static String[] modes = { "create", "add", "prnt", "rm", "iter", "upd", "ins", "get", "fromstr", "size" };
	
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
					if(program.getColl(args.get(0)) == null) { System.out.println("Is null"); }
					if(program.getColl(args.get(0)) instanceof Collection)
					{
						if(program.varExists(args.get(1)))
						{
							((Collection)program.getColl(args.get(0))).add(program.getVar(args.get(1)));
						}
						else
						{
							((Collection)program.getColl(args.get(0))).add(args.get(1));
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
					if(program.getColl(args.get(0)) instanceof Collection)
					{
						((Collection)program.getColl(args.get(0))).print();
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
					if(program.getColl(args.get(0)) instanceof Collection)
					{
						int index = -1;
						if(program.varExists(args.get(1)))
						{
							if(program.varType(args.get(1)).equals("int"))
							{
								index = Integer.parseInt(program.getVar(args.get(1)).toString());
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
								index = Integer.parseInt(args.get(1));
							}
							catch(Exception e)
							{
								throw new JayInterpreterException("Type error: expected var or __int__.");
							}
						}
						((Collection)program.getColl(args.get(0))).remove(index);
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
				
			case "upd":
				if(args.size() != 3)
				{
					throw new JayInterpreterException("Syntax error: `coll_upd` requires exactly 3 arguments, " + args.size() + " given.");
				}
				if(program.varExists(args.get(0)))
				{
					if(program.getColl(args.get(0)) instanceof Collection)
					{
						int index = -1;
						if(program.varExists(args.get(1)))
						{
							if(program.varType(args.get(1)).equals("int"))
							{
								index = Integer.parseInt(program.getVar(args.get(1)).toString());
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
								index = Integer.parseInt(args.get(1));
							}
							catch(Exception e)
							{
								throw new JayInterpreterException("Type error: expected var or __int__.");
							}
						}
						
						if(program.varExists(args.get(2)))
						{
							((Collection)program.getColl(args.get(0))).update(index, program.getVar(args.get(2)));
						}
						else
						{
							((Collection)program.getColl(args.get(0))).update(index, args.get(2));
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
				
			case "iter":
				if(args.size() != 2)
				{
					throw new JayInterpreterException("Syntax error: `coll_iter` requires exactly 2 arguments, " + args.size() + " given.");
				}
				if(program.varExists(args.get(0)))
				{
					if(program.getColl(args.get(0)) instanceof Collection)
					{
						((Collection)program.getColl(args.get(0))).iterate(args.get(1), program);
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
				
			case "get":
				if(args.size() != 3)
				{
					throw new JayInterpreterException("Syntax error: `coll_get` requires exactly 3 arguments, " + args.size() + " given.");
				}
				if(!program.varExists(args.get(2))) 
				{
					throw new JayInterpreterException("Name error: variable " + args.get(2) + " doesn't exist.");
				}
				if(program.varExists(args.get(0)))
				{
					if(program.getColl(args.get(0)) instanceof Collection)
					{
						int index = -1;
						if(program.varExists(args.get(1)))
						{
							if(program.varType(args.get(1)).equals("int"))
							{
								index = Integer.parseInt(program.getVar(args.get(1)).toString());
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
								index = Integer.parseInt(args.get(1));
							}
							catch(Exception e)
							{
								throw new JayInterpreterException("Type error: expected var or __int__.");
							}
						}
						if(!((Collection)program.getColl(args.get(0))).getContentType().equals(program.varType(args.get(2))))
						{
							throw new JayInterpreterException("Type error: type mismatch between collection type and variable type.");
						}
						((Collection)program.getColl(args.get(0))).deepCopyValToVar(args.get(2), index, program);
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
				
			case "fromstr":
				if(args.size() != 2)
				{
					throw new JayInterpreterException("Syntax error: `coll_fromstr` requires exactly 2 arguments, " + args.size() + " given.");
				}
				ArrayList<String> args1 = new ArrayList<String>();
				args1.add("char");
				args1.add(args.get(0));
				Coll c = new Coll(args1, "create");
				c.execute(program);
				
				if(program.varExists(args.get(1)))
				{
					if(program.varType(args.get(1)).equals("string"))
					{
						Collection cl = (Collection)program.getColl(args.get(0));
						for(char ch : program.getVar(args.get(1)).toString().toCharArray())
						{
							cl.add(ch);
						}
					}
					else
					{
						throw new JayInterpreterException("Type error: expected __string__, got __" + program.varType(args.get(1)) + "__.");
					}
				}
				break;
				
			case "size":
				if(args.size() != 2)
				{
					throw new JayInterpreterException("Syntax error: `coll_size` requires exactly 2 arguments, " + args.size() + " given.");
				}
				if(program.varExists(args.get(0)))
				{
					if(program.getColl(args.get(0)) instanceof Collection)
					{
						if(program.varExists(args.get(1)))
						{
							if(program.varType(args.get(1)).equals("int"))
							{
								program.setVar(args.get(1), ((Collection)program.getColl(args.get(0))).size());
							}
							else
							{
								throw new JayInterpreterException("Type error: expected __int__, got __" + program.varType(args.get(1)) + "__.");
							}
						}
						else
						{
							throw new JayInterpreterException("Name error: variable " + args.get(1) + " doesn't exist.");
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
				
			case "ins":
				if(args.size() != 3)
				{
					throw new JayInterpreterException("Syntax error: `coll_ins` requires exactly 3 arguments, " + args.size() + " given.");
				}
				if(program.varExists(args.get(0)))
				{
					if(program.getColl(args.get(0)) instanceof Collection)
					{
						int index = -1;
						if(program.varExists(args.get(1)))
						{
							if(program.varType(args.get(1)).equals("int"))
							{
								index = Integer.parseInt(program.getVar(args.get(1)).toString());
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
								index = Integer.parseInt(args.get(1));
							}
							catch(Exception e)
							{
								throw new JayInterpreterException("Type error: expected var or __int__.");
							}
						}
						
						if(program.varExists(args.get(2)))
						{
							((Collection)program.getColl(args.get(0))).insert(index, program.getVar(args.get(2)));
						}
						else
						{
							((Collection)program.getColl(args.get(0))).insert(index, args.get(2));
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
		}
	}
}
