import java.util.HashMap;
import java.util.Map;

public class ResponsesFromFindRequests
{
	private String[] descriptions;
	private HashMap<Integer, ID> mapOfIDs;
	private Integer mapNumber;
	private static ResponsesFromFindRequests instance = new ResponsesFromFindRequests();
	
	public ResponsesFromFindRequests()
	{
		this.descriptions = new String[25];
		this.mapOfIDs = new HashMap<Integer, ID>();
		this.mapNumber = new Integer(0);
	}
	
	public static ResponsesFromFindRequests getInstance()
	{
		return ResponsesFromFindRequests.instance;
	}
	
	public static ResponsesFromFindRequests newInstance()
	{
		return ResponsesFromFindRequests.getInstance();
	}
	
	public void addID(ID id, String description)
	{
		//System.out.println("SUCCESSFULLY ADDED ID WITH DESCRIPTION: " + description);
		this.mapOfIDs.put(this.mapNumber, id);
		
		descriptions[this.mapNumber] = description;
		
		this.mapNumber++;
		
		if(this.mapNumber > 24)
		{
			this.mapNumber = 0;
		}
	}
	
	public void addID(ID id)
	{
		this.mapOfIDs.put(this.mapNumber, id);;
		
		this.mapNumber++;
	}
	
	public ID getID(int spot)
	{
		return this.mapOfIDs.get(spot);
	}
	
	public void printDescriptions()
	{
		System.out.println("PRINTING DESCRIPTIONS");
		for(int i=0; i<this.descriptions.length; i++)
		{
			if(this.descriptions[i] != null)
			{
				System.out.println("Spot number: " + i + " has description: " + descriptions[i]);
			}
		}
	}
	
	public void printOptions()
	{
		System.out.println("SIZE: " + this.mapOfIDs.size());
		for(Map.Entry<Integer, ID> cursor : this.mapOfIDs.entrySet())
		{
			System.out.println("Key: " + cursor.getKey() + "ID: " + cursor.getValue());
		}
	}
}
