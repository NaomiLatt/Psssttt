

/*WHAT IS THE DIFFERENCE BETWEEN THIS TWO CLASSES
 * public static int getLengthInBytes() && public int get()
			
	Is this missing a byte array as an instance variable?
				
 */
/*
 * public class TimeToLive 
{
	
	Lauren M DiGregorio
	  April 12, 2015
	  
	  For the final project
	  Count the number of hops until a datgram message is terminated
	  
	  Class Variables
	  	timeToLive
	  		how many hops the packet can jump until the message is terminated		
			
	  Constructors
	  	
	  	TimeToLive(int timeToLive)
	  		set the hop count
	
		TimeToLive(byte[] byteArray)
	  		takes the length of byteArray to set the instance variable	  	
	  	
	
		Methods
			public static int getLengthInBytes()
				returns timeToLive
				
			public int get()
				returns timeToLive
				WHAT IS THE DIFFERENCE BETWEEN THIS TWO CLASSES
			
			public void set(int timeToLive)
				sets the timeToLive
			
			public byte[] getBytes()
				returns a byte array
			
			public String toString()
				returns byte array in hex form?
				
	
			*/	
	/*private int timeToLive;
	
	public TimeToLive(int timeTolive)
	{
		if( 0 < timeToLive ){ throw new IllegalArgumentException("TimeToLive must input something greater than zero"); }
		this.timeToLive = timeToLive;
	}
	
	public TimeToLive(byte[] byteArray)
	{
		//takes the length of byteArray to set the instance variable	
		//this.timeToLive = byteArray.length;
		if(byteArray == null) 									{ throw new IllegalArgumentException("TimeToLive, byteArray is equal to null. Try again."); 	}
		if(byteArray.length != TimeToLive.getLengthInBytes() ) 	{ throw new IllegalArgumentException("TimeToLive, byteArray is the wrong length. Try again."); 	}
		
	}
	
	public TimeToLive(byte[] byteArray,int timeToLive)
	{
		
	}
	public static int getLengthInBytes()
	{
		//returns timeToLive
		//Does this work? or do we need to set this 
		return 4;
	}
		
	public int get()
	{
		//returns timeToLive
		return this.timeToLive;
	}
		
	
	public void set(int timeToLive)
	{
		//sets the timeToLive
		if(timeToLive < 0 ) {throw new IllegalArgumentException("You cannot set TimeToLive variable to something less than zero in the TimeToLive class. Try again.");}
		
		this.timeToLive = timeToLive;
	}
	
	public byte[] getBytes()
	{
		//returns a byte array
		 byte[] result;
		 
		 result = new byte[4];

		  result[0] = (byte) (this.timeToLive >> 24);
		  result[1] = (byte) (this.timeToLive >> 16);
		  result[2] = (byte) (this.timeToLive >> 8);
		  result[3] = (byte) (this.timeToLive >> 0);

		  return result;
	}
	
	public String toString()
	{
		//returns byte array in hex form?
		String result;
		result = "The timeToLive is: " + this.timeToLive;
		return result;
	}
}
*/
	
	
	import java.math.BigInteger;

	public class TimeToLive
	{
		private int timeToLive;
		
		public TimeToLive(int timeToLive)
		{
			if(timeToLive < 1 ){throw new IllegalArgumentException("Time to live is less than one.  Input a number greater than zero");	}
			
			this.timeToLive = timeToLive;
		}
		
		public TimeToLive(byte[] byteArray)
		{
			if( byteArray == null ) 						{ throw new IllegalArgumentException("TimeToLive byte[] constructor: null parameter"); }
			if( byteArray.length != getLengthInBytes() ) 	{ throw new IllegalArgumentException("TimeToLive byte[] constructor: byte array is not proper length"); }
			
			this.timeToLive = 0;
			
			for(int i=0; i < getLengthInBytes(); i++)
			{
				this.timeToLive = (this.timeToLive << 8) | byteArray[i];
			}
		}
		
		public TimeToLive(byte[] byteArray, int startingByte)
		{
			if( byteArray == null ) 						{ throw new IllegalArgumentException("TimeToLive byte[] constructor: null parameter"); }
			//if( byteArray.length != TimeToLive.getLengthInBytes() ) 	{ throw new IllegalArgumentException("TimeToLive byte[] constructor: byte array is not proper length"); }
			
			this.timeToLive = 0;
			
			for(int i=startingByte; i < (startingByte + TimeToLive.getLengthInBytes()); i++)
			{
				this.timeToLive = (this.timeToLive << 8) | byteArray[i];
			}
			
		
			System.out.println(this.timeToLive);
		}
		
		public static int getLengthInBytes()
		{
			return 4;
		}
		
		public int get()
		{
			//check
			return this.timeToLive;
		}
		
		public void set(int timeToLive)
		{
			this.timeToLive = timeToLive;
		}
		
		public byte[] getBytes()
		{
			//return BigInteger.valueOf(this.timeToLive).toByteArray();
			
			byte[] array;
			array = new byte[TimeToLive.getLengthInBytes()];
			
			array[0] = (byte)(this.timeToLive >>> 24);
			array[1] = (byte)(this.timeToLive >>> 16);
			array[2] = (byte)(this.timeToLive >>> 8);
			array[3] = (byte)(this.timeToLive);
			
			return array;
		}
		
		public String toString()
		{
			return "Time to live is: " + this.get();
		}
	}