import java.net.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.lang.*;
/*
	Lauren M DiGregorio
	  April 12, 2015
	  
	  For the final project
	  This class is the vehicle to send datagramPackets
	  
	  Class Variables
	  	none
		
	  Constructors
	  	
	  	DatagramSender(InetSocketAddress inetSocketAddress, IncomingPacketQueue queue, int packetSize)
	  		Creates a DatagramSender object
	
		Methods
			public void action(DatagramSocket datagramSocket, SynchronizedPacketQueue queue)
				Action sends the packet out
				
			

*/
public class DatagramSender extends DatagramSenderReceiver
{
	public DatagramSender(DatagramSocket datagramSocket, OutgoingPacketQueue queue, int packetSize) throws SocketException
	{
		super(datagramSocket, queue, packetSize);
		System.out.println("port in datagramSender: " + datagramSocket.getPort());
	}
	
	public void action(DatagramSocket datagramSocket, SynchronizedPacketQueue queue) throws IOException
	{
		
		//you are not creating the datagramPacket here, you should be getting it from the queue, and 
		//sending it to the address designated in the datagramPacket
		
		if(datagramSocket == null ) 	{ throw new IllegalArgumentException("datagramSocket in Datagram Receiver is equal to null, try again.");}
		if(queue == null)				{ throw new IllegalArgumentException("queue in Datagram Receiver is equal to null, try again.");}
		
		//get rid of while loop and just send one thing at a time, just the first thing in the queue


		if( queue.peek() != null && queue.isEmpty() == false)
		 {
		
			DatagramPacket datagramPacket = queue.deQueue();
			System.out.println("Send packet == " + datagramPacket.getData().length);
			datagramSocket.send(datagramPacket);
			 try {
	                Thread.sleep(20);
	            } 
	            catch (InterruptedException e) { throw new IllegalArgumentException("Sleep method in datagramSender did not work"); }
			  
		 }
			 
		 
    }// end ection
	
	
	
	public void print(String message)
	{
		System.out.println(message);
	}//print
}

