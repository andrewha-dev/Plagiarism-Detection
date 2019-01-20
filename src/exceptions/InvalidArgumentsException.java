package exceptions;

public class InvalidArgumentsException extends Exception
{
	public InvalidArgumentsException(String errorMessage)
	{
		super(errorMessage);
	}

	public InvalidArgumentsException(String errorMessage, Throwable err)
	{
		super(errorMessage, err);
	}

}
