	/*
 Lauren M DiGregorio
May 8, 2015
  
  this is For the final project
	  This class contains all the commands a user can call from the user interface
	  
	  Class Variables
	  	responses 
	  		Collection of responses that the UIContoller has made to find resource in its own manifest
	  		
	  Constructors
	  	
	  	RequestFromUIControllerToFindResource(ID)
	  		uses ID to create a request type, Type is to find ID within UIControllers own Resource manager
	
		Methods
			public void updateRequest(UDPMessage udpMessage)
				uses the collection variable in RequestManager.  Every time a peer receives a Find Request, 
				Collection holds ID1 of all responses sent out,  If the Find Request ID1 matches an ID that 
				is in our collection, we can then ignore the Find request. Else, add ID to our list, construct 
				a UDPMessage meant for the UIController.  This response then flips the place of ID1 and ID2
				(therefore whoever is looking for the response will be looking for their ID in ID2).
				Takes UDPMessage, puts it into a datagramPacket, addressed to the UIContorller (calling super
				getUIControllerAdress()), and enQueued to outgoingPacketQUeue
 */


//public class RequestFromUIControllerToFindResources extends RequestFromUIController
//{

//}
import java.net.*;
import java.util.*;
 
public class RequestFromUIControllerToFindResources extends RequestFromUIController
{
	
	private ArrayList<ID> responses;
	private InetSocketAddress uiControllerAddress;
	private OutgoingPacketQueue outQueue;
	private Map<Integer, DatagramPacket> 	allFinds;

	public RequestFromUIControllerToFindResources(ID requestID,InetSocketAddress uiControllerAddress, OutgoingPacketQueue outQueue)
	{
		super(requestID);
		
		responses = new ArrayList<ID>();
		
		if(uiControllerAddress == null)	{throw new IllegalArgumentException("uiControllerAddress is null");}
		if(outQueue == null)			{throw new IllegalArgumentException("queue is null");}
		
		this.uiControllerAddress 	= uiControllerAddress;
		this.outQueue 				= outQueue;
	}
	
	

	public void updateRequest(UDPMessage udpMessage)
	{
		DatagramPacket datagramPacket;
		
		if(!responses.contains(udpMessage.getID1()))
		{
			responses.add(udpMessage.getID1());
			
			datagramPacket = new UDPMessage(udpMessage.getID2(),udpMessage.getID1(),udpMessage.getTimeToLive(),udpMessage.getMessage()).getDatagramPacket();
			datagramPacket.setSocketAddress(this.uiControllerAddress);
			outQueue.enQueue(datagramPacket);
			
		}
		
	}//updateRequest

}