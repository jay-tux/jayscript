import java.util.*;

public class MathExpr {
	private String value;
	private boolean operator;
	private int converted;
	private float convertedF;
	
	private static HashMap<String, Integer> operators;
	
	static {
		operators = new HashMap<>();
		operators.put("+", 1);
		operators.put("-", 1);
		operators.put("*", 2);
		operators.put("/", 2);
		operators.put("(", 0);
		operators.put(")", 0);
	}

	public MathExpr(String value)
	{
		this.value = value;
		operator = operators.containsKey(value);
	}
	
	public String getValue() { return value; }
	public boolean isOperator() { return operator; }
	public int getConverted() { return converted; }
	public float getConvertedF() { return convertedF; }
	public void setConverted(int value) { converted = value; }
	public void setConvertedF(float value) { convertedF = value; }
	
	public static int precedence(String operator)
	{
		if(!operators.containsKey(operator))
		{
			throw new JayInterpreterException("Expression error: expected <operator>, got " + operator + ".");
		}
		return operators.get(operator);
	}
	
	public int precedence()
	{
		if(!operators.containsKey(value))
		{
			throw new JayInterpreterException("Expression error: expected <operator>, got " + value + ".");
		}
		return operators.get(value);
	}
	
	public int calc(int i1, int i2)
	{
		if(!operator) { throw new JayInterpreterException("Expression error: can't evaluate non-operator."); }
		
		switch(value)
		{
			case "+": return i1 + i2;
			case "-": return i1 - i2;
			case "*": return i1 * i2;
			case "/": return i1 / i2;
			default: throw new JayInterpreterException("Expression error: not an evaluatable operator '" + value + "'.");
		}
	}
	
	public float calcF(float f1, float f2)
	{
if(!operator) { throw new JayInterpreterException("Expression error: can't evaluate non-operator."); }
		
		switch(value)
		{
			case "+": return f1 + f2;
			case "-": return f1 - f2;
			case "*": return f1 * f2;
			case "/": return f1 / f2;
			default: throw new JayInterpreterException("Expression error: not an evaluatable operator '" + value + "'.");
		}
	}
	
	public String toString()
	{
		return value;
	}
}
