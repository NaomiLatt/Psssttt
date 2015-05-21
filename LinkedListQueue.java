
public class LinkedListQueue 
{

	/*
	Lauren M DiGregorio
	  Feb 11, 2015
	  
	  
	  This class will create a array or linkedlist objects. ASSIGNMENT 1
	  
	  Class Variables
	  	front
	  		Is a Node object that points to the front of the list
		rear
			Node that points to the end of the linkedlist
			
	  Constructors
	  	
	  	LinkedListQueue()
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
    			
		Modification History
			Feb 23 2015
				Finished first iteration of assignment
				
			Mar 4, 2015
				fixed node probelm, and parameter checked on enQueue
	  	`	May 5, 2015
				modified peek method to not throw QueueUnderflow exception
	  
	 */
	
	private Node front;
	private Node rear;
	
	public LinkedListQueue()
	{
		//set node equal to null to signify the beginning of node
		this.front = null;
		this.rear = null;
		
	}//end constructor
	
	public void removeAll()
	{
		//Retrieves and removes items from queue. This method 
		//differs from poll only in that it throws an exception if this queue is empty.
		this.front = null;
		this.rear = null;
		
	}//end removeAll
	
	public boolean isEmpty()
	{
		//returns True if list is empty
		boolean result;
		
		result = false;
		
		if(this.front == null)
		{
			result = true;
		}
		return result;
	}//end isEmpty
	
	
	public Object peek()
	{	
		Object data;

		if(this.isEmpty())
		{
			
			//throw new QueueUnderflowException();
			data = null;
		}
		
		//return this.front.getData();
		else
		{
			data = this.front.getData();
		}
		return data;
		
	}//end peek
	
	public Object deQueue()
	{
		
		Node frontOfLine;
		Object data;
		
		if(this.isEmpty())
		{
			data = null;
		}
		else
		{
			//gets the front value 
			frontOfLine = this.front;
			
			//sets new front
			this.front = this.front.getNext();

			//returns value that was taken off the queue
			data =  frontOfLine.getData();
		}
		
		return data;
		
		
	}//end deQueue
	
	public void enQueue(Object data)
	{
		//queues only add objects at the end of the queue. No where else
		//set front and rear
		Node newNode = new Node(data, null);
		
		if(this.isEmpty())
		{
			this.front = newNode;
			this.rear = this.front;
			
		}
		else
		{

			//set next of current rear, now contains pointer to next node
			this.rear.setNext(newNode);
			this.rear = newNode;
		}//end else
		
	}//end enQueue
	
	public void enQueue(LinkedListQueue queue)
	{

		Node data;

		if(queue == null)
		{
			throw new IllegalArgumentException("Parameter passed into enQueue is equal to null");
		}
		
		if(queue == this)
		{
			throw new IllegalArgumentException("Parameter passed is equal to itself");
		}
		
		if(this.isEmpty())
		{
			this.front = queue.front;
			this.rear = queue.rear;	
			
		}
		else
		{

			//this gets rid of null
			this.rear.setNext(queue.front);
			this.rear = queue.rear;
		}
	
		queue.removeAll();
	}//end enQueue
	
	
	 class Node
	 {
		/*
		This nested class will set the nodes in the linkedList -- each queue index has a linked list
		  
		  Class Variables
		  	data
		  		Data is an Object that holds the number that needs to be indexed
			next
				Node that points to the next node
				
			
		  Constructors
		  	
		  	Node(Object data, Node next)
		  		Constructs the Node class while setting the variables 
		
			Methods
				public Object getData()
					Returns Object with the data
					
				public Node getNext()
					Returns next available Node 
					
				public void setNext(Node next)
	  				Sets the next node
	  				
		Modification History
			Feb 23 2015
				Finished first iteration of assignment
				
		 */
		private Object data;
		private Node next;
		
		public Node(Object data, Node next)
		{
			//how do you check parameters
			this.data = data;
			this.next = next;

		}//end constructor
		
		public Object getData()
		{
		//Returns Object with the data
			return this.data;
		}
		public Node getNext()
		{
			//Returns next available Node
			return this.next;
		}
		
		public void setNext(Node next)
		{
			//Sets the next node with this.next
			this.next = next;
		}
	}//end Node class
}
