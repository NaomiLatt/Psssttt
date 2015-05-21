import java.io.*;

public class Resource
{
	
	private String description;
	private File location;
	private String mimeType;
	private ID resourceID;
	private RandomAccessFile raFile;

	public Resource(ID id, String data) throws FileNotFoundException, IOException
	{
		if(ID.getLengthInBytes() != 16){throw new IllegalArgumentException("id length is not 16 bytes");}
		if(data == null){throw new IllegalArgumentException("data is null");}
		if(this.raFile.length()>456){throw new IllegalArgumentException("RandomAccessFile raFile > 456");}

		String[] fields;
		fields = data.split(""+'\t');
		
		if(fields.length != 3){throw new IllegalArgumentException("record does not have three fields");}

		this.mimeType = fields[0].trim();
		this.location = new File(fields[1].trim());
		this.description = fields[2].trim();
		this.resourceID = id;
		this.raFile = new RandomAccessFile(this.location, "rw");	
	}

	public Resource(ID id, String data, char delimiter) throws FileNotFoundException, IOException
	{
		if(ID.getLengthInBytes() != 16){throw new IllegalArgumentException("id length is not 16 bytes");}
		if(data == null){throw new IllegalArgumentException("data is null");}

		String[] fields;
		fields = data.split(""+delimiter);

		if(fields.length != 3){throw new IllegalArgumentException("record does not have three fields");}

		this.mimeType = fields[0].trim();
		this.location = new File(fields[1].trim());
		this.description = fields[2].trim();
		this.resourceID = id;
		this.raFile = new RandomAccessFile(this.location, "rw");
	}

	public String getDescription()
	{
		return this.description;
	}//getDescription

	public File getLocation()
	{
		return this.location;
	}//getLocation

	public String getMimeType()
	{
		return mimeType;
	}//getMimeType

	public ID getID()
	{
		return this.resourceID;
	}//getID

	public long getSizeInBytes()
	{
		return location.length();
	}//getSizeInBytes

	public RandomAccessFile getRandomAccessFile()
	{
		return this.raFile;
	}//getRandomAccessFile

	public synchronized byte[] getBytes(long start, long end) throws IOException
	{
		if(start < 0){throw new IllegalArgumentException("getBytes method: Starting byte must be at least zero");}
		if(start > end){throw new IllegalArgumentException("getBytes method: Starting byte must be less than ending byte");}
		if(start >= this.getRandomAccessFile().length()){ throw new IllegalArgumentException("getBytes method: start byte greater than file length"); }
		
		if(end >= this.getRandomAccessFile().length()){ end = this.getRandomAccessFile().length(); }
		//if(end >= this.getRandomAccessFile().length()){ throw new IllegalArgumentException("getBytes method: end greater than length"); }
		
		byte[] bytes;
		bytes = new byte[(int)(end-start)];
		this.getRandomAccessFile().read(bytes, (int)start, (int)(end-start));
		
		return bytes;
	}//getBytes

	public byte[] getBytes(int partNumber) throws IOException
	{
		int start;
		int end;
		start = (partNumber-1) * 456;
		end = partNumber * 456;
		return this.getBytes(start, end);
	}
	
	public boolean matches(String searchString)
	{
		return this.getDescription().contains(searchString);
	}//searchString
	
	public int numberOfParts()
	{
		return (int)Math.ceil(this.getSizeInBytes()/(double)ResourceManager.numberOfBytesPerPacket());
	}
	
}//class