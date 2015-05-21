import java.nio.*;

/*public class PartNumbers 
{
	private int numberOfParts;
	private int numberOfMissingParts;
	
	
	public PartNumbers(int numberOfParts)
	{
		if(numberOfParts<0){throw new IllegalArgumentException("numberOfParts less than zero");}
		this.numberOfParts=numberOfParts;
	}
	
	public static int getLengthInBytes(){return 4;}
	
	
	
	
	
	
	
	public static byte[] getBytes(long data)
	{
		byte[] byteArray;

		byteArray = new byte[ getLengthInBytes() ];
		
		for ( int i=byteArray.length-1; i>=0; i--) 
		{ 		
			byteArray[i] = (byte)
			(data & 0xFF);  
			data = data >> 8;
			
		}//end for

		return byteArray;
	}//getBytes
	
	

	
	
	
	
	public static int getFromByteArray(byte[] byteArray)
	{
		if(byteArray==null){throw new IllegalArgumentException("byteArray is null");}
		if(byteArray.length != getLengthInBytes()){throw new IllegalArgumentException("byteArray length is not 4");}
		
		int partNumber;
		partNumber = ByteBuffer.wrap(byteArray).getInt();
		return partNumber;
	}//getFromByteArray

	
	
	
	public int numberOfMissingParts
}
*/

public class PartNumbers 
{
	private int numberOfParts;
	private int numberOfMissingParts;
	
	public PartNumbers(int numberOfParts)
	{
		if(numberOfParts<0){throw new IllegalArgumentException("numberOfParts less than zero");}
		this.numberOfParts=numberOfParts;
		this.numberOfMissingParts=numberOfParts;
	}
	
	public int get()
	{
		return this.numberOfParts;
	}
	
	public byte[] getBytes()
	{
		byte[] bytes = new byte[4];
		for(int i = 0; i < 4; i++)
		{
			bytes[i] = (byte)(this.numberOfParts >>> (i * 8));
		}
		return bytes;
	}
	
	public int getLengthInBytes()
	{
		return 4;
	}
	
	public int numberOfMissingParts()
	{
		return this.numberOfMissingParts;
	}
}

