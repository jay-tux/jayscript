import java.util.*;

public class Math extends Command
{
	protected String mode;
	protected static String[] modes = { "set", "print" };
	
	public Math(List<String> args, String mode)
	{
		super(args);
		if(!Arrays.asList(modes).contains(mode)) {
			throw new JayInterpreterException("Syntax error: unknown math mode.");
		}
		this.mode = mode;
		if(!Sys.isImported("math")) { throw Sys.getNotImported(); }
	}
	
	public void execute(State program)
	{
		if(args.size() < 1)
		{
			throw new JayInterpreterException("Syntax error: <expr> requires to have at least one part.");
		}
		
		String varname = "";
		if(mode.equals("set"))
		{
			varname = args.get(0);
			args.remove(0);
		}
		int res = calculate(infixToPostfix(args), program);
		
		switch(mode)
		{
		
			case "set":
				program.setVar(varname, res);
				break;
				
			case "print":
				System.out.println(res);
				break;
				
			default:
					throw new JayInterpreterException("Syntax error: unknown math mode.");
		}
	}
	
	protected List<MathExpr> infixToPostfix(List<String> infix)
	{
		Stack<MathExpr> stack = new Stack<>();
		ArrayList<MathExpr> fin = new ArrayList<>();
		for(String s : infix)
		{
			MathExpr m = new MathExpr(s);
			if(!m.isOperator())
			{
				fin.add(m);
			}
			else
			{
				if(stack.isEmpty() || stack.peek().getValue().equals("("))
				{
					stack.push(m);
				}
				else if(m.getValue().equals("("))
				{
					stack.push(m);
				}
				else if(m.getValue().equals(")"))
				{
					MathExpr tmp = stack.pop();
					while(!tmp.getValue().equals("("))
					{
						fin.add(tmp);
						tmp = stack.pop();
					}
				}
				else if(!stack.isEmpty() && m.precedence() >= stack.peek().precedence())
				{
					stack.push(m);
				}
				else if(!stack.isEmpty() && m.precedence() < stack.peek().precedence())
				{
					MathExpr tmp = stack.pop();
					fin.add(tmp);
					while(!stack.isEmpty() && m.precedence() < stack.peek().precedence())
					{
						tmp = stack.pop();
						fin.add(tmp);
					}
					stack.push(m);
				}
			}
		}
		
		while(!stack.isEmpty())
		{
			fin.add(stack.pop());
		}
		
		return fin;
	}
	
	private List<MathExpr> fill(List<MathExpr> unfilled, State program)
	{
		for(MathExpr ex : unfilled)
		{
			if(!ex.isOperator())
			{
				if(program.varExists(ex.getValue()))
				{
					//System.out.println("Variable exists.");
					Object tmp = program.getVar(ex.getValue());
					int val = Integer.parseInt(tmp.toString());
					ex.setConverted(val);
				}
				else
				{
					//System.out.println("Variable doesn't exist.");
					try
					{
						int j = Integer.parseInt(ex.getValue());
						ex.setConverted(j);
					}
					catch(Exception e)
					{
						throw new JayInterpreterException("Expression error: expression expected <int> or <var>, but got " + ex.getValue() + ".");
					}
				}
			}
		}
		
		return unfilled;
	}
	
	private int calculate(List<MathExpr> postfix, State program)
	{
		List<MathExpr> filled = fill(postfix, program);
		Stack<Integer> values = new Stack<>();
		for(MathExpr ex : filled)
		{
			if(ex.isOperator())
			{
				try
				{
					int i1 = values.pop();
					int i2 = values.pop();
					values.push(ex.calc(i2, i1));
				}
				catch(JayInterpreterException e)
				{
					throw e;
				}
				catch(Exception e)
				{
					throw new JayInterpreterException("Expression error: Expression error: inevaluatable expression.");
				}
			}
			else
			{
				values.push(ex.getConverted());
			}
		}
		return values.pop();
	}
}
