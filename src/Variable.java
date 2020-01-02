import java.util.*;

public class Variable
{
	public static String[] builtin = new String[] { "int", "char", "float", "string" };
	private static HashMap<String, HashMap<String, String>> customs;
	
	static 
	{
		customs = new HashMap<>();
	}
	
	private String type;
	private Object value;
	private HashMap<String, Variable> fields;
	
	public Variable(String type)
	{
		if(!Arrays.asList(builtin).contains(type) && !customs.containsKey(type)) 
		{
			throw new JayInterpreterException("Type error: type __" + type + "__ unknown.");
		}
		this.value = null;
		this.type = type;
		if(!isBuiltin(type))
		{
			fields = new HashMap<>();
			for(String s : customs.get(type).keySet())
			{
				//System.out.println("Declaring field " + s + "; type is __" + customs.get(type).get(s) + "__");
				fields.put(s, new Variable(customs.get(type).get(s)));
			}
		}
	}
	
	public static boolean isBuiltin(String type)
	{
		return Arrays.asList(builtin).contains(type);
	}
	
	public static boolean isType(String type)
	{
		return isBuiltin(type) || customs.containsKey(type);
	}
	
	public static void createType(String name, HashMap<String, String> fields)
	{
		if(!customs.containsKey(name) && !Arrays.asList(builtin).contains(name))
		{
			customs.put(name, fields);
		}
		else
		{
			throw new JayInterpreterException("Type error: type __" + name + "__ already defined.");
		}
	}
	
	public String getType() { return type; }
		
	public Object getValue() 
	{
		if(!isBuiltin(type)) { throw new JayInterpreterException("Type error: can't get value::single from non-builtin type."); }
		return value; 
	}
	public HashMap<String, Variable> getFields() 
	{
		if(isBuiltin(type)) { throw new JayInterpreterException("Type error: can't get value::fields from builtin type."); }
		return fields; 
	}
	public Variable getField(String fieldname) 
	{
		if(isBuiltin(type)) { throw new JayInterpreterException("Type error: can't get value::fields from builtin type."); }
		if(!fields.containsKey(fieldname) && !fields.containsKey(fieldname.split("->")[0])) 
		{ throw new JayInterpreterException("Type error: type __" + type + "__ doesn't have a field named " + fieldname); }
		
		if(fieldname.contains("->"))
		{
			return fields.get(fieldname.split("->")[0]).getField(fieldname.split("->")[1]);
		}
		else
		{
			return fields.get(fieldname);
		}
	}
	
	public void setValue(Object newV)
	{
		//System.out.println("Variable type: " + type);
		if(!isBuiltin(type)) { throw new JayInterpreterException("Type error: can't set value::single from non-builtin type."); }
		if(!isLegalFor(type, newV))
		{
			throw new JayInterpreterException("Type error: can't convert '" + newV.toString() + "' to a " + type + ".");
		}
		value = newV;
	}
	
	public void deepCopy(Variable target)
	{
		target.fields = fields;
		target.type = type;
		target.value = value;
	}
	
	public void setField(String fieldname, Object newV)
	{
		//System.out.println("Trying to get field " + fieldname + " from variable of type " + type + ".");
		//if(fields == null) System.out.println("Fields array is null.");
		//if(fields.keySet() == null) System.out.println("Fields' keyset is null.");
		//System.out.println("Fields: " + String.join(", ", fields.keySet()));
		if(isBuiltin(type)) { throw new JayInterpreterException("Type error: can't set value::fields from builtin type."); }
		if(fields.containsKey(fieldname) && isBuiltin(fields.get(fieldname).type))
		{
			if(!fields.containsKey(fieldname)) { throw new JayInterpreterException("Type error: type __" +
					type + "__ doesn't have a field named " + fieldname); }
			fields.get(fieldname).setValue(newV);
		}
		else
		{
			if(!fieldname.contains("->"))
			{
				throw new JayInterpreterException("Syntax error: can't set non-builtin type directly.");
			}
			fields.get(fieldname.split("->")[0]).setField(fieldname.split("->")[1], newV);
		} 
	}
	
	public boolean sameType(Variable other)
	{
		return type.equals(other.type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variable other = (Variable) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public static boolean isLegalFor(String type, Object value)
	{
		switch(type)
		{
			case "int":
				if(value instanceof Integer)
				{
					return true;
				}
				try {
					Integer.parseInt(value.toString());
					return true;
				}
				catch(Exception e)
				{
					return false;
				}
			
			case "char":
				if(value instanceof Character)
				{
					return true;
				}
				if(value.toString().length() == 1)
				{
					return true;
				}
				return false;
				
			case "float":
				if(value instanceof Float)
				{
					return true;
				}
				try {
					Float.parseFloat(value.toString());
					return true;
				}
				catch(Exception e)
				{
					return false;
				}
				
			case "string":
				return true;
				
			default:
				throw new JayInterpreterException("Type error: can't check legality of unknown type " + type + ".");
		}
	}
}
