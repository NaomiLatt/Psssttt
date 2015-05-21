
public class QueueUnderflowException extends RuntimeException
{
	public QueueUnderflowException()
	{
		throw new IllegalArgumentException("Nothing in queue.  Add something and try again playa.");
	}//end exception
}
