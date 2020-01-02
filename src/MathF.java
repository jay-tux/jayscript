import java.util.*;

public class MathF extends Math
{
	public MathF(ArrayList<String> args, String mode)
	{
		super(args, mode);
	}
	
	private ArrayList<MathExpr> fill(ArrayList<MathExpr> unfilled, State program)
	{
		for(MathExpr ex : unfilled)
		{
			if(!ex.isOperator())
			{
				if(program.varExists(ex.getValue()))
				{
					//System.out.println("Variable exists.");
					Object tmp = program.getVar(ex.getValue());
					float val = Float.parseFloat(tmp.toString());
					ex.setConvertedF(val);
				}
				else
				{
					//System.out.println("Variable doesn't exist.");
					try
					{
						float j = Float.parseFloat(ex.getValue());
						ex.setConvertedF(j);
					}
					catch(Exception e)
					{
						throw new JayInterpreterException("Expression error: expression expected <float> or <var>, but got " + ex.getValue() + ".");
					}
				}
			}
		}
		
		return unfilled;
	}
	
	private float calculate(ArrayList<MathExpr> postfix, State program)
	{
		ArrayList<MathExpr> filled = fill(postfix, program);
		Stack<Float> values = new Stack<>();
		for(MathExpr ex : filled)
		{
			if(ex.isOperator())
			{
				try
				{
					float i1 = values.pop();
					float i2 = values.pop();
					values.push(ex.calcF(i2, i1));
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
				values.push(ex.getConvertedF());
			}
		}
		return values.pop();
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
		float res = calculate(infixToPostfix(args), program);
		
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
}
