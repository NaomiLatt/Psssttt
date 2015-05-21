import java.net.DatagramPacket;
import java.security.*;

/*
Lauren M DiGregorio
	  April 12, 2015
	  
	  For the final project
	  This class will create an ID object from the front of the Queue
	  
	  Class Variables
	  	id
	  		byte[] that holds the IDs
	  		
	  	idLengthInBytes
	  		int that holds the legnth of the ID
	  		
		idQueue
			LinkedListQueue that holds the random IDs available to be used
			
		maxQueueuLength
			int holds the max queue length, can't exceed MTU
			
		queueLength
			int holds teh queue length
			
		secureRandom
			SecureRandom is a random ID generated to conceal the idenity of the origin of the packet, can't be zero
			
		zeroID
			An ID of all zeros used when entering into the community
		
			
	  Constructors
	  	
	  	private ID()
	  		creates an ID object 
	
		public ID(byte[] byteArray)
	  		creates an ID object from a byteArray
	  	
	  	public ID(DatagramPacket packet, int startingByte)
			Creates datagramPacket using a packet and and starting byte number
			
		public ID(String hexString)
			Creates an ID object using a hexString as input
	
		Methods
			public static synchronized ID idFactory()
				See if ID is available in queue, if not make one
				
			public static synchronized ID createZeroID()
				Creates a zero ID based on ID size 
				
			public static synchronized void generateID()
  				Make one ID 
			
			public static synchronized int getIDLength()
				Return private variable zeroID.Length
			
			private static synchronized LinkedListQueue getQueue()
				Returns private variable idQueue
				
			public static synchronized int getMaxQueueLength()
				Returns private variable maxQueueLength
			
			public static synchronized int getQueueLength()
				Returns private variable queueLength
			
			public static synchronized ID getZeroID()
				Returns private variable zeroID
			
			public static synchronized void setIDLength(int lengthInBytes)
				sets private variable idLengthInBytes
			
			public static synchronized void setMaxQueueLength(int length)
				Sets private variable maxQueueLength
			
			public byte[] getBytes()
				returns a clone of byteArray
				
			public boolean equals(Object object)
				returns true if object's hash matches this object's hash
			
			public int hashcode()
				returns the hash of an object
			
			public string toString()
				returns string version of byteArray in hex

*/

/*
public class ID 
{
	private static byte[]	id;
	private static int 		idLengthInBytes;
	private static LinkedListQueue idQueue;
	private static int 		maxQueueLength;
	private static int 		queueLength;
	private static SecureRandom secureRandom;
	private ID		 		zeroID;
	
	private ID()
	{
	//	creates an ID object 
		//calls secure random and puts ID in queue
		byte[] checkingForZeros;
		boolean passed;
		int sum;
			
		this.secureRandom 		= new SecureRandom();
		this.idLengthInBytes 	= 16;
		this.id					= new byte[idLengthInBytes];
		this.idQueue			= new LinkedListQueue();
		this.secureRandom 		= new SecureRandom();
		
		//generates random ID and puts random ID into queue
		this.secureRandom.nextBytes(this.id);
		passed = false;
		
		while(passed == false)
		{
			sum = 0;
			for(int i = 0; i < 16; i++ )
			{
				sum = sum + (int)this.id[i];
			}//end for
			if(sum != 0)
			{
				passed = true;
			}//end if
			
		}//while
		
		this.idQueue.enQueue(this.id);
		
	}
	
	
	public ID(byte[] byteArray)
	{
		//creates an ID object from a byteArray
		if(byteArray == null)					{throw new IllegalArgumentException("ID constructor can't take null as an argument.");}
		if(byteArray.length != this.id.length)	{throw new IllegalArgumentException("ID Constructor, parameter byteArray is the incorrect legnth to be set as an id. Try again");}
		
		this.id = byteArray.clone();

	}
	public ID(DatagramPacket packet, int startingByte)
	{
		//packet.getArray() = byte[], figure out if you need the first id or second then plug into 
		//check is if the startingByte is either 0 or 15 else it is an error
		byte[] data;
		byte[] id;
		if(startingByte != 0 || startingByte != idLengthInBytes - 1 )	{throw new IllegalArgumentException("ID constructor, starting byte (the second parameter) needs to be equal to 0 or 15.  Try again."); }
		
		id = new byte[idLengthInBytes];
		data = packet.getData();
		for(int i = startingByte; i < idLengthInBytes; i++)
		{
			id[i] = data[i];
		}
		this.id = id;
	}
	public ID(String hexString)
	{
		//convert to byte array and then call ID(byte[] byteArray)
		byte [] data;
		data = hexString.getBytes();
		this.id = data.clone();
	}


	public static synchronized ID idFactory()
	{
	//See if ID is available in queue, if not make one, uses secure random to generate ID, cant't be zero
	//is queue have at least one id in it, if so gets somethingin queue, else calls private constructor and puts something in queue
		ID localId;
		
		if( idQueue.isEmpty() == false )
		{
			localId = (ID)idQueue.deQueue();
		}
		else
		{
			localId = new ID();
			localId.generateID();	
		}
		return localId;
	}
		
	public synchronized ID createZeroID()
	{
	//Creates a zero ID based on ID size 
		ID id;
		byte[] temp;
		
		temp = new byte[idLengthInBytes];
		for(int i=0; i<idLengthInBytes; i++ )
		{
			temp[i] = 0;
		}//end for
		
		id = new ID(temp);
		return id;
	}
		
	public static synchronized void generateID()
	{
	//Make one ID, cant be zero
		ID localId;
		localId = new ID();
		
	}
	
	public static synchronized int getIDLengthInBytes()
	{
		//Return private variable zeroID.Length
		return ID.idLengthInBytes;
	}
	
	private static synchronized LinkedListQueue getQueue()
	{
		//Returns private variable idQueue
		return idQueue;
	}
		
	public static synchronized int getMaxQueueLength()
	{
		//Returns private variable maxQueueLength
		return maxQueueLength;
	}
	
	public static synchronized int getQueueLength()
	{
		//Returns private variable queueLength
		return queueLength;
		
	}
	
	public synchronized ID getZeroID()
	{
		//Returns private variable zeroID
		return this.createZeroID();
	}
	
	public static synchronized void setIDLength(int lengthInBytes)
	{
		//sets private variable idLengthInBytes
		if(lengthInBytes < 0)		{ throw new IllegalArgumentException("Can't set ID to less than zero"); }
		
		idLengthInBytes = lengthInBytes;
		idQueue.removeAll();
	}
	
	public static synchronized void setMaxQueueLength(int length)
	{
		//Sets private variable maxQueueLength
	}
	
	public byte[] getBytes()
	{
		//returns a clone of byteArray
		return this.id.clone();
	}
		
	public boolean equals(Object object)
	{
		//returns true if object's hash matches this object's hash
		boolean result;
		if(this.hashCode() == object.toString().hashCode())	{ result = true;  }
		else												{ result = false; }
		return result;
	}
	
	public int hashcode()
	{
		//returns the hash of an object
		return this.toString().hashCode();
		
	}
	
	public String toString()
	{
		//returns string version of byteArray
		
		String result;
		result = "This ID object has id.deepToString [" + "] with a length of [" + idLengthInBytes +"] with the queue + [FIND A GOOD REFERENCE] with a maxQueueLength of ["+maxQueueLength+"] and a current length ["+queueLength+"] and the secureRandom object"+secureRandom.toString()+"] and a zeroID of ["+this.zeroID;
		return result;
	}
	
	
}//end class
*/



import java.net.DatagramPacket;
import java.security.SecureRandom;
import java.util.Arrays;

public class ID {
	private static int idLengthInBytes;
	private static LinkedListQueue idQueue;
	private static int maxQueueLength;
	private static int queueLength;
	private static SecureRandom secureRandom;
	private static ID zeroID;
	private byte[] id;
	
	
	static
	{
		idLengthInBytes = 16;
		idQueue 		= new LinkedListQueue();
		maxQueueLength 	= 300;
		queueLength		= 0;
		secureRandom	= new SecureRandom();
		zeroID 			= createZeroID();
	}
	
	private ID()
	{
		//Generate random ID
		this.id = new byte[getLengthInBytes()];
		
		secureRandom.nextBytes(this.id);
		
		//Check for small chance secureRandom returned all zeroes
		while(this.isZero());
		{
			secureRandom.nextBytes(this.id);
		}
	}
	
	public ID(byte[] byteArray)
	{
		if(byteArray == null){ throw new IllegalArgumentException("ID constructor: byte array parameter is null"); }
		
		this.id = byteArray.clone();
	}
	
	public ID(DatagramPacket packet, int startingByte)
	{
		//Copy ID from packet into id
		byte[] data;
		this.id = new byte[ID.getLengthInBytes()];
		
		data = packet.getData();

		//ARGS: src obj, srcPos, Object des, destPos, Length
		System.arraycopy(data, startingByte, this.id, 0, ID.getLengthInBytes());
	}
	
	private static ID createZeroID()
	{
		return new ID(new byte[getLengthInBytes()]);
	}
	
	public static synchronized void generateID()
	{
		getQueue().enQueue(new ID());
		queueLength++;
	}
	
	public static synchronized ID idFactory()
	{
		//Get an ID from the queue if one exists
		if(idQueue.peek() != null)
		{
			queueLength--;
			return (ID) getQueue().deQueue();
		}
		//Otherwise return new ID
		else
		{
			return new ID();
		}
	}
	
	public static int getLengthInBytes()
	{
		return idLengthInBytes;
	}
	
	private synchronized static LinkedListQueue getQueue()
	{
		return idQueue;
	}
	
	public static int getMaxQueueLength()
	{
		return maxQueueLength;
	}
	
	public static int getQueueLength()
	{
		return queueLength;
	}
	
	public static ID getZeroID()
	{
		return zeroID;
	}
	
	public static void setLengthInBytes(int lengthInBytes)
	{
		idLengthInBytes = lengthInBytes;
	}
	
	public static void setMaxQueueLength(int length)
	{
		maxQueueLength = length;
	}
	
	public byte[] getBytes()
	{
		return this.id;
	}
	
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(id);
		return result;
	}

	public boolean equals(Object obj) 
	{
		if (this == obj) 
		{
			return true;
		}
		if (obj == null) 
		{
			return false;
		}
		if (getClass() != obj.getClass()) 
		{
			return false;
		}
		ID other = (ID) obj;
		if (!Arrays.equals(this.id, other.id)) 
		{
			return false;
		}
		return true;
	}

	public boolean isZero()
	{
		return equals(getZeroID());
	}
	
	public String toString()
	{
		return this.id.toString();
	}
}






