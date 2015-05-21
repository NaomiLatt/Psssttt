import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

/*
Lauren M DiGregorio
	  April 12, 2015
	  
	  For the final project
	  This class is the vehicle to send datagramPackets
	  
	  Class Variables
	  	none
		
	  Constructors
	  	
	  	DatagramReceiver(InetSocketAddress inetSocketAddress, IncomingPacketQueue queue, int packetSize)
	  		Creates a DatagramSender object
	
		Methods
			public void action(DatagramSocket datagramSocket, SynchronizedPacketQueue queue)
				Action sends the packet out
				
			




public class DatagramReceiver extends DatagramSenderReceiver
{
	//list holds incoming datagramPackets on a find() command
	private IncomingPacketQueue 			tempList;
	private int								count;
	private Map<Integer, DatagramPacket> 	allFinds;
	
	public DatagramReceiver(DatagramSocket datagramSocket, IncomingPacketQueue queue, int packetSize) throws SocketException
	{
		//Creates a DatagramSender object
		super(datagramSocket, queue, packetSize);
		
		this.tempList = new IncomingPacketQueue();
		this.count = 0;
	}


	public void action(DatagramSocket datagramSocket, SynchronizedPacketQueue queue) throws IOException
	{
		//Action sends the packet out

		System.out.println("Made it into the receiver");
		
		if(datagramSocket == null ) 	{ throw new IllegalArgumentException("datagramSocket in Datagram Receiver is equal to null, try again.");}
		if(queue == null)				{ throw new IllegalArgumentException("queue in Datagram Receiver is equal to null, try again.");}	
		
		
		//byte[]		   	buffer;
		boolean 		keepGoing;
		
		this.allFinds = new HashMap();
		//create a buffer that will be able to receive whatever is passed by the datagramPacket
		//buffer 			= new byte[2000];
		keepGoing		= true;
		//datagramSocket is set already by the parameter that is passed
		
		

		//while doesnt work because the queue is empty 

		while( keepGoing )
		{
			DatagramPacket 	datagramPacket;
			byte[]		   	buffer;
			
			buffer 			= new byte[2000];
			datagramPacket 	= new DatagramPacket(buffer,buffer.length);
			
			datagramSocket.receive(datagramPacket);
			System.out.println("****Packet Receiveed: "+ datagramPacket.getLength());
			String message = new String(datagramPacket.getData(), "UTF-8");
			print("["+this.count+"] : " + message);
			
			this.allFinds.put(new Integer(this.count), datagramPacket);
			
			if( datagramPacket.getData() == null )
			{
				keepGoing = false;
			}
			System.out.println("keepGoing playa: " + keepGoing);
			System.out.println();
			//print("The data received: " +new String(datagramPacket.getData(),0,datagramPacket.getLength()));
		}
		
		
		//socket reads into the queue and then you read everything put into the queue
		
	}//end action
	
	public void print(String message)
	{
		//print something every time something is added to the queue
		System.out.println(message);
	}//end print
	public int getCount()
	{
		return this.count;
	}
	
	public IncomingPacketQueue getIncomingPacketQueue()
	{
		return this.tempList;
	}
	public void setCount()
	{
		this.count = 0;
	}
	public void setIncomingPacketQueue()
	{
		this.tempList = new IncomingPacketQueue();
	}
	
}
*/



import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/*
Lauren M DiGregorio
	  April 12, 2015
	  
	  For the final project
	  This class is the vehicle to send datagramPackets
	  
	  Class Variables
	  	none
		
	  Constructors
	  	
	  	DatagramReceiver(InetSocketAddress inetSocketAddress, IncomingPacketQueue queue, int packetSize)
	  		Creates a DatagramSender object
	
		Methods
			public void action(DatagramSocket datagramSocket, SynchronizedPacketQueue queue)
				Action sends the packet out
*/

public class DatagramReceiver extends DatagramSenderReceiver
{
	private int								count;
	private Map<Integer, DatagramPacket> 	allFinds;
	private AllRequestsHandler				allRequests;
	
	public DatagramReceiver(DatagramSocket datagramSocket, IncomingPacketQueue queue, int packetSize) throws SocketException
	{
		//Creates a DatagramReceiver object
		super(datagramSocket, queue, packetSize);
		
		this.allFinds 	= new HashMap<Integer, DatagramPacket>();
		this.count		= 0;
	}


	public void action(DatagramSocket datagramSocket, SynchronizedPacketQueue queue) throws IOException
	{
		//Action waits for packets to come in

		DatagramPacket 	datagramPacket;
		byte[]		   	buffer;
		UDPMessage		udpMessage;
		Request 		request;
		
		System.out.println("Made it into the receiver");
		
		if(datagramSocket == null ) { throw new IllegalArgumentException("Action method in Datagram Receiver: null datagram socket");}
		if(queue == null)			{ throw new IllegalArgumentException("Action method in Datagram Receiver: null queue");}	
		
		
		buffer 			= new byte[512];
		datagramPacket 	= new DatagramPacket(buffer, buffer.length);
		
		datagramSocket.receive(datagramPacket);
		System.out.println("****Packet Received: "+ datagramPacket.getAddress());
		
		String message = new String(datagramPacket.getData(), "UTF-8");
		print("["+this.count+"] : " + message);
		//this.count = this.count + 1;
		//System.out.println("****Packet Received message: "+ message);
		//this.allFinds.put(new Integer(this.count), datagramPacket);
		queue.enQueue(datagramPacket);
		
		//public Request(ID id, DatagramPacket datagramPacket, String requestNumber)
		udpMessage 	= new UDPMessage(datagramPacket);
		request		= new Request(udpMessage.getID1(), datagramPacket, ""+count);
		
		//need to add conditional if ID doesnt exist in list already
		//allRequests.addRequest(request);
		

		//socket reads into the queue and then you read everything put into the queue
	}//end action
	
	
	public AllRequestsHandler getAllRequests()
	{
		return this.allRequests;
	}
	public void print(String message)
	{
		System.out.println(message);
	}//end print
	
	public void setMap()
	{
		//reset Hashmap every time 
		this.allFinds = new HashMap<Integer, DatagramPacket>();
	}
	
	
	public DatagramPacket getFindObject(Integer key)
	{
		return this.allFinds.get(key);
	}
	
	
	
}