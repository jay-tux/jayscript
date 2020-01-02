import java.util.*;

public final class Arg 
{
	private Arg() {}
	
	public static ArrayList<String> splitArgs(String argStr)
	{
		ArrayList<String> ret = new ArrayList<>();
		boolean in = false;
		String[] prim = argStr.split("\"");
		for(String s : prim)
		{
			if(in)
			{
				ret.add(s);
			}
			else
			{
				for(String add : s.split(" "))
				{
					ret.add(add);
				}
			}
			in = !in;
		}
		return ret;
	}
}
