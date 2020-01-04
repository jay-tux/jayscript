import java.util.*;

public class Collection extends Variable
{
	private List<Object> contents;
	
	public Collection(String type)
	{
		super("");
		contents = new ArrayList<Object>();
	}
	
	public static void create(String type, String name, State program)
	{
		program.declareColl(type, name);
	}
	
	public void add(Object value)
	{
		if(Variable.isLegalFor(type, value))
		{
			contents.add(value);
		}
	}
	
	public void print()
	{
		System.out.print("[ ");
		for(Object o : contents)
		{
			System.out.print(o.toString() + " - ");
		}
		System.out.println("NULL ]");
	}
	
	public void remove(int index)
	{
		if(index >= contents.size() || index < 0) { throw new JayInterpreterException("Collection error: index out of range."); }
		contents.remove(index);
	}
	
	public void update(int index, Object newVal)
	{
		if(index >= contents.size() || index < 0) { throw new JayInterpreterException("Collection error: index out of range."); }
		contents.set(index, newVal);
	}
	
	public void insert(int index, Object newVal)
	{
		if(index >= contents.size() || index < 0) { throw new JayInterpreterException("Collection error: index out of range."); }
		contents.add(index, newVal);
	}
	
	public Object get(int index)
	{
		if(index >= contents.size() || index < 0) { throw new JayInterpreterException("Collection error: index out of range."); }
		return contents.get(index);
	}
	
	public void deepCopyValToVar(String identifier, int index, State program)
	{
		if(index >= contents.size() || index < 0) { throw new JayInterpreterException("Collection error: index out of range."); }
		program.setVar(identifier, contents.get(index));
	}
	
	public void iterate(String function, State program)
	{
		for(Object o : contents)
		{
			Routine r = program.getRoutine(function);
			if(!(r instanceof Function)) { throw new JayInterpreterException("Collection error: argument is a routine, not a function."); }
			Function f = (Function)r;
			f.invokeColl(program, o, type);
		}
	}
}
