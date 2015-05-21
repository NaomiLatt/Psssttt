import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Scanner;
import java.net.*;

public class TestSend 
{

	public static void main(String[] args) throws SocketException 
	{
		// TODO Auto-generated method stub
	/*	DatagramSender 			datagramSender;
		DatagramSocket	 		datagramSocket;
		OutgoingPacketQueue 	queue;
		Scanner 				userInput;
		DatagramPacket			datagramPacket;
		byte[]					buffer;
		
		System.out.print("Enter anything to allow receiver to run.");
		
		//I was thinking this was stopping the code from executing until a user inputs information 
		//that way i can get the 
		userInput = new Scanner(System.in);
		System.out.println(userInput);
		
		buffer				= new byte[100];
		datagramSocket 		= new DatagramSocket(54321);
		queue 				= new OutgoingPacketQueue();
		
		for(int i = 0; i < buffer.length; i++ )
		{
			buffer[i] = 1;
		}
		
		
		datagramPacket 		= new DatagramPacket(buffer, buffer.length);
		
		queue.enQueue(datagramPacket);
		
		datagramSender = new DatagramSender(datagramSocket, queue, 100);
		//datagramSender.StartAsThread().run();
		datagramSender.StartAsThread().run();
	*/
		
		startServer();
        startSender();
		
		
	}
	  public static void startSender() 
	  {
	        (new Thread() 
	        
	        {
	            @Override
	            public void run() 
	            {
	            	byte[]		   buffer;
	        		DatagramPacket datagramPacket;
	        		DatagramSocket datagramSocket;
	        		try
	        		{
	        			datagramSocket = new DatagramSocket();
	        			
	        			print("sender DatagramSocket created!");
	        			buffer = new byte[2000];

	        			print("sender buffer created!");
	        			datagramPacket = new DatagramPacket(buffer,buffer.length);
	        			
	        			
	        			datagramPacket.setAddress(InetAddress.getByName("192.168.141.1"));
	        			print("sender set address worked!");
	        			
	        			datagramPacket.setPort(54321);
	        			print("sender setPort worked!");
	        			
	        			datagramPacket.setData("Hello!".getBytes());
	        			datagramSocket.send(datagramPacket);
	        			print("sender datagramPacket sent!");
	        			
	        		}
	        		catch(SocketException se)		{print(se.getMessage());se.printStackTrace();}
	        		catch(UnknownHostException uhe)	{print(uhe.getMessage());}
	        		catch(IOException ioe)			{print(ioe.getMessage());}
	            }
	            
	            
	            
	        public void print(String message)
	    	{
	    		System.out.println(message);
	    	}//print
	            
	        }).start();
	        
	       
	    }

	    public static void startServer() {
	        (new Thread() {
	            @Override
	            public void run() 
	            {
	            	
	            	byte[]		   buffer;
	        		DatagramPacket datagramPacket;
	        		DatagramSocket datagramSocket;
	        		try
	        		{
	        			datagramSocket 	= new DatagramSocket(54321);
	        			print("server DatagramSocket created!");
	        			
	        			buffer 			= new byte[2000];
	        			print("server buffer created!");
	        			
	        			datagramPacket 	= new DatagramPacket(buffer,buffer.length);
	        			
	        			datagramSocket.receive(datagramPacket);
	        			print(new String(datagramPacket.getData(),0,datagramPacket.getLength()));


	        		}
	        		catch(SocketException se)		{print(se.getMessage());se.printStackTrace();}
	        		catch(UnknownHostException uhe)	{print(uhe.getMessage());}
	        		catch(IOException ioe)			{print(ioe.getMessage());}
	            }
	        
	        
	            public void print(String message)
	        	{
	        		System.out.println(message);
	        	}//print
	        
	        
	        }).start();
	    }
}
