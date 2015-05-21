import java.util.concurrent.atomic.*;
import java.net.*;
import java.io.IOException;
import java.lang.*;

/*
	
	Lauren M DiGregorio
	  April 14, 2015
	  
	  This class will send or receive packets. Implements runnable
	  
	  Class Variables
	  	done
	  		AtomicBoolean that puts a lock on the class so that only one object can access it at a time
	  		
		datagramSocket
			DatagramSocket that sends or receives packets
			
		int packetSize
			int that shows the size of the packet
			
		queue
			SynchronizedLinkedListQueue that holds the packets that are either sent or received 
			
		
	  Constructors
	  	
	  	DatagramSenderReceiver(InetSocketAddress inetSocketAddress, SynchronizedPacketQueue queue, int packetSize)
	  		creates a DatagramSenderReceiver object
	
		Methods
			public int getPacketSize()
				Accessor for packetSize
				
			public void run()
				while not done, call child action 
				
			public void stop()
  				Terminates the threads in run()
			
			public boolean isStopped()
				Returns true or false depending if the 2 threads are running or not
			
			public void StartAsThread()
				Takes sender and receiver threads and spins off their own threads
				
			abstract SynchronizedPacketQueue action(DatagramSocket datagramSocket, SynchronizedListQueue queue)
				Either sends or receives packets depending how subclass is running
 
 
 
 
 */

public abstract class DatagramSenderReceiver implements Runnable 
{
	private AtomicBoolean 	done;
	private DatagramSocket 	datagramSocket;
	private int 			packetSize;
	private SynchronizedPacketQueue queue;
	private boolean			debugOn;
	
	public DatagramSenderReceiver(DatagramSocket datagramSocket, SynchronizedPacketQueue queue, int packetSize) throws SocketException
	{
		//creates a DatagramSenderReceiver object
		
		int port;
		
		if( queue == null )				{throw new IllegalArgumentException("Queue in DatagramSenderReceiver is equal to null, try again.");}
		if( packetSize < 0 ) 			{throw new IllegalArgumentException("DatagramPacket size must be greater than 0 in DatagramSenderReceiver.  Try again.");}
		if( datagramSocket == null)		{throw new IllegalArgumentException("inetSocket in DatagramSenderReceiver can't be equal to null. Try again");}
		//how can we check inetAddressSocket?
		
		System.out.println("address of socket in DtagramsenderReceiver class: "+ datagramSocket.getPort());
		
		this.datagramSocket = datagramSocket;
		this.queue 			= queue;
		this.packetSize		= packetSize;
		
		//do we set the AtomicBoolean in the constructor?	when done=true, the object is useable else it is set to false
		this.done			= new AtomicBoolean(false);
		this.debugOn		= true;
		
	} 
	
	
	public int getPort()
	{
		//return this.datagramSocket.getPort();
		return this.datagramSocket.getLocalPort();
	}

	public int getPacketSize()
	{
	//Access for packetSize
		return this.packetSize;
	}
		
	public void run() 
	{
	//while not done, call child action 
		
		
		while( done.get() == false )
		{
			//debug("DatagramSenderRecevier.run(), the queue being read");
			try 
			{
				this.action(this.datagramSocket, this.queue);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}

		}//end while
		
	}
		
	public void stop()
	{
		//Terminates the threads in run()
		this.done.set(true);
	}
	
	public boolean isStopped()
	{
		//Returns true or false depending if the 2 threads are running or not, check AtomicBoolean
		return this.done.get();
	}
	
	public Thread startAsThread() throws SocketException
	{
		//creates new thread
		//start calls run method 
		Thread thread;
		thread = new Thread(this); 
		
		thread.start();
		
		return thread;
	}
		
	abstract void action(DatagramSocket datagramSocket, SynchronizedPacketQueue queue) throws IOException;
		//Either sends or receives packets depending how subclass is running
	
	public void debug(String message)
	{
		if(this.debugOn == true)
		{
			System.out.println(message);
		}
	}
	public SynchronizedPacketQueue getSynchronizedPakcetQueue()
	{
		return this.queue;
	}
	
	public synchronized void addToQueue(SynchronizedPacketQueue queue)
	{
		//this is so the gossip partners can add addressed datagrampackets to the queue
		this.queue.enQueue(queue);
	}//end addToQueue
	
}
