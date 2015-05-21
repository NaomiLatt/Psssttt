public abstract class RequestFromPeer implements Runnable
{
	private UDPMessage udpMessage;
	
	public RequestFromPeer(UDPMessage udpMessage)
	{
		if(udpMessage == null){throw new IllegalArgumentException("RequestFromPeer constructor: null parameter");}
		this.udpMessage = udpMessage;
	}
	
	public UDPMessage getUDPMessage()
	{
		return this.udpMessage;
	}
	
	public abstract void run();
}
