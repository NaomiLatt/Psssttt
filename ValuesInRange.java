
public class ValuesInRange 
{
	/*
	Lauren M DiGregorio
	  Feb 11, 2015
	  
	  This class will process a bitmap
	  
	  Class Variables
	  	bitMap
	  		Is a private int array
		maxiumValueInRange
			Int that hold the max number in the bitmap int array
			
		int minimumVlaueInRange
			Int that hold the min number in the bitmap int array
	  
	  Constructors
	  	
	  	ValuesInRange(int minimumValueInRange, int maximumValueInRange)
	  		Creates a new ValuesInRange object with minValueInRange and maxValueInRange as parameters.  Uses parameters to generate an 
	  		array containing numbers in that range.  Numbers can be duplicated or unused. 
	
	
		Methods
		
			private void checkValue(int value)
		   		Checks that any value passed down to the function is within
		   		the specified range.
	   
		  	public void clearFor(int value)
		   		Clears a bit in the array by reducing its value to 0.
		   
		   	public boolean contains(int value)
		   		Checks if the given value is in the array, returns true if
		    		it is present and otherwise false.
		   
		   	private int getBitNumberFor(int value)
		   		Returns the bit number for a given element
		  
		  		private int getElementNumberFor(int value)
		  			Returns the index of the array that a value can be found at
		  
		   	public int getMinimumValueInRange()
		   		Returns this.minimumValueInRange
		   	
		   	public int getMaximumValueInRange()
		   		Returns this.maximumValueInRange
		   	
		   	private int getNumberOfBitsInEachMap()
		  			Returns the number of bits in each map
		  
		   	public void setFor(int value)
		   		Sets the bit in the array for the value passed.   
		  
		 */
	private int [] theBitMap;
	private int maximumValueInRange;
	private int minimumValueInRange;
	
	
	public ValuesInRange(int minimumValueInRange, int maximumValueInRange) {
		this.minimumValueInRange = minimumValueInRange;
		this.maximumValueInRange = maximumValueInRange;
		
		//Check if the Max is greater than Min
		if (this.maximumValueInRange<this.getMinimumValueInRange())
		{throw new IllegalArgumentException("The maximum value parameter ("+this.getMaximumValueInRange()+") is less than the minimum value parameter ("+getMinimumValueInRange()+")");}
		
		//Create new bitmap to hold the values
		theBitMap = new int[(int)Math.ceil(((getMaximumValueInRange())-(getMinimumValueInRange())+1)/this.getNumberOfBitsInEachMap())+1];
	}

	public int getMaximumValueInRange() {
		//returns the max value
		return maximumValueInRange;
	}

	public int getMinimumValueInRange() {
		//returns the min value
		return minimumValueInRange;
	}
	
	private void checkValue(int value)
	{
		//ensure that the value is within the given range 
		if (value<getMinimumValueInRange())
		{throw new IllegalArgumentException("Parameter value ("+value+") is less than the minimum value in the range (" +getMinimumValueInRange()+")");}
		else if(value>getMaximumValueInRange())
		{throw new IllegalArgumentException("Parameter value ("+value+") is more than the maximum value in the range (" +getMaximumValueInRange()+")");}
		}
	
	
	public void setFor(int inValue){
		
		//initialize temp variables
		int temp; 
		int position =1;
		
		//check that the value is valid
		this.checkValue(inValue); 
		
		//get the index in the bitmap 
		temp = theBitMap[this.getElementNumberFor(inValue)];
		
		//shift the bits and set the bitmap value at the index to 1
		position = 1 << this.getBitNumberFor(inValue);
		temp = temp | position;
		theBitMap[this.getElementNumberFor(inValue)] = temp;
	}
	
	public boolean contains(int inValue){
		int temp;
		
		//check that the value is valid
		this.checkValue(inValue);
		
		//shifts the bitmap and checks if it is a 1 or 0
		// if the number is a 1 then the function returns 
		// true, indicating that the value is present at 
		// least once in the bitmap 
		temp = theBitMap[this.getElementNumberFor(inValue)];
		return (((temp & (1 << this.getBitNumberFor(inValue)) )>>> this.getBitNumberFor(inValue))!=0);
	}
	
	public void clearFor(int inValue){
		int temp;
		
		//check that the value is valid
		this.checkValue(inValue);
		
		//Set the value of the bit we are interested in 
		// to 0
		temp = theBitMap[this.getElementNumberFor(inValue)] | 1;
		if( ((temp >>> this.getBitNumberFor(inValue))&1)==1){ 
			theBitMap[this.getElementNumberFor(inValue)] = temp ^ (1 << this.getBitNumberFor(inValue));
		}
	}
	
	private int getBitNumberFor(int inValue){
		//returns the index in the bitmap that the value should be stored in
		return ((inValue-getMinimumValueInRange())%this.getNumberOfBitsInEachMap());
	}
	
	private int getElementNumberFor(int inValue){
		//returns the index in the bitmap array that the value should be stored in
		return (   (int)Math.abs(     (inValue-getMinimumValueInRange()     )    )/this.getNumberOfBitsInEachMap()     );
	}
	
	private int getNumberOfBitsInEachMap(){
		//this was the number of bits in an int
		return 32;
	}
	
	public String toString(){
		//used for error checking and debugging purposes 
		System.out.println("The min is: "+ getMinimumValueInRange() + " The max is "+ getMaximumValueInRange());
		return " " +getElementNumberFor(10) + "";
	}

}
