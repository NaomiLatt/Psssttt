
import java.io.*;
import java.net.*;
import java.util.*;

public class UDPMessage
{

	private ID 			id1;
	private ID 			id2;
	private TimeToLive 	timeToLive;
	private byte[] 		message;

	public UDPMessage(ID id1, ID id2, TimeToLive timeToLive, String message)
	{
		this(id1, id2, timeToLive, message.getBytes());
	}

	public UDPMessage(ID id1, ID id2, TimeToLive timeToLive, byte[] message)
	{
		if(id1.getBytes().length != ID.getLengthInBytes())					{throw new IllegalArgumentException("Invalid ID length for id1.");}
		if(id2.getBytes().length != ID.getLengthInBytes())					{throw new IllegalArgumentException("Invalid ID length for id2.");}
		if(timeToLive.getBytes().length != TimeToLive.getLengthInBytes())	{throw new IllegalArgumentException("Invalid TimeToLive length.");}
		
		//not sure if this is an error, we made need a message that equals null
		if(message == null)													{throw new IllegalArgumentException("message is null");}

		this.id1		= id1;
		this.id2 		= id2;
		this.timeToLive = timeToLive;
		this.message 	= message;
	}

	public UDPMessage(DatagramPacket datagramPacket)
	{
		byte[] 	srcArray;
		int		destPos;
		
		if(datagramPacket == null)					{throw new IllegalArgumentException("UDPMessage(datagramPacket) constructor, datagramPacket is null");}
		if(datagramPacket.getData().length > 512)	{throw new IllegalArgumentException("UDPMessage(datagramPacket) constructor, incoming message is too big. Send smaller packet") ;}

		this.id1 		= new ID(datagramPacket, 0);
		this.id2 		= new ID(datagramPacket, 16);
		this.timeToLive = new TimeToLive(datagramPacket.getData(), 32);
		
		
		srcArray 	= new byte[476];
		destPos		= (ID.getLengthInBytes()+ID.getLengthInBytes()+TimeToLive.getLengthInBytes());
		
		//datagramPacket.getData().length - destPos  --> this is the length of the payload, full length of the datagramPacket - headers
		System.arraycopy(datagramPacket.getData(), destPos, srcArray, 0, datagramPacket.getData().length - destPos);
		
		this.message = srcArray;
	
	}

	public DatagramPacket getDatagramPacket()
	{
		return this.getDatagramPacket(this.message);
	}//getDatagramPacket

	public DatagramPacket getDatagramPacket(String payload)
	{
		if(payload == null){payload = "";}
		return this.getDatagramPacket(payload.getBytes());
	}//getDatagramPacket

	public DatagramPacket getDatagramPacket(byte[] payload)
	{
		byte[] packetData;
		packetData = Utilities.concatenate(this.getID1().getBytes(), this.getID2().getBytes(), this.getTimeToLive().getBytes(), payload);
		return new DatagramPacket(packetData, packetData.length);
	}//getDatagramPacket

	public ID getID1()
	{
		return this.id1;
	}//getID1

	public ID getID2()
	{
		return this.id2;
	}//getID2

	public TimeToLive getTimeToLive()
	{
		return this.timeToLive;
	}//getTimeToLive

	public byte[] getMessage()
	{
		return this.message;
	}//getMessage
	
	public static int getMaximumPacketSizeInBytes()
	{
		return 512;
	}
	
	public static int getMinimumPacketSizeInBytes()
	{
		//TODO change to compute this instead of just returning it
		return 456;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("ID1: " + this.id1 + "\n");
		sb.append("ID2: " + this.id2 + "\n");
		sb.append("TimeToLive: " + this.timeToLive.get() + "\n");
		try{ sb.append("Message: " + new String(this.message, "UTF-8") + "\n"); } 
		catch(UnsupportedEncodingException e){ e.printStackTrace(); }
		
		return new String(sb);
	}

}//class




















/*
import java.net.*;


Lauren M DiGregorio
	  April 12, 2015
	  
	  For the final project
	  This is the message that will be sent back and forth by the UI controller and the a
	  
	  Class Variables
	  	id1
	  		id1 holds the first id
	  	id2
	  		id2 holds the second id object
	  	TimeToLive
	  		Holds the hop count, the length of hops until the message will no longer be passed on
		byte[] message
			holds the payload
		
	  Constructors
	  	
	  	UDPMessage(ID id1, ID id2, TimeToLive timeToLive, String message)
	  		creates a UDPMessage object  with the payload as a string
	  	
	  	UDPMessage(ID id1, ID id2, TimeToLive timeToLive, byte[] message)
			creates a UDPMessage object with the payload as a byte array
	
		Methods
			public DatagramPacket getDatagramPacket()
				returns the Datagram Packet using the instance variables, but calls getDatagramPacket 
				that has a payload with the Byte[]
				
			public DatagramPacket getDatagramPacket(String payload)
				returns the datagramPacket using the a string as the payload but converts the string
				as a byte[] message 
				
			public DatagramPacket getDatagramPacket(byte[] payload)
  				This is the method called by the other two methods, returns the DatagramPacket with 
  				the payload as byte[]
			
			public ID getID1()
				returns ID1
			
			public ID getID2()
				returns ID2
				
			public TimeToLive getTimeToLive()
				returns timeToLive
			
			public byte[] getMessage()
				returns message

*/

/*public class UDPMessage 
{
	private ID id1;
	private ID id2;
	private TimeToLive timeToLive;
	private byte[] message;

	UDPMessage( ID id1, ID id2, TimeToLive timeToLive, String message )
	{
		if(id1 == null ) 		{ throw new IllegalArgumentException("UDPMessage, id1 cannot be equal to null"); 		}
		if(id2 == null ) 		{ throw new IllegalArgumentException("UDPMessage, id2 cannot be equal to null"); 		}
		if(timeToLive == null ) { throw new IllegalArgumentException("UDPMessage, timeToLive cannot be equal to null"); }
		
		this.id1 		= id1;
		this.id2 		= id2;
		this.timeToLive = timeToLive;
		this.message 	= message.getBytes();
		
	}
	
	UDPMessage(ID id1, ID id2, TimeToLive timeToLive, byte[] message)
	{
		//creates a UDPMessage object with the payload as a byte array
		if(id1 == null ) 		{ throw new IllegalArgumentException("UDPMessage, id1 cannot be equal to null"); 		}
		if(id2 == null ) 		{ throw new IllegalArgumentException("UDPMessage, id2 cannot be equal to null"); 		}
		if(timeToLive == null)  { throw new IllegalArgumentException("UDPMessage, timeToLive cannot be equal to null"); }
		if(message == null ) 	{ throw new IllegalArgumentException("UDPMessage, message cannot be equal to null"); 	}
		
		this.id1 		= id1;
		this.id2 		= id2;
		this.timeToLive = timeToLive;
		this.message 	= message;
	}


	public DatagramPacket getDatagramPacket()
	{
		//returns the Datagram Packet using the instance variables, but calls getDatagramPacket 
		//that has a payload with the Byte[]
		DatagramPacket datagramPacket;
		datagramPacket = this.getDatagramPacket(this.message);
		return datagramPacket;
		
	}
		
		
	public DatagramPacket getDatagramPacket(String payload)
	{	//returns the datagramPacket using the a string as the payload but converts the string
		//as a byte[] message 
		DatagramPacket datagramPacket;
		byte[] myData;
		
		myData = payload.getBytes();
		
		datagramPacket = this.getDatagramPacket(myData);
		
		return datagramPacket;
	}
	

	
	public DatagramPacket getDatagramPacket(byte[] payload )
	{
		//This is the method called by the other two methods, returns the DatagramPacket with
				//the payload as byte[]
		//check to make sure my.length works or if you need to use size
		//make sure buffer is long enough for all information when creating the datagramPacket
		
		byte[] buffer;
		DatagramPacket datagramPacket;
		ID id1;
		ID id2;
		byte[] myData;
		int size;
		TimeToLive ttl;
		
		myData = payload;
		//should be coming from parameter
		
		id1 = ID.idFactory();
		id2 = ID.idFactory();
		ttl = new TimeToLive(75);
		size = (ID.getIDLengthInBytes() * 2 ) + TimeToLive.getLengthInBytes() +myData.length;
		buffer = new byte[size];
		
		System.arraycopy( id1.getBytes(), 	0, buffer, 0, 							ID.getIDLengthInBytes() );
		System.arraycopy( id2.getBytes(), 	0, buffer, ID.getIDLengthInBytes(), 	ID.getIDLengthInBytes() );
		System.arraycopy( ttl.getBytes(), 	0, buffer, ID.getIDLengthInBytes()*2, 	ttl.getLengthInBytes()  );  
		System.arraycopy(myData, 0, buffer, (ID.getIDLengthInBytes()*2)+ ttl.getLengthInBytes(), myData.length);
		
		datagramPacket = new DatagramPacket(buffer, size);
		
		return datagramPacket;
	
	}
			
	
	public ID getID1()
	{
		//returns ID1
		return this.id1;
	}
	
	public ID getID2()
	{
		//returns ID2
		return this.id2;
	}
		
	public TimeToLive getTimeToLive()
	{
		//returns timeToLive
		return this.timeToLive;
	}
	
	public byte[] getMessage()
	{
		//returns message
		return this.message;
	}
	
	
}//end class


import java.io.*;
import java.net.*;
import java.util.*;

public class UDPMessage
{

	private ID 			id1;
	private ID 			id2;
	private TimeToLive 	timeToLive;
	private byte[] 		message;

	public UDPMessage(ID id1, ID id2, TimeToLive timeToLive, String message)
	{
		this(id1,id2,timeToLive,message.getBytes());
	}

	public UDPMessage(ID id1, ID id2, TimeToLive timeToLive, byte[] message)
	{
		if(id1.getBytes().length != ID.getLengthInBytes())					{throw new IllegalArgumentException("Invalid ID length for id1.");}
		if(id2.getBytes().length != ID.getLengthInBytes())					{throw new IllegalArgumentException("Invalid ID length for id2.");}
		if(timeToLive.getBytes().length != TimeToLive.getLengthInBytes())	{throw new IllegalArgumentException("Invalid TimeToLive length.");}
		
		//not sure if this is an error, we made need a message that equals null
		if(message == null)													{throw new IllegalArgumentException("message is null");}

		this.id1		= id1;
		this.id2 		= id2;
		this.timeToLive = timeToLive;
		this.message 	= message;
	}

	public UDPMessage(DatagramPacket datagramPacket)
	{
		byte[] 	srcArray;
		int		destPos;
		
		if(datagramPacket == null)					{throw new IllegalArgumentException("UDPMessage(datagramPacket) constructor, datagramPacket is null");}
		if(datagramPacket.getData().length > 512)	{throw new IllegalArgumentException("UDPMessage(datagramPacket) constructor, incoming message is too big. Send smaller packet") ;}

		this.id1 		= new ID(datagramPacket,0);
		this.id2 		= new ID(datagramPacket,16);
		this.timeToLive = new TimeToLive(datagramPacket.getData(),32);
		
		
		srcArray 	= new byte[52];
		destPos		= (ID.getLengthInBytes()+ID.getLengthInBytes()+TimeToLive.getLengthInBytes());
		
		//datagramPacket.getData().length - destPos  --> this is the length of the payload, full length of the datagramPacket - headers
		System.arraycopy(datagramPacket.getData(), destPos, srcArray, 0, datagramPacket.getData().length - destPos);
		
		this.message = srcArray;
	
	}

	public DatagramPacket getDatagramPacket()
	{
		byte[] 	packetData;
		int		packetSize;
		packetData = new byte[34 + this.message.length];
		
		//ARGS: 		(1)  src obj, 	(2)srcPos,     (3) Object des, (4)destPos, (5)Length
		
		System.arraycopy(this.id1.getBytes(), 		0,	packetData, 0, 															ID.getLengthInBytes());
		System.arraycopy(this.id2.getBytes(), 		0,	packetData, ID.getLengthInBytes(), 										ID.getLengthInBytes());
		System.arraycopy(this.timeToLive.getBytes(),0,	packetData,	(ID.getLengthInBytes()*2),									TimeToLive.getLengthInBytes());
		System.arraycopy(this.message,				0,	packetData,	(ID.getLengthInBytes()*2) + TimeToLive.getLengthInBytes() ,	this.message.length);
		
		packetSize	= ID.getLengthInBytes() + ID.getLengthInBytes() + TimeToLive.getLengthInBytes() + this.message.length;
		
		return new DatagramPacket(packetData, packetSize);
	}//getDatagramPacket

	
	
	
	
	public DatagramPacket getDatagramPacket(String payload)
	{
		return new DatagramPacket(payload.getBytes(),34+this.message.length);
	}//getDatagramPacket

	public DatagramPacket getDatagramPacket(byte[] payload)
	{
		return new DatagramPacket(payload,34+this.message.length);
	}//getDatagramPacket

	public ID getID1()
	{
		return this.id1;
	}//getID1

	public ID getID2()
	{
		return this.id2;
	}//getID2

	public TimeToLive getTimeToLive()
	{
		return this.timeToLive;
	}//getTimeToLive

	public byte[] getMessage()
	{
		return this.message;
	}//getMessage
	public static int getMaximumPacketSizeInBytes()
	{
		return 512;
	}
	
	public static int getMinimumPacketSizeInBytes()
	{
		//TODO change to compute this instead of just returning it
		return 456;
	}

}//class


import java.io.*;
import java.net.*;
import java.util.*;

public class UDPMessage
{

	private ID 			id1;
	private ID 			id2;
	private TimeToLive 	timeToLive;
	private byte[] 		message;

	public UDPMessage(ID id1, ID id2, TimeToLive timeToLive, String message)
	{
		this(id1, id2, timeToLive, message.getBytes());
	}

	public UDPMessage(ID id1, ID id2, TimeToLive timeToLive, byte[] message)
	{
		if(id1.getBytes().length != ID.getLengthInBytes())					{throw new IllegalArgumentException("Invalid ID length for id1.");}
		if(id2.getBytes().length != ID.getLengthInBytes())					{throw new IllegalArgumentException("Invalid ID length for id2.");}
		if(timeToLive.getBytes().length != TimeToLive.getLengthInBytes())	{throw new IllegalArgumentException("Invalid TimeToLive length.");}
		
		//not sure if this is an error, we made need a message that equals null
		if(message == null)													{throw new IllegalArgumentException("message is null");}

		this.id1		= id1;
		this.id2 		= id2;
		this.timeToLive = timeToLive;
		this.message 	= message;
	}

	public UDPMessage(DatagramPacket datagramPacket)
	{
		byte[] 	srcArray;
		int		destPos;
		
		if(datagramPacket == null)					{throw new IllegalArgumentException("UDPMessage(datagramPacket) constructor, datagramPacket is null");}
		if(datagramPacket.getData().length > 512)	{throw new IllegalArgumentException("UDPMessage(datagramPacket) constructor, incoming message is too big. Send smaller packet") ;}

		this.id1 		= new ID(datagramPacket,0);
		this.id2 		= new ID(datagramPacket,16);
		this.timeToLive = new TimeToLive(datagramPacket.getData(),32);
		
		
		srcArray 	= new byte[512];
		destPos		= (ID.getLengthInBytes()+ID.getLengthInBytes()+TimeToLive.getLengthInBytes());
		
		//datagramPacket.getData().length - destPos  --> this is the length of the payload, full length of the datagramPacket - headers
		System.arraycopy(datagramPacket.getData(), destPos, srcArray, 0, datagramPacket.getData().length - destPos);
		
		this.message = srcArray;
	
	}

	public DatagramPacket getDatagramPacket()
	{
		byte[] 	packetData;
		int		packetSize;
		packetData = new byte[34 + this.message.length];
		
		//ARGS: 		(1)  src obj, 	(2)srcPos,     (3) Object des, (4)destPos, (5)Length
		
		System.arraycopy(this.id1.getBytes(), 		0,	packetData, 0, 															ID.getLengthInBytes());
		System.arraycopy(this.id2.getBytes(), 		0,	packetData, ID.getLengthInBytes(), 										ID.getLengthInBytes());
		System.arraycopy(this.timeToLive.getBytes(),0,	packetData,	(ID.getLengthInBytes()*2),									TimeToLive.getLengthInBytes());
		System.arraycopy(this.message,				0,	packetData,	(ID.getLengthInBytes()*2) + TimeToLive.getLengthInBytes() ,	this.message.length);
		
		packetSize	= ID.getLengthInBytes() + ID.getLengthInBytes() + TimeToLive.getLengthInBytes() + this.message.length;
		
		return new DatagramPacket(packetData, packetSize);
	}//getDatagramPacket

	public DatagramPacket getDatagramPacket(String payload)
	{
		return new DatagramPacket(payload.getBytes(), 34+this.message.length);
	}//getDatagramPacket

	public DatagramPacket getDatagramPacket(byte[] payload)
	{
		return new DatagramPacket(payload, 34+this.message.length);
	}//getDatagramPacket

	public ID getID1()
	{
		return this.id1;
	}//getID1

	public ID getID2()
	{
		return this.id2;
	}//getID2

	public TimeToLive getTimeToLive()
	{
		return this.timeToLive;
	}//getTimeToLive

	public byte[] getMessage()
	{
		return this.message;
	}//getMessage
	
	public static int getMaximumPacketSizeInBytes()
	{
		return 512;
	}
	
	public static int getMinimumPacketSizeInBytes()
	{
		//TODO change to compute this instead of just returning it
		return 456;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("ID1: " + this.id1 + "\n");
		sb.append("ID2: " + this.id2 + "\n");
		sb.append("TimeToLive: " + this.timeToLive.get() + "\n");
		try{ sb.append("Message: " + new String(this.message, "UTF-8") + "\n"); } 
		catch(UnsupportedEncodingException e){ e.printStackTrace(); }
		
		return new String(sb);
	}

}//class
*/