import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
public class RequestsManager 
{
	private HashMap<ID,RequestFromUIController> requestsDirectory;
	private static RequestsManager instance = new RequestsManager();
	
	private RequestsManager()
	{
		requestsDirectory = new HashMap<ID,RequestFromUIController>();
	}

	public static RequestsManager getInstance()
	{
		return RequestsManager.instance;
	}//getInstance

	public static RequestsManager newInstance()
	{
		return RequestsManager.getInstance();
	}//newInstance

	public RequestFromUIController getRequest(ID id)
	{
		return requestsDirectory.get(id);
	}//getRequest

	public void insertRequest(RequestFromUIController request)
	{
		requestsDirectory.put(ID.idFactory(),request);
	}//insertRequest
	
}
