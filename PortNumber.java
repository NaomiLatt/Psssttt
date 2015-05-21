/*
 Lauren M DiGregorio
 April 18, 2014
  
  this is For the final project
	  This class models a port number
	  
	  Class Variables
	  	int Port
		
	  Constructors
	  	
	  	PortNumber(int portNumber)
	  		Creates a portnumber class
	
		Methods
			public int get()
				returns port instance variable
			
			public String toString()
				returns port instance variable as a string
				
 */
public class PortNumber 
{
	private int port;
	

  	public PortNumber(int portNumber)
  	{
  		//Creates a portnumber class
  		//find upper range of valid port numbers
  		if( portNumber < 0 ) { throw new IllegalArgumentException("Port Number passed to PortNumber class is less than zero.  Try again.");}
  		
  		this.port = portNumber;
  	
  	}//end PortNumber
  		


	public int get()
	{
		//returns port instance variable
		return this.port;
	}
	
	public String toString()
	{
		//returns port instance variable as a string
		String dbug;
		dbug = "The portNumber is: " + this.get();
		return dbug;
	}
}
