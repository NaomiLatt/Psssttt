import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class GetRequestFromPeer extends RequestFromPeer 
{
	public GetRequestFromPeer(UDPMessage message)
	{
		super(message);
	}
	
	public void run()
	{
		Resource 				foundResource;
		byte[]					message;
		ByteArrayOutputStream 	outputStream;
		byte[]					partNumberToGet;
		int						partNumber;
		ID 						resourceID;
		UDPMessage				responseMessage;

		//Format part number
		partNumberToGet = new byte[PartNumbers.getLengthInBytes()];
		
		//Extract the bytes of the part number from the message
		System.arraycopy(this.getUDPMessage().getMessage(), ID.getLengthInBytes(),
				partNumberToGet, 0, PartNumbers.getLengthInBytes());
		
		//Get the integer from bytes of part number
		partNumber		= ByteBuffer.wrap(partNumberToGet).getInt();
		
		System.out.println("partNumber: " + partNumber);
		
		//Find resource using ID from Get request
		resourceID 		= this.getUDPMessage().getID2(); 
		foundResource 	= ResourceManager.getInstance().getResourceThatMatches(resourceID);
		
		//If a resource was found through the specific ID
		if(foundResource != null)
		{	
			//Begin formatting response message
			
			outputStream = new ByteArrayOutputStream();
			
			//Write the response message to a byte array
			try 
			{
				//Random ID (16 bytes)
				outputStream.write(ID.idFactory().getBytes());
				
				//Part number (4 bytes)
				outputStream.write(partNumberToGet);
				
				//Write bytes from file corresponding to part number
				outputStream.write(foundResource.getBytes(partNumber));
			} 
			catch (IOException ioe){ ioe.printStackTrace(); }
			
			//Put byte array into message
			message = outputStream.toByteArray();
			
			//Create response message
			//Response message format: Resource ID, Original Request ID, TimeToLive, message(Random ID, Part number, bytes)
			//TODO subtract TTL
			responseMessage = new UDPMessage( foundResource.getID(), this.getUDPMessage().getID1(),
					this.getUDPMessage().getTimeToLive(), message );
			
			//Send out response to all Gossip Partners
			GossipPartners.getInstance().send(responseMessage);
			
			System.out.println(responseMessage.toString());
			
			/*** TEST CODE FOR SMALL FILE ***/
			/*** TEST CODE FOR SMALL FILE ***/
			/*** TEST CODE FOR SMALL FILE ***/
			if(message.length < 456)
			{
				System.out.println("Message length: " + message.length);
				
				RandomAccessFile gotFile;
				
				try
				{
					gotFile = new RandomAccessFile(new File("/Users/Sam/Desktop/newfile.txt"), "rw");
					
					gotFile.write(message, 20, message.length-20);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Close
			try{ outputStream.close(); }
			catch(IOException ioe){ ioe.printStackTrace(); }
		}
	}
}
