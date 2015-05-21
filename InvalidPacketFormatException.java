
public class InvalidPacketFormatException extends RuntimeException
{
	public InvalidPacketFormatException()
	{
		throw new IllegalArgumentException("PortNumber specified is the incorrect type.  If it is for sending, it should be for receiving and if it is for receiving it should be for sending");
	}
}
