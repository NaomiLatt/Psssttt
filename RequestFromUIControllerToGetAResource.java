
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.net.*;

	public class RequestFromUIControllerToGetAResource extends RequestFromUIController implements Runnable
	{	
		private ValuesInRange       arrived;
		private PeerController      peerController;
		private ID                  resourceID;
		private ArrayList<ID> 		responses;
		//private long				resourceLength;

		public RequestFromUIControllerToGetAResource (ID requestID, ID resourceID, long resourceLength)
		{
			super(requestID);
			
			if(resourceID == null){throw new IllegalArgumentException("resourceID is null");}
	  
			this.arrived = new ValuesInRange(1, (int) (Math.ceil(resourceLength / (double) 456)));
			this.resourceID = resourceID;
			//this.resourceLength = resourceLength;
	  
		} // constructor
  
		private ID             getResourceID () 	{ return this.resourceID; }
		private PeerController getPeerController() 	{ return this.peerController;}
		private int            totalParts    () 	{ return arrived.getMaximumValueInRange(); }
  
		public void updateRequest ( UDPMessage udpMessage )
		{
			byte[]                buffer;
			DatagramPacket        datagramPacket;
			long                  endingByte;
			int                   partNumber;
			byte[]				  partNumberBytes;
			long                  startingByte;
			
		   // ID1 contains the matching resource ID.
		   // ID2 contains the original RequestID from our UIController
		   // These two must be swapped before sending to the UIController.
		   // In addition, the payload part contains a random id followed by
		   // a part number. This must be converted into starting byte number
		   // and ending byte number before being sent to the UIController

			if(!responses.contains(resourceID))
			{
				//only process this response if we haven't already done so
				//add resourceID to our list of responses, so we won't process it again
				responses.add(resourceID);
		  
				synchronized ( this.arrived )
				{
					partNumberBytes = new byte[PartNumbers.getLengthInBytes()];
					
					System.arraycopy(udpMessage.getMessage(), ID.getLengthInBytes(), partNumberBytes, 0, PartNumbers.getLengthInBytes());
					partNumber = PartNumbers.getFromByteArray(partNumberBytes);
					
					if ( !this.arrived.contains ( partNumber ) )
					{
						this.arrived.setFor ( partNumber );
					
				     //  calculate the starting and ending byte numbers
							startingByte = (partNumber-1) * 456;
							endingByte = (partNumber * 456);
		
				     //  put the data from the file into buffer
							buffer = new byte[(int)(endingByte - startingByte + 1)];
							System.arraycopy(udpMessage.getMessage(), (ID.getLengthInBytes() + PartNumbers.getLengthInBytes()), buffer, 0, (int) (endingByte - startingByte));
		
				     //  put the starting, ending byte numbers, and file data into a byte array
							ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
							
							try
							{
								byteArrayOutputStream.write(new Long(startingByte).byteValue());
								byteArrayOutputStream.write(new Long(endingByte).byteValue());
								byteArrayOutputStream.write(buffer);
							}
							catch(Exception e){ e.printStackTrace(); }
							
							byte[] message = byteArrayOutputStream.toByteArray();
							
				     //  package it up and send it to the UIController
							UDPMessage responseMessage = new UDPMessage(udpMessage.getID2(), udpMessage.getID1(), new TimeToLive(15), message);
							
							datagramPacket = responseMessage.getDatagramPacket(); 
							
							datagramPacket.setSocketAddress(this.getUIControllerAddress());
							datagramPacket.setPort(12346);
							
							this.getQueue().enQueue(datagramPacket);
				     //  let the request thread know it can now request another part
				       		this.arrived.notify();
		     
					} // if ( !this.arrived [ partNumber ] )
				}  //  synchronized ( this.arrived )
			}	//if(!responses.contains(resourceID))	
		}	//   public void updateRequest ( UDPMessage udpMessage )
		
		public void run()
		{
			byte[]         buffer;
			DatagramPacket datagramPacket;
			byte[]		   partNumberInBytes;
			UDPMessage     udpMessage;
		  
			//  request each part of this resource. keep the requests synchronized with
			//  the responses such that a request for another part is not made until the
			//  response to the previous part request has been received.
		  
			for (int i=1; i<=this.totalParts(); i++)
		    {
				synchronized ( this.arrived )
				{
					//  create and mail the request
					buffer = new byte[20];
					
					partNumberInBytes = Utilities.getBytes(i);
					
					//Copy the ID and Part number to the buffer
					System.arraycopy(ID.idFactory().getBytes(), 0, buffer, 0, 						ID.getLengthInBytes());
					System.arraycopy(partNumberInBytes, 		0, buffer, ID.getLengthInBytes(), 	PartNumbers.getLengthInBytes());
					
					udpMessage = new UDPMessage(this.getRequestID(), this.getResourceID(), new TimeToLive(500), buffer);
					
					GossipPartners.getInstance().send(udpMessage);;
					
					//  wait to be notified that the part that was just requested has been received
					while ( ! this.arrived.contains ( i ) ) { try {this.arrived.wait(); } catch (InterruptedException ei) {}  }
				} // synchronized ( this.arrived )
		    }  // for
		}  // public void run()
}// class