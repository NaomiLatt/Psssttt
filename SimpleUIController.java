import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleUIController
{
	private DatagramSocket 			datagramSocket;
	private boolean 				done;
	private InetSocketAddress 		inetSocketAddress;
	private PortNumberForReceiving 	incomingPortNumber;
	private PortNumberForSending	outgoingPortNumber;
	
	public SimpleUIController(PortNumberForReceiving incomingPortNumber, PortNumberForSending outgoingPortNumber) throws UnknownHostException, SocketException
	{
		this.datagramSocket 	= new DatagramSocket();
		this.inetSocketAddress  = new InetSocketAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()), incomingPortNumber.get());
		this.incomingPortNumber = incomingPortNumber;
		this.outgoingPortNumber = outgoingPortNumber;
	}
	
	public void start() throws IOException
	{
		String			command;
		String			input;
		String			parameters;
		DatagramPacket  packetToPeer;
		byte[]			packetContents;
		BufferedReader 	reader;
		
		System.out.println("Started UI controller.");
		
		this.done = false;
		
		while(!this.done)
		{
			reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Commands should be of format: command;parameters\n");
			System.out.print("Enter command: ");
			
			input = reader.readLine();
			input = input.replaceAll("[\n\r]", "");
			
			if(input.split(";").length < 2)
			{
				System.out.println("Invalid command format. Try again.");
			}
			else
			{
				command = input.split(";")[0];
				parameters = input.split(";")[1];
				
				if(command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit"))
				{
					this.done = true;
				}
				else if(command.equalsIgnoreCase("find"))
				{
					System.out.println("Sending find command to PeerController...\n");
					packetContents = Utilities.getBytes(input);
					
					packetToPeer = new DatagramPacket(packetContents, packetContents.length, this.inetSocketAddress);
					
					packetToPeer.setAddress(InetAddress.getLocalHost());
					packetToPeer.setPort(this.outgoingPortNumber.get());

					this.datagramSocket.send(packetToPeer);
				}//elseif
				else if(command.equalsIgnoreCase("get"))
				{
					
				}
			}//else
		}//while not done
	}//start
	
	public static void main(String[] args)
	{
		SimpleUIController simpleUIController;
		try
		{
			simpleUIController = new SimpleUIController(new PortNumberForReceiving(54321), new PortNumberForSending(12346));
			simpleUIController.start();
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}//class
