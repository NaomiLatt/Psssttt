
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class TestRequestFromPeer 
{
	public static void main(String[] args)
	{
		ByteBuffer				byteBuffer;
		UDPMessage 				findRequest;
		FindRequestFromPeer		findRequestFromPeer;
		String					findMessage;
		UDPMessage 				getRequest;
		GetRequestFromPeer		getRequestFromPeer;
		byte[]					getMessage;
		ByteArrayOutputStream 	outputStream;
		int						partNumber;
		Resource[] 				dirtyResources;
		ResourceManager			rman;
		TimeToLive 				timeToLive;
		
		//Setup
		rman = ResourceManager.newInstance();
		rman.loadResourcesFrom(new File("/Users/Sam/Desktop/Resources.txt"),'#');

		/***TEST FIND REQUEST CODE***/
		System.out.println("TESTING FIND REQUEST\n");
		
		//Set up UDPMessage
		findMessage = "test";
		
		timeToLive = new TimeToLive(10);
		
		findRequest = new UDPMessage(ID.idFactory(), ID.idFactory(), timeToLive , findMessage);
		
		findRequestFromPeer = new FindRequestFromPeer(findRequest);
		
		findRequestFromPeer.run();
		
		/***TEST GET REQUEST CODE***/
		System.out.println("TESTING GET REQUEST\n");
		
		//Get part number into byte array
		partNumber = 1;
		
		byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.putInt(partNumber);
		
		//Set up UDPMessage
		outputStream = new ByteArrayOutputStream();
		
		try 
		{
			outputStream.write(ID.idFactory().getBytes());
			outputStream.write(byteBuffer.array());
		} 
		catch (IOException ioe){ ioe.printStackTrace(); }
		
		getMessage = outputStream.toByteArray();
		
		System.out.println("Length of message: " + getMessage.length);
		
		try{ outputStream.close(); } 
		catch (IOException ioe){ ioe.printStackTrace(); }
		
		//Dirty code to get ID for request
		dirtyResources = rman.getResourcesThatMatch("test");
		
		getRequest = new UDPMessage(ID.idFactory(), dirtyResources[0].getID(), timeToLive, getMessage);
		
		System.out.println("Length of UDPMessage: " + getRequest.getMessage().length);
		
		getRequestFromPeer = new GetRequestFromPeer(getRequest);
		
		getRequestFromPeer.run();
	}
}
