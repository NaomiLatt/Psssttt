import java.net.DatagramPacket;


public class Request
	{
		private ID 				id;
		private DatagramPacket	datagramPacket;
		private String 			requestNumber;
		private UDPMessage		udpMessage;
		private int				subIndexNumber;
		
		public Request(ID id, DatagramPacket datagramPacket, String requestNumber)
		{
			this.id 			= id;
			this.datagramPacket = datagramPacket;
			this.requestNumber 	= requestNumber;
			this.udpMessage		= new UDPMessage(datagramPacket);
			this.subIndexNumber = -1;
		}
		
		public ID getID()
		{
			return this.id;
		}
		
		public DatagramPacket getDatagramPacket()
		{
			return this.datagramPacket;
		}
		
		public String getRequestNumber()
		{
			return this.requestNumber;
		}
		
		public UDPMessage getUDPMessage()
		{
			return this.udpMessage;
		}
		
		public int getSubRequestNumber()
		{
			return this.subIndexNumber;
		}
		public void setIndexNumber(String subIndex)
		{
			this.requestNumber = subIndex;
		}
		
	}