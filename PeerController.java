import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PeerController
{
	private CommandProcessor 	commandProcessor;
	private IncomingPacketQueue commandQueue;
	private DatagramReceiver 	commands;
	
	private IncomingPacketQueue incomingPackets;
	private DatagramReceiver 	peer;
	private InetSocketAddress 	peerControllerAddress;
	
	private DatagramSender 		sender;
	
	private OutgoingPacketQueue queue;
	
	private InetSocketAddress 	uiControllerAddress;
	private InetSocketAddress   uiControllerAddressReceiving;

	public PeerController(PortNumber peerPort, PortNumberForSending portNumberToUI, PortNumberForReceiving portNumberFromUI, int maxDatagramPacketSize) throws SocketException, UnknownHostException
	{
		if(peerPort.get() < 0){throw new IllegalArgumentException("PeerPort is less than zero");}
		if(portNumberToUI.get() < 0){throw new IllegalArgumentException("UI sending port is less than zero");}
		if(portNumberFromUI.get() < 0){throw new IllegalArgumentException("UI receiving port is less than zero");}
		if(maxDatagramPacketSize < UDPMessage.getMinimumPacketSizeInBytes()){throw new IllegalArgumentException("maxDatagramPacketSize less than minimum packet size");}
		if(maxDatagramPacketSize > UDPMessage.getMaximumPacketSizeInBytes()){throw new IllegalArgumentException("maxDatagramPacketSize greater than maximum packet size");}
		
		//creates all of the instance variables and registers all of the commands.  Essentially creates 
		//everything we could possibly need to do everything!
		
		//not sure what IP to set the peercontrolleraddress to
		this.peerControllerAddress = new InetSocketAddress(peerPort.get());
		this.incomingPackets = new IncomingPacketQueue();
		this.peer = new DatagramReceiver(new DatagramSocket(peerPort.get()), this.incomingPackets, maxDatagramPacketSize);
		this.peer.startAsThread();
		
		this.uiControllerAddressReceiving = new InetSocketAddress(InetAddress.getLocalHost(),portNumberFromUI.get());
		this.commandQueue = new IncomingPacketQueue();
		this.commands = new DatagramReceiver(new DatagramSocket(portNumberFromUI.get()), this.commandQueue, maxDatagramPacketSize); 
		this.commands.startAsThread();
		
		this.uiControllerAddress = new InetSocketAddress(portNumberToUI.get());
		queue = new OutgoingPacketQueue();
//*****************Not sure what port to initialize the sender to since it can send both places!
		this.sender = new DatagramSender(new DatagramSocket(portNumberToUI.get()), this.queue, maxDatagramPacketSize);
		this.sender.startAsThread();
		
		
//I don't know how/what to do or the commandProcessor portion
		
		//commandProcessor = new CommandProcessor()

		//Now it makes an instance of the GossipPartners and we must manually add IP Addresses to this since 
		//the join doesn't work because MultiCasting is gone from the UST Network.
		//Self
		//GossipPartners.getInstance().addPartner(new GossipPartner(new InetSocketAddress(InetAddress.getLocalHost(), 12345), this.queue));
		//Jack
		//GossipPartners.getInstance().addPartner(new GossipPartner(new InetSocketAddress("10.20.24.132", 12345), this.queue));
		//Jarvis
		GossipPartners.getInstance().addPartner(new GossipPartner(new InetSocketAddress("140.209.121.104", 12345), this.queue));
		//Josh
		//GossipPartners.getInstance().addPartner(new GossipPartner(new InetSocketAddress("140.209.121.209", 12345), this.queue));
	}

	public void start() throws UnsupportedEncodingException
	{
		DatagramPacket 			datagramPacket;
		AtomicBoolean 			done;
		UDPMessage 				gossipUDPMessage;
		boolean 				idle;
		Resource 				resource;
		RequestFromUIController request;
		UDPMessage 				udpMessage;
	
		System.out.println("Started peer controller.");
		done = new AtomicBoolean(false);
		
		//Now the main loop!
		while(!done.get())
		{
			idle = true; // idle literally just keeps track of if we are processing a packet or not. true
			 				 // means we are "idle" and doing nothing to the packet, false means we are not idle 			  
			 				   // and are updating/sending/queuing the packets

			datagramPacket = this.incomingPackets.deQueue(); //deQueue from packetqueue, will return null if empty
			if(datagramPacket != null)
			{
				System.out.println("Got an incoming packet!");
				idle = false;

				//make a UDPMessage from dataGramPacket
				udpMessage = new UDPMessage(datagramPacket);
				
				System.out.println("Incoming packet says originating request ID is " + udpMessage.getID2().toString());
				 
				//give UDPMessage to GossipPartners because no matter what it is it has to get sent to the 
				//GossipPartners to be sent to the Peer Community
				int ttl = udpMessage.getTimeToLive().get();
				
				if(ttl > 0)
				{
					ttl = ttl-1;
					udpMessage = new UDPMessage(udpMessage.getID1(), udpMessage.getID2(), new TimeToLive(ttl), udpMessage.getMessage());
					
					GossipPartners.getInstance().send(udpMessage);
				}
				//get the Request associated with the ID from the deQueued packet by pulling it from the 
				//RequestsManager.  if it is not there, it will return null and we will process it, if it is 
				//there, we will do nothing
				request = RequestsManager.getInstance().getRequest(udpMessage.getID2());
				
				if(request != null)
				{
					System.out.println("Found an existing request");
					//call updateRequest() method on udpMessage, which is either a find or get request so 
					//the RequestFromUIControllerToFindResources or the 
					//RequestFromUIControllerToGetaResource will take care of them!
				}


				//now it's not a request to get or find resources from the UI, so it must be from the Peer (
				//the order of this is arbitrary, just how I decided to think about it with some jarvis help)
				else
				{
					resource = ResourceManager.getInstance().getResourceThatMatches(udpMessage.getID2()); //name of this method is not correct, i 												 
																//don't have my code with me to check it! 												 
																//But it just gets the resource 														 
																//corresponding to the ID2 from the 												 	 
																//ResourceManager and handles it if it is 												 
																//not null and if it is null (which means 												 
																//it is not a request for our resource!)

					if(resource != null)
					{
						//if we got a resource back, it's a getRequestFromPeer
						new GetRequestFromPeer(udpMessage).run(); //this might have a .run() after
					}
					else
					{
						//if it hasn't been anything else (and the ID2 doesn't correspond to a Resource), 
						//then it's a find request from a peer!
						new FindRequestFromPeer(udpMessage).run(); //same possible .run()
					}
				}//else updateRequest null
			}//if DatagramPacket not null
			
			idle = true;
			
			//Check command queue
			datagramPacket = this.commandQueue.deQueue();
			if(datagramPacket != null)
			{
				String 									input;
				String 									command;
				String 									parameters;
				RequestFromUIControllerToFindResources 	findRequest;
				RequestFromUIControllerToGetAResource	getRequest;
				UDPMessage								udpFindMessage;
				UDPMessage								udpGetMessage;
				
				idle = false;
				
				input = new String(datagramPacket.getData(), "UTF-8");
				input = input.trim();
				System.out.println("Got command!" + input);
	
				command = input.split(";")[0];
				parameters = input.split(";")[1];
				
				if(command.equalsIgnoreCase("find"))
				{
					udpFindMessage = new UDPMessage(ID.idFactory(), ID.idFactory(), new TimeToLive(5), parameters);
				//	findRequest = new RequestFromUIControllerToFindResources(udpFindMessage.getID1(), this.uiControllerAddress);
					
				//	RequestsManager.getInstance().insertRequest(findRequest);
					
					//GetRequestFromPeer getRequestFromPeer = new GetRequestFromPeer(udpFindMessage);
					
					GossipPartners.getInstance().send(udpFindMessage);
					
					System.out.println("Sent out find request with request ID of " + udpFindMessage.getID1().toString());
				}
				else if(command.equalsIgnoreCase("get"))
				{
					
				}
			}//command queue check
		}//while(!done)
	}//start
	
	public static void main(String[] args)
	{
		try
		{
			PeerController peerController = new PeerController(new PortNumber(12345), new PortNumberForSending(54321), new PortNumberForReceiving(12346), 512);
			RequestsManager.newInstance();
			GossipPartners.newInstance();
			ResponsesFromFindRequests.newInstance();
			peerController.start();
		}
		catch(UnknownHostException uhe){uhe.printStackTrace();}
		catch(SocketException se){se.printStackTrace();}
		catch(IOException ioe){ioe.printStackTrace();}
			
	}//main
}//class