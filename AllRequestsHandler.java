import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

public class AllRequestsHandler 
{
	Vector<Request> allRequests;
	
	public AllRequestsHandler()
	{
		this.allRequests = new Vector<Request>();
	}
	
	public void addRequest(Request request)
	{
		this.allRequests.addElement(request);
	}
	
	public void printOptions(String requestOptions) throws UnsupportedEncodingException
	{
		for(int i = 0; i < allRequests.size(); i++)
		{
			if(allRequests.get(i).getRequestNumber().equals( requestOptions ) )
			{
				String message;
				
				message = new String(    allRequests.get(i).getUDPMessage().getMessage(), "UTF-8"     );
				System.out.println("[" + allRequests.get(i).getRequestNumber() + "."+ i + "] : " + message);
				
				allRequests.get(i).setIndexNumber(allRequests.get(i).getRequestNumber() + "." + i);
				
			}//end if
		}//end for
		
		
	}//end print method
	
	public Request getRequest(String requestOption) throws UnsupportedEncodingException
	{
		//need to parse requestOption
		Request request;
		request = null;
		
		for(int i = 0; i < allRequests.size(); i++)
		{
			if(allRequests.get(i).getRequestNumber().equals( requestOption ) )
			{
				String message;
				request = allRequests.get(i);
				message = new String(    allRequests.get(i).getUDPMessage().getMessage(), "UTF-8"     );
				System.out.println("[" + allRequests.get(i).getRequestNumber() + "."+ i + "] : " + message);
				
				allRequests.get(i).setIndexNumber(allRequests.get(i).getRequestNumber() + "." + i);
				break;
				
			}
		}//end for
		
		return request;
	
	}//end getRequest
	
	
	
	
	
	
	
}
