import java.util.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Interpreter {
	private ArrayList<String> lines;
	private State program;
	private static String progDir;
	
	private Interpreter(String prog) 
	{
		File in = new File(prog);
		if(in.exists() && !in.isDirectory())
		{
			try
			{
				progDir = Paths.get(prog).getParent().toString() + "/";
				this.lines = new ArrayList<>();
				lines.add("");
				for(String li : Files.readAllLines(Paths.get(prog), Charset.defaultCharset()))
				{
					lines.add(li);
				}
			}
			catch(IOException ex)
			{
				throw new JayInterpreterException("Loading error: Failed to read from file.");
			}
		}
		else
		{
			throw new JayInterpreterException("Loading error: File doesn't exist or is a directory.");
		}
		program = State.defaultState();
	}
	
	public void run()
	{
		imports();
		scanFlags();
		//System.out.println(String.join("\n", lines));
		program.giveLines(lines);
		program.runSingle();
	}
	
	//Importing not tested yet
	private void imports()
	{
		int lineno = 0;
		int removes = 0;
		HashMap<Integer, List<String>> toAdd = new HashMap<>();
		for(String line : lines)
		{
			if(line.trim().startsWith("!"))
			{
				File imp = new File(progDir + line.trim().substring(1));
				if(imp.exists() && !imp.isDirectory())
				{
					removes++;
					try
					{
						toAdd.put(lineno, Files.readAllLines(Paths.get(progDir + line.trim().substring(1)), Charset.defaultCharset()));
					}
					catch(IOException ex)
					{
						throw new JayInterpreterException("Import error: failed to read '" + line.trim().substring(1) + "'.");						
					}
				}
				else
				{
					throw new JayInterpreterException("Import error: can't import '" + line.trim().substring(1) + "'.");
				}
			}
			lineno++;
		}
		
		int offset = 0;
		
		for(int i : toAdd.keySet())
		{
			lines.remove(i);
		}
		
		for(int i : toAdd.keySet())
		{
			lines.addAll(offset + i - removes + 1, toAdd.get(i));
			offset += toAdd.get(i).size();
		}
		
		//System.out.println(String.join("\n", lines));
	}
	
	public String getWorkDir() { return progDir; }
	
	private void scanFlags()
	{
		int lineno = 0;
		for(String line : lines)
		{
			if(line.trim().startsWith("@"))
			{
				program.setFlag(line.trim().substring(1), lineno);
			}
			lineno++;
		}
	}
	
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			System.out.println("Please re-run, giving a file to run.");
			return;
		}
		try
		{
			Interpreter i = new Interpreter(args[0]);
			i.run();
		}
		catch(JayInterpreterException e)
		{
			System.err.println("Interpreting failed: " + e.getMessage() + ".");
		}
	}
}
