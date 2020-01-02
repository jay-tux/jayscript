public class JayInterpreterException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
	public JayInterpreterException()
	{
		super("An error has occurred.");
	}
	
	public JayInterpreterException(String message)
	{
		super(message);
	}
}
