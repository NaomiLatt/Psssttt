import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;


/*
	Lauren M DiGregorio
	  April 18, 2015
	  
	  For the final project
	  This class performs the commands on the datagrampackets
	  
	  Class Variables
	  	commandProcessor
	  		this is type COmmandProcessor, expains what needs to happen depending on the command
	  		
	  	done
	  		boolean that indicates if the command is finished
	  	
	  	incomingPacketsFromPeerQueue
	  		is an IncomingPacketQueue that holds packets coming into the system
	  		
	  	outgoingPacketsToPeerQUeue
	  		is an OutgoingPacketQueue that holds packets going out of the system
	  		
	  	receiveFromPeer
	  	 	DatagramReceiver that does the actual receiving of packets 
	  	 	
	  	sendToPeer
	  		DatagramReceiver that does the actual sending of the packets
	  		
	  		
		
	  Constructors
	  	
	  	UIController(PortNumberForReceiving incomingPartNumber, PortNumberForSending outgoingPortNumber, int packetSize)
	
	Methods
		public void start()
			starts processing commands on incoming packets

*/
public class UIController 
{
	private CommandProcessor 				commandProcessor; 				//	this is type COmmandProcessor, expains what needs to happen depending on the command
	private boolean 						done; 							//	boolean that indicates if the command is finished
	private IncomingPacketQueue 			incomingPacketsFromPeerQueue;	//	is an IncomingPacketQueue that holds packets coming into the system
	private IncomingPacketQueue 			uiQueue;						//  queue that receives stuff from internal UI
	private OutgoingPacketQueue 			outgoingPacketsToPeerQueue;		//	is an OutgoingPacketQueue that holds packets going out of the system
	private DatagramReceiver 				receiveFromPeer;				//	DatagramReceiver that does the actual receiving of packets 
	private DatagramReceiver				receiveFromUI;					//  receiver listening for commands sent on the UI
	private DatagramSender 					sendToPeer;  					//  DatagramReceiver that does the actual sending of the packets
	private int								portIn;
	private int								portInUI;
	private int								portOut;
	private ResourceManager					resourceManager;				//	holds all available resources on my machine
	private InetSocketAddress				inetSocketAddress;				// holds your own IP information
	private RequestsManager					requestsManager;
	private Map<Integer, DatagramPacket> 	allFinds;
	
	
	
	public UIController(PortNumberForReceiving incomingPortNumber, PortNumberForSending outgoingPortNumber, int packetSize) throws SocketException, UnknownHostException
	{
		//register all commands here
		//IncomingPortNumber is the port that all peers are listening 
		//OutgoingPortNumber is the port we are using internally
		//Third queue doesnt need port number for sending, is the port we are sending packets out through, does not need to be bound to a port
		
		CommandError 		commandError;
		CommandNone 		commandNone;
		UIControllerCommand	begin;
		
		DatagramSocket 	socketIn;
		DatagramSocket 	socketInUI;
		DatagramSocket 	socketOut;
		
		
		
		if(incomingPortNumber.get() < 0){ throw new IllegalArgumentException("UIContoller, incomingPortNumber number being set must be greater than zero");}
		if(outgoingPortNumber.get() < 0){ throw new IllegalArgumentException("UIContoller, outgoingPortNumber number being set must be greater than zero");}
		
		commandError 	= new CommandError();
		commandNone 	= new CommandNone();
		this.portInUI	= 244;
		
		begin			= new Begin("Begin", "This command will begin the peer");
		socketOut		= new DatagramSocket();
		socketIn		= new DatagramSocket(incomingPortNumber.get());
		
		//internal listener, doesn't need to be set from the outside
		socketInUI		= new DatagramSocket(this.portInUI);
		
		this.inetSocketAddress				= new InetSocketAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()), incomingPortNumber.get());
		this.commandProcessor 				= new CommandProcessor(commandError, commandNone);
		this.done							= true;
		this.incomingPacketsFromPeerQueue 	= new IncomingPacketQueue();
		this.uiQueue						= new IncomingPacketQueue();
		this.outgoingPacketsToPeerQueue		= new OutgoingPacketQueue();
		this.portIn							= incomingPortNumber.get();
		this.portOut						= outgoingPortNumber.get();
		
		//how do we want to set the packetSize?
		this.receiveFromPeer 	= new DatagramReceiver	(  socketIn  , this.incomingPacketsFromPeerQueue, 600  	);
		this.sendToPeer 		= new DatagramSender	(  socketOut  , this.outgoingPacketsToPeerQueue, 600  	);
		this.receiveFromUI		= new DatagramReceiver  (  socketInUI , this.uiQueue, 600 );																				
		
		this.commandProcessor.register(new CommandHelp());
		this.commandProcessor.register(new Begin("Begin", "This will begin the peer"));
		this.commandProcessor.register(new Find("Find", "This will Find a resource based on the parameters passed"));
		this.commandProcessor.register(new Get("Get", "This will coninuously get a resource from a peer"));
		
		requestsManager = RequestsManager.getInstance();
		
		//Jarvis
		GossipPartners.getInstance().addPartner(new GossipPartner(new InetSocketAddress("140.209.121.104", 12345), this.outgoingPacketsToPeerQueue));
		//Josh
		GossipPartners.getInstance().addPartner(new GossipPartner(new InetSocketAddress("140.209.121.209", 12345), this.outgoingPacketsToPeerQueue));
		
		
	}

	public void start() throws IOException
	{
		//starts processing commands on incoming packets
		BufferedReader userInput;
		String command;
		Command	commandToSendToProcessor;
		
		this.receiveFromPeer.startAsThread();
		this.sendToPeer.startAsThread();
		
		this.done = false;
		
		while(this.done == false)
		{
			
			userInput = new BufferedReader(new InputStreamReader(System.in));
	        System.out.print("Enter Command: ");
	        command = userInput.readLine();
	       
	        command = command.replaceAll("[\n\r]", "");
	        System.out.println("command entered : "+command);
	        
	        // put full line into command processor
	        commandToSendToProcessor = this.commandProcessor.getCommand(command);
	        
	        System.out.println("Parameters are set in UI before sent to peer : "+commandToSendToProcessor.getParameters());
	        commandToSendToProcessor.run();
	        
	        try 							{ Thread.sleep(10);	} 
	        catch (InterruptedException e) 	{ e.printStackTrace();}
	     
        
        		

	        
	        
	        
	        
	        
	        
        	}//end while
        
	        
		
		/*
		 * 
		 * 		while(!done.get())
		{
			idle = true; // idle literally just keeps track of if we are processing a packet or not. true
			 				 // means we are "idle" and doing nothing to the packet, false means we are not idle 			  
			 				   // and are updating/sending/queuing the packets
		
			
			if(!this.incomingPackets.isEmpty())
			{
				
				datagramPacket = this.incomingPackets.deQueue(); //deQueue from packetqueue, will return null if empty
				//if(datagramPacket != null)
				//{
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
						
				//		RequestsManager.getInstance().insertRequest(findRequest);
						
						//GetRequestFromPeer getRequestFromPeer = new GetRequestFromPeer(udpFindMessage);
						
						
						
						
						GossipPartners.getInstance().send(udpFindMessage);
						
						System.out.println("Sent out find request with request ID of " + udpFindMessage.getID1().toString());
					}
					else if(command.equalsIgnoreCase("get"))
					{
						
					}
				}//command queue check
				
				
				
				
			//}//end if
			
		}//while(!done)
		 */
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	   /*     if(command.equals("Exit") || command.equalsIgnoreCase("Quit") || command.equals("exit") || command.equals("quit") )
			{
			
	        	this.done = true;
	        	this.receiveFromPeer.stop();
	        	this.sendToPeer.stop();
				//need to kill peer too?
				
			}
	        else if(command.equals("Find") || command.equalsIgnoreCase("find"))
	        {
	        	System.out.print("What would you like to find: ");
	        	itemToSearchFor = userInput.readLine();
	        	
	        	commandToSend	= (UIControllerCommand)this.commandProcessor.getCommand("Find");
				
				System.out.println("before send2.  Command is: " + commandToSend.getCommandName());
				commandToSend.sendToPeer();
	        }
			else
			{
				
				//call sendToPeer to send command to peer
				System.out.println("before send");
				
				commandToSend	= (UIControllerCommand)this.commandProcessor.getCommand(command);
				
				System.out.println("before send2.  Command is: " + commandToSend.getCommandName());
				commandToSend.sendToPeer();
				System.out.println("after send");
				
			}
	       
		*/
			
	}//end start method
	
	public void enQueueFromPeer(OutgoingPacketQueue queue)
	{
		this.outgoingPacketsToPeerQueue.enQueue(queue);
	}
	
	
	
	
	
	public abstract class UIControllerCommand extends Command
	{
		/*
		Lauren M DiGregorio
		  April 18, 2015
		  
		  For the final project
		  This class chooses the correct command to be exceuted
		  
		  Class Variables
		  	none	
		  		
			
		  Constructors
		  	UIControllerCommand()
		  		create a Connandone type object
		  	
		  	UIControllerCommand(String commandName, String description)
		  		Creates the command object to be executed
		
		Methods
			public CommandProcessor getCommandProcessor()
				returns the correct command processor to execute the command to be processed
			
			public boolean getDoneFlag()
				returns done flag boolean
			
			public void print(String message)
				prints a message to the screen
			
			public void println(String data)
				prints data to the screen
			
			public oivd setDoneFlag(boolean flag)
				sets done flag in above class
			
			public void sendToPeer()
				sends command to peer
			
			

	*/
		
		public UIControllerCommand()
		{
			//User entered no command so the CommandNone object is created and executed
			super();
			
		}
  		
  	
	  	public UIControllerCommand(String commandName, String description)
	  	{
	  		//Creates the command object to be executed
	  		//make sure it is a valid command
	  		//WHat is this class for?
	  		//if(commandProcessor.commandExists(commandName) == false )	{ throw new IllegalArgumentException("UIControllerCommand, commandName not found in hash table");	}
	  		super(commandName, description);
	  		
	  		
	  	}
	
	
		public CommandProcessor getCommandProcessor()
		{
			//returns the correct command processor to execute the command to be processed
			return commandProcessor;
		}
		
		public boolean getDoneFlag()
		{
			//returns done flag boolean
			return done;
		}
		
		public void print(String message)
		{
			//prints a message to the screen
			System.out.print(message);
		}
		
		public void println(String data)
		{
			//prints data to the screen
			System.out.println(data);
		}
		
		public void setDoneFlag(boolean flag)
		{
			//sets done flag in above class
			done = flag;
		}
		
		public void sendToPeer(byte[] buffer) throws UnknownHostException
		{
	/*		//sends command to peer
			//takes this object command, formats it to the correct protcol 
			//first I am going to make sure it sends properly and then I will add the additional formatting
			DatagramPacket 	datagramPacket;
			byte[]			buffer;
			
			//initalize the buffer and packet  MAKE SURE THE OTHERS WATCH THAT THE BUFFER IS BIG ENOUGH TO WORK
			buffer 			= new byte[600];
			datagramPacket 	= new DatagramPacket(buffer, buffer.length);
			
			//set data with command name
			datagramPacket.setData(this.getCommandName().getBytes());
			
			//set the address (local host) and port on the datagramPacket
			//We eventually do not want this to be hard coded in here, we will use UDPMessge to create the datagramPacket
			//datagramPacket.setAddress(InetAddress.getByName("192.168.141.1"));
			datagramPacket.setAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
			System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress());
			//System.out.println("I want to replace with"+ inetSocketAddress.getLocalHost().getHostAddress());
			
			//use class variable
			//Actually i think this can be hard coded
			//datagramPacket.setPort(244);
			
			
			//What is the logic to set the port, how do you know 
			datagramPacket.setPort(portIn);
			System.out.println("UIController.sendToPeer, sendToPeer.getport()" +sendToPeer.getPort());
			//datagramPacket.setPort(portOut);
			
			System.out.println("Packet was sent with this name: " + this.getCommandName());
			outgoingPacketsToPeerQueue.enQueue(datagramPacket);
			
			*/
			
			//sends command to peer
			//takes this object command, formats it to the correct protcol 
			//first I am going to make sure it sends properly and then I will add the additional formatting
		
			
			
			
			//class sends packet to UI and to peer community
			DatagramPacket 	datagramPacket1;
			DatagramPacket 	datagramPacket2;
			UDPMessage		udpMessageUI;
			UDPMessage		udpMessageToPeers;
			//byte[]			buffer;
			
			//initalize the buffer and packet  MAKE SURE THE OTHERS WATCH THAT THE BUFFER IS BIG ENOUGH TO WORK
			//buffer 			= new byte[600];
			datagramPacket1 		= new DatagramPacket(buffer, buffer.length);
			datagramPacket2			= new DatagramPacket(buffer, buffer.length);
			
			//requestsManager.insertRequest(udpMessage);
			
			//set data with command name
			datagramPacket1.setData(buffer);
			datagramPacket1.setAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
			datagramPacket1.setPort(portInUI);
			
			//send to peers
			datagramPacket2.setData(buffer);
			
			udpMessageUI		= new UDPMessage(datagramPacket1);
			udpMessageToPeers	= new UDPMessage(datagramPacket2);
			
			//set the address (local host) and port on the datagramPacket
			//We eventually do not want this to be hard coded in here, we will use UDPMessge to create the datagramPacket
			//datagramPacket.setAddress(InetAddress.getByName("192.168.141.1"));
			
			System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress());
			//System.out.println("I want to replace with"+ inetSocketAddress.getLocalHost().getHostAddress());
			
			int ttl = udpMessageToPeers.getTimeToLive().get();
			
			if(ttl > 0)
			{
				ttl = ttl-1;
				udpMessageToPeers = new UDPMessage(udpMessageToPeers.getID1(), udpMessageToPeers.getID2(), new TimeToLive(ttl), udpMessageToPeers.getMessage());
				
				GossipPartners.getInstance().send(udpMessageToPeers);
			}
			
			
			//System.out.println("Packet was sent with this name: " + this.getCommandName());
			outgoingPacketsToPeerQueue.enQueue(datagramPacket1);
			
		}
	
	}// end UIControllerCommand class
	
	public class CommandError extends UIControllerCommand
	{
		/*
		Lauren M DiGregorio
		  April 18, 2015
		  
		  For the final project
		  This class is create when a user passes unrecognized commands
		  
		  Class Variables
		  	none	
		  		
		  Constructors
		  	CommandError()
		  		creates a commandError object
		
		Methods
			public void execute()
				implements abstract method from Command
				

	*/
		
		public CommandError()
  		{
			//creates a commandError object
  		}

		public void run()
		{
			//implements abstract method from Command
			System.out.print("The command you are trying is not available in the registry. Either register it or use another command.");
		}
	}//end Command Error
	
	
	
	public class CommandNone extends UIControllerCommand
	{
		/*
		Lauren M DiGregorio
		  April 18, 2015
		  
		  For the final project
		  This class is created when a user types no commands
		  
		  Class Variables
		  	none	
		  		
		  Constructors
		  	CommandNone()
		  		creates a CommandNone object
		
		Methods
			public void execute()
				implements abstract method from Command
				

	*/
		
		public CommandNone()
  		{
			//creates a commandError object
			
  		}

		public void run()
		{
			//implements abstract method from Command
			System.out.println("The command you typed does nothing");
		}
	}//end CommandNone
	
	public class CommandHelp extends UIControllerCommand
	{
		/*
		Lauren M DiGregorio
		  April 18, 2015
		  
		  For the final project
		  This class is create when a user passes unrecognized commands
		  
		  Class Variables
		  	none	
		  		
		  Constructors
		  	CommandHelp()
		  		creates a CommandNone object
		
		Methods
			public void execute()
				implements abstract method from Command
	*/
		
		public CommandHelp()
  		{
			//creates a commandError object
			
  		}

		public void run()
		{
			//implements abstract method from Command
			Command[] list;
			list = commandProcessor.getAllCommands();
			System.out.println("Commands available: ");
			for(int i = 0; i < list.length; i++)
			{
				System.out.println(list[i].getCommandName() + ": " + list[i].getDescription() );
			}//end for
		}//end execute
		
	}//end Command Error
	
	
	public class Begin extends UIControllerCommand
	{
		/*
		Lauren M DiGregorio
		  April 18, 2015
		  
		  For the final project
		  This class is create when a user passes unrecognized commands
		  
		  Class Variables
		  	none	
		  		
		  Constructors
		  	CommandHelp()
		  		creates a CommandNone object
		
		Methods
			public void execute()
				implements abstract method from Command
	*/
		
		public Begin(String commandName, String description)
  		{
			//creates a commandError object
			super(commandName, description);
			
  		}

		public void run()
		{
			//implements abstract method from Command
			System.out.println("I am the begin command");
		}//end execute
		
	}//end class Being
	
	public class Find extends UIControllerCommand
	{
		/*
		Lauren M DiGregorio
		  April 18, 2015
		  
		  For the final project
		  This class is create when a user passes unrecognized commands
		  
		  Class Variables
		  	none	
		  		
		  Constructors
		  	CommandHelp()
		  		creates a CommandNone object
		
		Methods
			public void execute()
				implements abstract method from Command
	*/
		
		public Find(String commandName, String description)
  		{
			//creates a commandError object
			super(commandName, description);
			
  		}

		public void run() 
		{
			//implements abstract method from Command
			System.out.println("I am the Find command");
			
			//gets ids and inserts requests
			
			ID 			id1;
			ID 			id2;
			byte[] 		buffer;
			TimeToLive	timeToLive;
			RequestFromUIControllerToFindResources	requestFromUIControllerToFindResources;
			
			id1 		= ID.idFactory();
			id2 		= ID.idFactory();
			timeToLive 	= new TimeToLive(4);
			buffer		= new byte[512];
			
			
			//ARGS: 		(1)  src obj, 	(2)srcPos,     (3) Object des, (4)destPos, (5)Length
			
			
			//System.arraycopy(datagramPacket, 0, 		this.id, 0, ID.getLengthInBytes());
			
			
			//need to set data
			//System.arraycopy(data, 0, this.id1.getBytes(), 0, ID.getLengthInBytes());
			System.arraycopy(id1.getBytes(), 0, buffer, 0, ID.getLengthInBytes());
			
			//Copy second ID
			//System.arraycopy(data, ID.getLengthInBytes(), this.id2.getBytes(), 0, ID.getLengthInBytes());
			System.arraycopy(id2.getBytes(), 0, buffer, ID.getLengthInBytes(), ID.getLengthInBytes());
			
			//Copy TimeToLive
			//System.arraycopy(data, (ID.getLengthInBytes() * 2), this.timeToLive.getBytes(), 0, TimeToLive.getLengthInBytes());
			System.arraycopy(timeToLive.getBytes(), 0, buffer, (ID.getLengthInBytes() * 2), TimeToLive.getLengthInBytes());
			
			//Copy message
			//System.arraycopy(data, (ID.getLengthInBytes() * 2) + TimeToLive.getLengthInBytes(), this.message, 0, UDPMessage.getMinimumPacketSizeInBytes());
			System.arraycopy(this.getParameters().getBytes(), 0, buffer, (ID.getLengthInBytes() * 2) + TimeToLive.getLengthInBytes(), this.getParameters().getBytes().length);
		
			System.out.println("My parameters are set: "+this.getParameters());
			
			
			try 
			{
				requestFromUIControllerToFindResources = new RequestFromUIControllerToFindResources(id1, new InetSocketAddress(InetAddress.getLocalHost(), 244 ), outgoingPacketsToPeerQueue );
				requestsManager.insertRequest(requestFromUIControllerToFindResources);
			} 
			catch (UnknownHostException e2) 	{e2.printStackTrace();}
			
			
			try 							{ super.sendToPeer(buffer);} 
			catch (UnknownHostException e) 	{ e.printStackTrace();}
			
			
			
		}//end execute
		
	}//end class Find
	
	public class Get extends UIControllerCommand
	{
		/*
		Lauren M DiGregorio
		  April 18, 2015
		  
		  For the final project
		  This class is create when a user passes unrecognized commands
		  
		  Class Variables
		  	none	
		  		
		  Constructors
		  	CommandHelp()
		  		creates a CommandNone object
		
		Methods
			public void execute()
				implements abstract method from Command
	*/
		
		
		public Get(String commandName, String description)
  		{
			//creates a commandError object
			super(commandName, description);
			
			
  		}

		public void run()
		{
			//implements abstract method from Command
			System.out.println("I am the Get command");
	
			
			//gets ids and inserts requests
			DatagramPacket datagramPacket;
			ID 			requestID;
			ID 			resourceID;
			ID			randomID;
			byte[] 		buffer;
			TimeToLive	timeToLive;
			RequestFromUIControllerToFindResources	requestFromUIControllerToFindResources;
			UDPMessage	udpMessage;
			byte[]		payload;
			String		payloadInformation;
			String[]	splitPayload;	
			String		fileType;
			String		size;
			
			
			//need to fix this
			PartNumbers	partnumber = new PartNumbers(1);
			

			timeToLive 		= new TimeToLive(4);
			buffer			= new byte[512];
			datagramPacket 	= null;
			randomID		= ID.idFactory();
			splitPayload	= new String[2];
			
			//ARGS: 		(1)  src obj, 	(2)srcPos,     (3) Object des, (4)destPos, (5)Length
			
			
			//System.arraycopy(datagramPacket, 0, 		this.id, 0, ID.getLengthInBytes());
			
			
			//format this byte array to e a get request that can be sent and also
			//Need to use the original request ID and add resource ID
			//Look it up in AllRequests by using the RequestNumber, which is the parameter of the command
			//use parameter to look up the right request object
			
			
			//receiveFromPeer.getAllRequests().getRequest(this.getParameters());  RETURNS REQUEST ITEM
			try 									{   datagramPacket = receiveFromPeer.getAllRequests().getRequest(this.getParameters()).getDatagramPacket();	} 
			catch (UnsupportedEncodingException e1) {	e1.printStackTrace(); }
			
			//getRequest(String requestOption)
			
			if(datagramPacket != null )
			{
				//get all the correct information from it
				udpMessage 			= new UDPMessage(datagramPacket);
				requestID			= udpMessage.getID2();
				resourceID			= udpMessage.getID1();
				payload				= udpMessage.getMessage();
				
				
				try 
				{
					payloadInformation	= new String(payload, "UTF-8");
					System.out.println("PAYLOAD INFORMATION: " + payloadInformation);
					splitPayload = payloadInformation.split("#");
				} 
				catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
				
				
				
				
				//System.arraycopy(data, 0, this.id1.getBytes(), 0, ID.getLengthInBytes());
				System.arraycopy(requestID.getBytes(), 0, buffer, 0, ID.getLengthInBytes());
				
				//Copy second ID
				//System.arraycopy(data, ID.getLengthInBytes(), this.id2.getBytes(), 0, ID.getLengthInBytes());
				System.arraycopy(resourceID.getBytes(), 0, buffer, ID.getLengthInBytes(), ID.getLengthInBytes());
				
				//Copy TimeToLive
				//System.arraycopy(data, (ID.getLengthInBytes() * 2), this.timeToLive.getBytes(), 0, TimeToLive.getLengthInBytes());
				System.arraycopy(timeToLive.getBytes(), 0, buffer, (ID.getLengthInBytes() * 2), TimeToLive.getLengthInBytes());
				
				//Copy message
				//System.arraycopy(data, (ID.getLengthInBytes() * 2) + TimeToLive.getLengthInBytes(), this.message, 0, UDPMessage.getMinimumPacketSizeInBytes());
				System.arraycopy(randomID.getBytes(), 0, buffer, (ID.getLengthInBytes() * 2) + TimeToLive.getLengthInBytes(), randomID.getBytes().length);
				
			}
			
			
			
			
		
		//	System.arraycopy(PartNumbers,partNumber.getBytes(), 0, buffer, (ID.getLengthInBytes() * 2) + TimeToLive.getLengthInBytes(), randomID.getBytes().length);
			
			System.out.println("My parameters are set: "+this.getParameters());
			
			//public RequestFromUIControllerToGetAResource (ID requestID, ID resourceID, long resourceLength)
/*			try 
			{
				requestFromUIControllerToFindResources = new RequestFromUIControllerToFindResources(id1, new InetSocketAddress(InetAddress.getLocalHost(), 244 ), outgoingPacketsToPeerQueue );
				requestsManager.insertRequest(requestFromUIControllerToFindResources);
			} 
			catch (UnknownHostException e2) 	{e2.printStackTrace();}
			
			*/
			
			
			try 							{ super.sendToPeer(buffer);} 
			catch (UnknownHostException e) 	{ e.printStackTrace();}
			
			
			
			
			
			
		}//end execute
		
	}//end class Get
	
	public static void main(String[] args) throws IOException
	{
	/*	try
		{
			PeerController peerController = new PeerController(new PortNumber(12345), new PortNumberForSending(54321), new PortNumberForReceiving(12346), 512);
			RequestsManager.newInstance();
			GossipPartners.newInstance();
			ResponsesFromFindRequests.newInstance();
			peerController.start();
		}
		catch(UnknownHostException uhe)	{uhe.printStackTrace();}
		catch(SocketException se)		{se.printStackTrace();}
		catch(IOException ioe)			{ioe.printStackTrace();}
			*/
		
		UIController uiController;
		
		uiController = new UIController(new PortNumberForReceiving(54321), new PortNumberForSending(12346), 512);
		GossipPartners.newInstance();
		uiController.start();
		
		//public UIController(PortNumberForReceiving incomingPortNumber, PortNumberForSending outgoingPortNumber, int packetSize) throws SocketException, UnknownHostException
		
		
		
	}//main
}//end UIController class
