import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.*;
/*
Lauren M DiGregorio
	  April 12, 2015
	  
	  For the final project
	  The resource manager holds all the resources that the peer owns and the associated ID
	  
	  Class Variables
		  resourceDirectory 
		  	HashMap that holds all resources the peer owns
			
		  static ResourceManager
		  	holds the actual file on the computer
		  	
		  
	  Constructors
	  	
	  	ResourceManger()
	  		creates a resource manager object, this is a private method
	
		Methods
			public static ResourceManager getInstance()
				Singleton class where there is only one instance
			
			public static ResourceManager newInstance()
				Resets the resourceManager file index
				
			public static void loadResourceFrom(File file)
				adds file to ResourceManager
			
			public Resource[] geResourcesThatMatch(String searchString)
				retrieve all resources 
				
			


public class ResourceManager 
{
	private HashMap<ID,Resource> resourceDirectory;
	private static ResourceManager resourcesManager = new ResourceManager();

	private ResourceManager()
	{
		resourceDirectory = new HashMap<ID,Resource>();
	}

	public static ResourceManager getInstance()
	{
		return ResourceManager.resourcesManager;
	}//getInstance

	public static ResourceManager newInstance()
	{
		return ResourceManager.getInstance();
	}//newInstance

	public synchronized void loadResourcesFrom(File file,char delimiter)
	{
		if(delimiter <= 0){throw new IllegalArgumentException("delimiter is null");}
		BufferedReader dataFile;
		String dataRecord;
		Resource resource;

		if(file==null){throw new IllegalArgumentException("File is null");}

		try
		{
			dataFile = new BufferedReader(new FileReader(file));

			dataRecord = dataFile.readLine();
			while(dataRecord != null)
			{
				resource = new Resource(ID.idFactory(),dataRecord,delimiter);
				resourceDirectory.put(resource.getID(),resource);
				dataRecord = dataFile.readLine();
			}
			dataFile.close();
		}
		catch(IOException ioe){System.out.println(ioe.getMessage());}
	}//loadResourcesFrom

	public Resource[] getResourcesThatMatch(String searchString)
	{
		int count;
		Resource[] matches;
		Resource[] resourceArray;
		Object[] objects;
		
		resourceArray = new Resource[resourceDirectory.size()];
		objects = new Object[resourceDirectory.size()];

		objects = resourceDirectory.values().toArray();

		for(int i=0;i<resourceArray.length;i++)
		{
			resourceArray[i] = (Resource) objects[i];
		}
		count = 0;
		for(int i=0;i<resourceArray.length;i++)
		{
			if(resourceArray[i].matches(searchString))
			{
				count = count + 1;
			}
		}
		matches = new Resource[count];
		count = 0;
		for(int i=0;i<resourceArray.length;i++)
		{
			if(resourceArray[i].matches(searchString))
			{
				matches[count] = resourceArray[i];
				count = count+1;
			}
		}

		return matches;
	}//getResourcesThatMatch

	public static int numberOfBytesPerPacket(){ return 456; }
}
*/




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ResourceManager
{
	private HashMap<ID,Resource> 	resourceDirectory;
	private static ResourceManager 	resourcesManager;
	
	static
	{
		resourcesManager = new ResourceManager();
	}

	private ResourceManager()
	{
		resourceDirectory = new HashMap<ID,Resource>();
	}

	public static ResourceManager getInstance()
	{
		return ResourceManager.resourcesManager;
	}

	public static ResourceManager newInstance()
	{
		return ResourceManager.getInstance();
	}

	public synchronized void loadResourcesFrom(File file, char delimiter)
	{
		if(delimiter <= 0){throw new IllegalArgumentException("delimiter is null");}
		if(file == null){throw new IllegalArgumentException("File is null");}
		
		BufferedReader 	dataFile;
		String 			dataRecord;
		Resource 		resource;

		try
		{
			dataFile = new BufferedReader(new FileReader(file));

			dataRecord = dataFile.readLine();
			
			while(dataRecord != null)
			{
				resource = new Resource(ID.idFactory(), dataRecord, delimiter);
				resourceDirectory.put(resource.getID(), resource);
				dataRecord = dataFile.readLine();
			}
			
			dataFile.close();
		}
		catch(IOException ioe){System.out.println(ioe.getMessage());}
	}

	public Resource[] getResourcesThatMatch(String searchString)
	{
		int count;
		Resource[] matches;
		Resource[] resourceArray;
		Object[] objects;
		
		resourceArray = new Resource[resourceDirectory.size()];
		objects = new Object[resourceDirectory.size()];

		objects = resourceDirectory.values().toArray();

		//Get resources from directory
		for(int i=0; i<resourceArray.length; i++)
		{
			resourceArray[i] = (Resource) objects[i];
		}
		
		count = 0;
		
		for(int i=0; i<resourceArray.length; i++)
		{
			if(resourceArray[i].matches(searchString))
			{
				count = count + 1;
			}
		}
		
		matches = new Resource[count];
		count = 0;
		
		for(int i=0; i<resourceArray.length; i++)
		{
			if(resourceArray[i].matches(searchString))
			{
				matches[count] = resourceArray[i];
				count = count+1;
			}
		}

		return matches;
	}//getResourcesThatMatch
	
	public Resource getResourceThatMatches(ID id)
	{
		return resourceDirectory.get(id);
	}
	
	public static int numberOfBytesPerPacket(){return 456;}

}//class