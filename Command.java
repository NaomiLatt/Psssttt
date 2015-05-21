

/*
 Lauren M DiGregorio
 April 18, 2014
  
  this is For the final project
	  This class contains all the commands a user can call from the user interface
	  
	  Class Variables
	  	commandName
	  		It is a String that contains the name of the command called by the user
	  		
	  	description
	  		this is a description of the command
	  	
	  	parameters
	  		string containing parameters
	  		
	  Constructors
	  	
	  	Command()
	  		a default constructor that allows a CommandNone error object to be made so there
	  		is no error thrown
	
		Command(String commandName, String description)
	  		constructor that builds the command 
	
		Methods
			public abstract void execute()
				abstract method implemented by subclasses as to what command is performed
			
			public Object clone()
				clones an object, makes a field for field copy of an object by calling Object.clone().
				throws a CloneNotSupportedException, make call to super class and then check what is returned
				mutable objects need to be modified
				
			public int compareTo(Command other)
				Objects that implement this interface can be used as keys in a sorted map or as 
				elements in a sorted set, without the need to specify a comparator.
				For example, if one adds two keys a and b such that (!a.equals(b) && a.compareTo(b) == 0) to a 
				sorted set that does not use an explicit comparator, 
				the second add operation returns false (and the size of the sorted set does not increase) because a and b 
				are equivalent from the sorted set's perspective.
			
			public boolean equals(String string)
				returns true or false whether the string--command--matches 
			
			public boolean equals(Object object)
				returns true or false whether the object--command--matches
			
			public String getCommandName()
				returns the private variable commandName
			
			public String getDescription()
				returns the private variable descriptor
				
			public String getParameters()
				returns the private variable parameters
			
			public String[] getParameters(String delimiters)
				returns an array of strings when the parameter is plural
			
			public int hashCode()
				returns the hashCode of the object to be used in equals
			
			public boolean hasParameters()
				returns boolean false if private variable parameters equals "", else return true
			
			public void print(String message)
				prints a message to the console
				
			public void println()
				prints a new line 
				
			public void println(String message)
				prints a message to the console and adds a newline character 
			
			public void setParameters(String parameters)
				sets the parameters of the command
				
			public String toString()
				prints out all the current variables in the object
 */
public abstract class Command implements Cloneable, Comparable<Command>
{
	private String commandName;
	private String description;
	private String parameters;

	public Command()
	{	//a default constructor that allows a CommandNone error object to be made so there
		//is no error thrown
		
		
	}
	public Command(String commandName, String description)
	{
		//constructor that builds the command 
		if(commandName == null)	{throw new IllegalArgumentException("In Command, commandName cannot be null");}
		if(description == null)	{throw new IllegalArgumentException("In Command, description cannot be null");}
		
		this.commandName = commandName;
		this.description = description;
	}

	public abstract void run();
	
	
	public Object clone()
	{	//clones an object, makes a field for field copy of an object by calling Object.clone().
		//throws a CloneNotSupportedException, make call to super class and then check what is returned
		//mutable objects need to be modified
		return this.clone();
	}	
	public int compareTo(Command other)
	{	//Objects that implement this interface can be used as keys in a sorted map or as 
		//elements in a sorted set, without the need to specify a comparator.
		//For example, if one adds two keys a and b such that (!a.equals(b) && a.compareTo(b) == 0) to a 
		//sorted set that does not use an explicit comparator, 
		//the second add operation returns false (and the size of the sorted set does not increase) because a and b 
		//are equivalent from the sorted set's perspective.
		int result;
		result = this.compareTo(other);
		return result;
		
	}
	public boolean equals(String string)
	{
		//returns true or false whether the string--command--matches 
		boolean result;
		
		if(this.hashCode() == string.hashCode()) { result = true;  }
		else						  			 { result = false; }
		
		return result;
	}
	
	public boolean equals(Object object)
	{
		//returns true or false whether the object--command--matches
		boolean result;
		
		if(this.hashCode() == object.toString().hashCode()) { result = true;  }
		else												{ result = false; }
		
		return result;
	}
	
	public String getCommandName()
	{
		//returns the private variable commandName
		return this.commandName;
	}
	
	public String getDescription()
	{
		//returns the private variable descriptor
		return this.description;
	}
		
	public String getParameters()
	{
		//returns the private variable parameters
		return this.parameters;
	}
	
	public String[] getParameters(String delimiters)
	{
		//returns an array of strings when the parameter is plural
		//check to make sure delimiter is in the string
		String[] result;
		
		result = this.parameters.split(delimiters);
		
		return result;
		
	}
	
	public int hashCode()
	{
		//returns the hashCode of the object to be used in equals
		return this.toString().hashCode();
	}
	
	public boolean hasParameters()
	{
		//returns boolean false if private variable parameters equals "", else return true
		boolean result;
		if(this.parameters == "")	{ result = true; }
		else						{ result = false;}
		
		return result;
	}
	
	public void print(String message)
	{
		//prints a message to the console
		System.out.print(message);
	}
		
	public void println()
	{
		//prints a new line 
		System.out.println();
	}
		
	public void println(String message)
	{
		//prints a message to the console and adds a newline character 
		System.out.println(message);
	}
	
	public void setParameters(String parameters)
	{
		//sets the parameters of the command
		if(parameters == null ) {throw new IllegalArgumentException("In command, you cannot set null as your parameter.  Try again.");}
		this.parameters = parameters;
	}
		
	public String toString()
	{
		//prints out all the current variables in the object
		String result;
		result = "Command object doing " + this.commandName + " and the description of that is " + this.description + " and the parameters being passed are " + this.parameters;
		return result;
	}
}
