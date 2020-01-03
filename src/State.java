import java.util.*;

public class State
{
	private Map<String, Variable> variables;
	private Map<String, Integer> flags;
	private Map<String, Boolean> options;
	private Map<String, Routine> routines;
	private List<String> lines;
	private int lineCounter;
	private boolean finished;
	private Command prevCommand;
	private int loopBack;
	private State parent;
	private boolean rethrow;
	private Variable returnValue;
	
	public static State defaultState()
	{
		State s = new State();
		s.parent = null;
		s.variables = new HashMap<>();
		s.flags = new HashMap<>();
		s.options = new HashMap<>();
		s.routines = new HashMap<>();
		s.options.put("debug", false);
		s.options.put("release", false);
		s.options.put("print_err", false);
		s.lineCounter = 0;
		s.loopBack = -1;
		s.finished = false;
		s.rethrow = false;
		s.returnValue = null;
		return s;
	}
	
	public Variable getReturn() { return returnValue; }
	
	public void enableRethrow() { rethrow = true; }
	
	public void setParent(State parent) { this.parent = parent; }
	
	public void addRoutine(String name, Routine content) 
	{
		if(routines.keySet().contains(name)) 
		{
			throw new JayInterpreterException("Name error: identifier '" + name + "' is already defined.");
		}
		routines.put(name, content); 
	}
	public boolean hasRoutine(String name) { return routines.keySet().contains(name) || (parent != null && parent.hasRoutine(name)); }
	public Routine getRoutine(String name) { return (hasRoutine(name)) ? 
			routines.keySet().contains(name) ? routines.get(name) : parent.getRoutine(name) : null; }
	
	public void giveLines(List<String> lines) 
	{
		if(this.lines == null) { this.lines = new ArrayList<String>(); }
		for(String s : lines)
		{
			this.lines.add(s.trim());
		}
	}
	public String getLine(int lineno) { return lines.get(lineno); }
	public String currenLine() { return lines.get(lineCounter); }
	
	public boolean inLoop() { return loopBack != -1; }
	public void startLoop() { loopBack = lineCounter; }
	public void endLoop() { loopBack = -1; }
	
	public void updateCommand(Command c) { this.prevCommand = c; }
	public Command getPrevious() { return this.prevCommand; }
	
	public Map<String, Variable> getVars() { return variables; }
	public Map<String, Integer> getFlags() { return flags; }
	public Map<String, Boolean> getOpts() { return options; }
	
	public boolean optionValue(String option)
	{
		if(options.containsKey(option))
		{
			return options.get(option);
		}
		else
		{
			throw new JayInterpreterException("Name error: identifier __opt::" + option + "__ not found.");
		}
	}
	
	public void deepCopy(String varname, String newname, State target)
	{
		if(!variables.containsKey(varname))
		{
			throw new JayInterpreterException("Name error: identifier " + varname + " unknown.");
		}
		String type = variables.get(varname).getType();
		Variable v = new Variable(type);
		variables.get(varname).deepCopy(v);
		target.variables.put(newname, v);
	}
	
	public void deepCopy(Variable var, String newname, State target)
	{
		if(target.variables.replace(newname, var) == null)
		{
			throw new JayInterpreterException("Name error: can't map return value to " + newname + ".");
		}
	}
	
	public void enableOption(String option)
	{
		if(options.containsKey(option))
		{
			if(!options.get(option))
			{
				options.replace(option, true);
			}
			else
			{
				throw new JayInterpreterException("Syntax error: identifier __opt::" + option + "__ already enabled.");
			}
		}
		else
		{
			throw new JayInterpreterException("Name error: identifier __opt::" + option + "__ not found.");
		}
	}
	
	public int getFlagAddress(String flag)
	{
		if(flags.containsKey(flag)) 
		{
			return flags.get(flag);
		}
		else
		{
			throw new JayInterpreterException("Name error: identifier '" + flag + "' not found.");
		}
	}
	
	public void setFlag(String flag, int line)
	{
		if(flags.containsKey(flag)) 
		{
			throw new JayInterpreterException("Name error: identifier '" + flag + "' already declared.");
		}
		else
		{
			flags.put(flag, line);
		}
	}
	
	public boolean flagSet(String flag) { return flags.containsKey(flag); }
	
	public Object getVar(String variable)
	{
		if(variables.containsKey(variable))
		{
			return variables.get(variable).getValue();
		}
		else if(variables.containsKey(variable.split("->")[0]))
		{
			Variable holder = variables.get(variable.split("->")[0]);
			for(int i = 1; i < variable.split("->").length; i++)
			{
				holder = holder.getField(variable.split("->")[i]);
			}
			//System.out.println("Root type: " + holder.getType());
			return holder.getValue();
		}
		else
		{
			throw new JayInterpreterException("Name error: identifier '" + variable + "' not found.");
		}
	}
	
	public void setReturn(String varname)
	{
		if(variables.containsKey(varname))
		{
			returnValue = variables.get(varname);
		}
		else
		{
			throw new JayInterpreterException("Name error: identifier '" + varname + "' not found.");
		}
	}
	
	public boolean varExists(String variable)
	{
		return variables.containsKey(variable);
	}
	
	public String varType(String variable)
	{
		if(variables.containsKey(variable) || variables.containsKey(variable.split("->")[0]))
		{
			if(variable.contains("->"))
			{
				Variable holder = variables.get(variable.split("->")[0]);
				for(int i = 1; i < variable.split("->").length; i++)
				{
					holder = holder.getField(variable.split("->")[i]);
				}
				return holder.getType();
			}
			else
			{
				return variables.get(variable).getType();
			}
		}
		else
		{
			throw new JayInterpreterException("Name error: identifier '" + variable + "' not found.");
		}
	}
	
	public void declare(String type, String identifier)
	{
		if(variables.containsKey(identifier) || variables.containsKey(identifier.split("->")[0]))
		{
			throw new JayInterpreterException("Name error: identifier '" + identifier + "' is already declared.");
		}
		variables.put(identifier, new Variable(type));
	}
	
	public void setVar(String variable, Object newValue)
	{
		if(!variables.containsKey(variable) && !variables.containsKey(variable.split("->")[0]))
		{
			throw new JayInterpreterException("Name error: identifier '" + variable + "' not found.");
		}
		if(variable.contains("->"))
		{
			Variable holder = variables.get(variable.split("->")[0]);
			for(int i = 1; i < variable.split("->").length; i++)
			{
				holder = holder.getField(variable.split("->")[i]);
			}
			//System.out.println("Root type: " + holder.getType());
			holder.setValue(newValue);
		}
		else
		{
			variables.get(variable).setValue(newValue);
		}
	}
	
	public int getLineCounter() { return lineCounter; }
	public void incrementLineCounter() { setLineCounter(lineCounter + 1); }
	public void decrementLineCounter() { setLineCounter(lineCounter - 1); }
	public void setLineCounter(int newCounter)
	{
		if(lineCounter >= lines.size())
		{
			throw new JayInterpreterException("Syntax error: expected command or 'exit', got EOF.");
		}
		lineCounter = newCounter;
	}
	
	public boolean isFinished() { return finished; }
	public void finish() { finished = true; }
	
	public void runSingle()
	{
		Statement s = Statement.fromLine(lines.get(lineCounter));
		if(s == null)
		{
			incrementLineCounter();
			runSingle();
		}
		else
		{
			try
			{
				s.execute(this);
				
				if(!isFinished())
				{
					runSingle();
				}
			}
			catch(JayInterpreterException ex)
			{
				if(rethrow)
				{
					throw ex;
				}
				else
				{
					System.out.println("Execution failed. Aborting.");
					System.err.println("Error occurred in line " + lineCounter + ": '" + lines.get(lineCounter).trim() + "':");
					System.err.println(ex.getMessage());
					return;
				}
			}
		}
	}
}
