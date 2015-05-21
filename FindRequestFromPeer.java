import java.io.UnsupportedEncodingException;

public class FindRequestFromPeer extends RequestFromPeer
{
	public FindRequestFromPeer(UDPMessage message)
	{
		super(message);
	}

	public void run() 
	{
		Resource[] 	foundResources;
		String		message;
		UDPMessage  responseMessage;
		String 		searchString;
		
		searchString = new String();
		
		try { searchString = new String(this.getUDPMessage().getMessage(), "UTF-8"); } 
		catch (UnsupportedEncodingException uee){ uee.printStackTrace(); }
		
		foundResources = ResourceManager.getInstance().getResourcesThatMatch(searchString);
		
		//If any resources were found from the search string
		if(foundResources.length > 0)
		{
			//Create a response message for each found resource
			for(Resource r : foundResources)
			{
				//Delimited message
				message = ID.idFactory().toString() + "#" + r.getMimeType() + "#" + r.getSizeInBytes() + "#" + r.getDescription(); 
				
				//Create response message
				responseMessage = new UDPMessage( r.getID(), this.getUDPMessage().getID1(),
						this.getUDPMessage().getTimeToLive(), message );
				
				System.out.println(responseMessage.toString());
				
				GossipPartners.getInstance().send(responseMessage);
			}
		}//if
	}//run
}//class
