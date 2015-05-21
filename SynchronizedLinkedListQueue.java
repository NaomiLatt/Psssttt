
/*
Lauren M DiGregorio
	  April 12, 2015
	  
	  For the final project
	  This class will create a synchronized linkedlist objects
	  
	  Class Variables
	  	front
	  		Is a Node object that points to the front of the list
		rear
			Node that points to the end of the linkedlist
			
		
	  Constructors
	  	
	  	SynchronizedLinkedListQueue()
	  		sets Front and Rear node for LInkedListQueue 
	
	
		Methods
			public void removeAll()
				removes everything in the queue
				
			public boolean isEmpty()
				returns True if queue is empty 
				
			public Object peek()
  				peek is a common method in queue implementation. it returns
		    	the object at the front of the queue without deleting the
		    	object from the front of the queue. That is, the queue is
		    	unchanged by the peek method.
			
			public Object deQueue()
				Only the object at the front of the queue can be removed.
    			That is how the Queue data structure is defined
			
			public void enQueue(Object data)
				queues only add objects at the end of the queue. No where else
				
			public void enQueue(LinkedListQueue queue)
				Place all the elements of the otherQueue on the end of this
    			queue. This is shown here as O(n) operation. In your linked
    			list implementation it must be an O(1) operation.

*/

public class SynchronizedLinkedListQueue extends LinkedListQueue 
{
	
	public SynchronizedLinkedListQueue()
	{
		//set node equal to null to signify the beginning of node
		super();
		
	}//end constructor
	
	public synchronized void removeAll()
	{
		//Retrieves and removes items from queue. This method 
		//differs from poll only in that it throws an exception if this queue is empty.
		super.removeAll();

	}//end removeAll
	
	public synchronized boolean isEmpty()
	{
		//returns True if list is empty

		return super.isEmpty();
	}//end isEmpty
	
	
	public synchronized Object peek()
	{
		// peek is a common method in queue implementation. it returns
		// the object at the front of the queue without deleting the
		// object from the front of the queue. That is, the queue is
		// unchanged by the peek method.
		
		//Retrieves, but does not remove, the head of this queue, or 
		//returns null if this queue is empty
		
		//the head of this queue, or null if this queue is empty

		return super.peek();
		
	}//end peek
	
	public synchronized Object deQueue()
	{
		
		//Only the object at the front of the queue can be removed.
		//That is how the Queue data structure is defined
		//removes the first thing in the queue
		
		//sets this.front to be equal to the second node
		
		return super.deQueue();
		
		
	}//end deQueue
	
	public synchronized void enQueue(Object data)
	{
		//queues only add objects at the end of the queue. No where else
		//set front and rear
		super.enQueue(data);
		
	}//end enQueue
	
	public synchronized void enQueue(SynchronizedLinkedListQueue queue)
	{
		//Place all the elements of the otherQueue on the end of this
		//queue. This is shown here as O(n) operation. In your linked
		//list implementation it must be an O(1) operation.
		//super.enQueue(queue);
		super.enQueue(queue);
	}//end enQueue
	
	

	
}//end class
