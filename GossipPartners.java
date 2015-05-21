import java.util.Collection;
import java.util.LinkedList;

public class GossipPartners 
{
	private static GossipPartners instance;
	private Collection<GossipPartner> gossipPartners;
	
	static
	{
		instance = new GossipPartners();
	}
	
	private GossipPartners()
	{
		gossipPartners = new LinkedList<GossipPartner>();
	}
	
	public static GossipPartners getInstance()
	{
		return instance;
	}
	
	public static GossipPartners newInstance()
	{
		return getInstance();
	}
	
	public void addPartner(GossipPartner gossipPartner)
	{
		//Add GossipPartner to the Collection
		
		if(gossipPartner == null){throw new IllegalArgumentException("addPartner method: null parameter");}
		
		this.gossipPartners.add(gossipPartner);
	}
	
	public void send(UDPMessage udpMessage)
	{
		if(udpMessage == null){ throw new IllegalArgumentException("GossipPartners send method: null udpMessage"); }
		
		int ttl = udpMessage.getTimeToLive().get();
		
		if(ttl > 0)
		{
			ttl = ttl-1;
			udpMessage = new UDPMessage(udpMessage.getID1(), udpMessage.getID2(), new TimeToLive(ttl), udpMessage.getMessage());
			
			//Send out the udpMessage through each GossipPartner
			for(GossipPartner gossipPartner : this.gossipPartners)
			{	
				gossipPartner.send(udpMessage);
			}
		}
		else
		{
			System.out.println("TimeToLive 0. Packet not sent.");
		}
	}
}




/*import java.util.*;
import java.util.LinkedList;

public class GossipPartners 
{
	private static GossipPartners instance = null;
	private Collection<GossipPartner> gossipPartners;
	
	static
	{
		instance = new GossipPartners();
	}
	
	private GossipPartners()
	{
		gossipPartners = new LinkedList<GossipPartner>();
	}
	
	public static GossipPartners getInstance()
	{
		return instance;
	}
	
	public static GossipPartners newInstance()
	{
		return getInstance();
	}
	
	/*
	private GossipPartners()
	{
		gossipPartners = new LinkedList<GossipPartner>();
	}
	
	public static GossipPartners getInstance()
	{
		if(instance == null) 	
		{ 
			instance = new GossipPartners();
			
		}
		return instance;
	}
	
	
	public void addPartner(GossipPartner gossipPartner)
	{
		//Add GossipPartner to the Collection
		
		if(gossipPartner == null){throw new IllegalArgumentException("addPartner method: null parameter");}
		
		this.gossipPartners.add(gossipPartner);
	}
	
	public void send(UDPMessage udpMessage)
	{
		if(udpMessage == null){ throw new IllegalArgumentException("GossipPartners send method: null udpMessage"); }
		
		//Send out the udpMessage through each GossipPartner
		for(GossipPartner gossipPartner : gossipPartners)
		{
			udpMessage.getDatagramPacket().setSocketAddress(gossipPartner.getGossipPartnerAddress());
			udpMessage.getDatagramPacket().setPort(12345);
			
			gossipPartner.send(udpMessage);
			
		}//end for
		
		
		
	}//end send method
	
	public Collection<GossipPartner> getGossipPartners()
	{
		return this.gossipPartners;
	}
	
	public GossipPartner getFirstGossipPartner()
	{
		
	//	return this.gossipPartners.peek();
		//have to use iterator
		GossipPartner gossipPartner1;
		gossipPartner1 = null;
		
		for (Iterator iterator = this.gossipPartners.iterator(); iterator.hasNext();)
		{
			 gossipPartner1 = (GossipPartner) iterator.next();
			 break;
		}
		return gossipPartner1;

	    
	}
}
*/
