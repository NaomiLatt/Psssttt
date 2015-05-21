import java.net.*;

public class TestDatagramSenderReceiver 
{

	public static void main(String[] args) throws SocketException, UnknownHostException, InterruptedException 
	{
		
		//This is how it should work
		DatagramSender 		datagramSender;
		DatagramReceiver 	datagramReceiver;
		OutgoingPacketQueue	queueOut;
		IncomingPacketQueue	queueIn;
		DatagramSocket		datagramSocket;
		byte[]		   		buffer;
		DatagramPacket		datagramPacket;
		DatagramPacket		datagramPacket2;
		DatagramPacket		datagramPacket3;
		
		//need to create instances of all variables
		datagramSocket 	= new DatagramSocket();
		queueOut		= new OutgoingPacketQueue();
		queueIn			= new IncomingPacketQueue();
		buffer 			= new byte[100];
	
	
		/*
		 * 
		 * This tests a non empty queue
		*/
		datagramPacket	= new DatagramPacket(buffer, buffer.length);
		datagramPacket2	= new DatagramPacket(buffer, buffer.length);
		//datagramPacket3	= new DatagramPacket(buffer, buffer.length);
		
		
		//add data to datagramPacket to be sent
		datagramPacket.setData("This is twerking!!!".getBytes());
		datagramPacket2.setData("This is twerking2!!!".getBytes());
		//datagramPacket3.setData("This is twerking3!!!".getBytes());
		
		
		//set the address (local host) and port on the datagramPacket
		datagramPacket.setAddress(InetAddress.getByName("192.168.141.1"));
		datagramPacket.setPort(54321);
		datagramPacket2.setAddress(InetAddress.getByName("192.168.141.1"));
		datagramPacket2.setPort(54321);
		//datagramPacket3.setAddress(InetAddress.getByName("192.168.141.1"));
		//datagramPacket3.setPort(54321);
		
		//enqueue the datagramPacket in the OutgoingPacketQueue
		queueOut.enQueue(datagramPacket);
		queueOut.enQueue(datagramPacket2);
		//queueOut.enQueue(datagramPacket3);
		
		
		
		
		
		
		
		/*This tests an empty queue
		 * 
		 * 
		 */
		
		
		//create the sender and receiver objects
		datagramSender 		= new DatagramSender(datagramSocket, queueOut, 512);
		datagramReceiver	= new DatagramReceiver(new DatagramSocket(54321), queueIn, 512);
		
		
		//run datagramReceiver and sender on their own threads
		datagramReceiver.startAsThread();
		datagramSender.startAsThread();
		
		
		
		//SLEEP IS CAUSING PROBALEMS OR IS SIGNIFICANT
		Thread.sleep(3);
		datagramReceiver.stop();
		datagramSender.stop();
		
		System.out.print("Give me the second packet: "+datagramReceiver.getFindObject(new Integer(0)));
		
		
		
		System.out.println("Sending and receiving is done");
		
		
	}

	
	
}
