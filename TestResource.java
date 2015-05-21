import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class TestResource 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String string;
		string = "Naomi loves frogs lots!";
		System.out.println("Please be true! "+string.contains(" frog"));

		ResourceManager rman;
		rman = ResourceManager.newInstance();
		rman.loadResourcesFrom(new File("/C:/Users/ldigregorio/Desktop/Resources.txt"),'#');

		Resource[] resources;
		resources = new Resource[rman.getResourcesThatMatch("Code From Jarvis class").length];
		resources = rman.getResourcesThatMatch("Code From Jarvis class");
		
		
		for(int i=0;i<resources.length;i++)
		{
			System.out.println(resources[i].getDescription());
			System.out.println(resources[i].getID());
			System.out.println(resources[i].getMimeType());
			System.out.println(resources[i].getSizeInBytes());
			System.out.println(resources[i].getLocation());
		}

	}
}
