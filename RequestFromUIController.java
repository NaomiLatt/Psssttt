/*
 Lauren M DiGregorio
 April 18, 2014
  
  this is For the final project
	  This class contains all the commands a user can call from the user interface
	  
	  Class Variables
	  	requestID
	  		ID that represents the file being requested
	  	
	  	 OUtgoingPacketQUeue
	  	 	queue that holds all outgoing packets.  has to connect to the UIController outgoingPacketQueue
	  	 
	  	 inetsocketAddress
	  	 	holds the address for your own IP
	  Constructors
	  	
	  	RequestFromUIController(ID)
	  		takes ID object and 
	
	
		Methods
			public ID getID()
				return the ID in the class
	
			public abstract void updateRequest(UDPMessage udpMessage)
				uses the collection variable in RequestManager.  Everytime a peer receives a Find Request, 
				Collection holds ID1 of all responses sent out,  If the Find Request ID1 matches an ID that 
				is in our collection, we can then ignore the Find request. Else, add ID to our list, construct 
				a UDPMessage meant for the URController.  This response then flips the place of ID1 and ID2
				(therefore whoever is looking for the response will be looking for their ID in ID2).
				
		
			public OutgoingPacketQueue getQueue()
				get queue
		
			public InetSocketAddress getUIControllerAddress()
				return your own IP address
			
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public abstract class RequestFromUIController 
{

	private ID requestID;
	private OutgoingPacketQueue outgoingPacketQueue;
	private InetSocketAddress uiControllerAddress;

	public RequestFromUIController(ID id)
	{
		if(id.getLengthInBytes()!=16){throw new IllegalArgumentException("Invalid ID length");}

		this.requestID = id;
		this.outgoingPacketQueue = new OutgoingPacketQueue();
		this.uiControllerAddress = new InetSocketAddress(12346);
	}

	public ID getRequestID()
	{
		return this.requestID;
	}//getID

	public abstract void updateRequest(UDPMessage udpMessage);

	public OutgoingPacketQueue getQueue()
	{
		return this.outgoingPacketQueue;
	}//getQueue

	public InetSocketAddress getUIControllerAddress()
	{
		return uiControllerAddress;
	}//getUIControllerAddress
}
