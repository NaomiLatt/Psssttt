import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class GossipPartner 
{
	private InetSocketAddress gossipPartnerAddress;
	private boolean isAlive;
	private OutgoingPacketQueue queue;

	public GossipPartner(InetSocketAddress gossipPartnerAddress, OutgoingPacketQueue queue)
	{
		//Check parameters
		if(gossipPartnerAddress == null){throw new IllegalArgumentException
			("GossipPartner constructor: null socket address parameter");}
		if(queue == null){throw new IllegalArgumentException
			("GossipPartner constructor: null queue parameter");}
		
		//Set class variables
		this.gossipPartnerAddress = gossipPartnerAddress;
		this.isAlive = true;
		this.queue = queue;
	}
	
	public boolean equals(Object obj) 
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GossipPartner other = (GossipPartner) obj;
		if (gossipPartnerAddress == null) {
			if (other.gossipPartnerAddress != null) 
			{
				return false;
			}
		} else if (!gossipPartnerAddress.equals(other.gossipPartnerAddress)) {
			return false;
		}
		return true;
	}
	
	public InetSocketAddress getGossipPartnerAddress()
	{
		return this.gossipPartnerAddress;
	}
	
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((gossipPartnerAddress == null) ? 0 : gossipPartnerAddress
						.hashCode());
		return result;
	}
	
	public boolean isAlive()
	{
		return this.isAlive;
	}
	
	public void send(UDPMessage message)
	{
		if(message == null)		{ throw new IllegalArgumentException("GossipPartner send method: null message"); }
		
		
		DatagramPacket datagramPacket = message.getDatagramPacket();
		
		datagramPacket.setSocketAddress(this.getGossipPartnerAddress());
		datagramPacket.setPort(this.getGossipPartnerAddress().getPort());
		
		System.out.println("Sent it to: " + this.getGossipPartnerAddress());
		System.out.println("on port: " + this.getGossipPartnerAddress().getPort());
		
		this.queue.enQueue(datagramPacket);
	}
}









/*import java.net.InetSocketAddress;


public class GossipPartner 
{
	private InetSocketAddress gossipPartnerAddress;
	private boolean isAlive;
	private OutgoingPacketQueue queue;

	public GossipPartner(InetSocketAddress gossipPartnerAddress, OutgoingPacketQueue queue)
	{
		//Check parameters
		if(gossipPartnerAddress == null){throw new IllegalArgumentException
			("GossipPartner constructor: null socket address parameter");}
		if(queue == null){throw new IllegalArgumentException
			("GossipPartner constructor: null queue parameter");}
		
		//Set class variables
		this.gossipPartnerAddress = gossipPartnerAddress;
		this.isAlive = true;
		this.queue = queue;
	}
	
	public boolean equals(Object obj) 
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GossipPartner other = (GossipPartner) obj;
		if (gossipPartnerAddress == null) {
			if (other.gossipPartnerAddress != null) 
			{
				return false;
			}
		} else if (!gossipPartnerAddress.equals(other.gossipPartnerAddress)) {
			return false;
		}
		return true;
	}
	
	public InetSocketAddress getGossipPartnerAddress()
	{
		return this.gossipPartnerAddress;
	}
	
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((gossipPartnerAddress == null) ? 0 : gossipPartnerAddress
						.hashCode());
		return result;
	}
	
	public boolean isAlive()
	{
		//i think we may need to ping to machine before setting this varaible
		return this.isAlive;
	}
	
	public void send(UDPMessage message)
	{
		//we want to address the packets in here and then enQueue to this.queue, and then enQueue the queue to 
		if(message == null){ throw new IllegalArgumentException("GossipPartner send method: null message"); }
		
		this.queue.enQueue(message);
	}
	public OutgoingPacketQueue getOutgoingPacketQueue()
	{
		return this.queue;
	}
}
*/