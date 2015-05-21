import java.net.DatagramSocket;
import java.net.SocketException;



public class SendToGossipCommunity
{
	GossipPartners 		gossipPartners;
	//private Collection<GossipPartner> gossipPartners;
	DatagramSender		datagramSender;
	OutgoingPacketQueue	outgoingPacketQueue;
	
	public SendToGossipCommunity(GossipPartners gossipPartners) throws SocketException
	{
		this.gossipPartners = gossipPartners;
		outgoingPacketQueue = new OutgoingPacketQueue();
		datagramSender		= new DatagramSender(new DatagramSocket(), outgoingPacketQueue, 512 );
		//datagramSender.run();
		
	}//end constructor
	
	public void sendPackets() throws SocketException
	{
		//Thread method implicitly calls run method
		datagramSender.startAsThread();
		
		while(true)
		{
			//do we need to end this class?
			
			if(!this.gossipPartners.getFirstGossipPartner().getOutgoingPacketQueue().isEmpty())
			{
				//we want to enQueue all packets in waiting to correct outgoing packet sender, datagramSender
				
				for(GossipPartner gossipPartner : gossipPartners.getGossipPartners())
				{
					//udpMessage.getDatagramPacket().setSocketAddress(gossipPartner.getGossipPartnerAddress());
					//udpMessage.getDatagramPacket().setPort(12345);
					
					//gossipPartner.send(udpMessage);
					this.outgoingPacketQueue.enQueue(gossipPartner.getOutgoingPacketQueue());
					
					
				}//end for
				
				
			}//end conditional
			
			
			
			//add some sleep
			try 							{ Thread.sleep(20);   } 
            catch (InterruptedException e) 	{ throw new IllegalArgumentException("Sleep method in SendToGossipCommunity did not work"); }
			
		}//end while
	}//end SendPacketsToAll method


}//end nested class

