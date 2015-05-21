import java.math.BigInteger;
import java.net.*;
import java.io.UnsupportedEncodingException;
import java.lang.*;
public class TestUDPMessage 
{

	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException 
	{
		// TODO Auto-generated method stub
		UDPMessage message1;
		UDPMessage message2;
		UDPMessage message3;
		
		ID id1;
		ID id2;
		ID id3;
		ID id4;

		TimeToLive 		timeToLive;
		String 			stringMessage;
		String			datagramMessage;
		byte[] 			byteMessage;
		DatagramPacket	datagramPacket;
		byte[]			buffer;
		int 			size;
		
		id1 = ID.idFactory();
		id2 = ID.idFactory();
		id3 = ID.idFactory();
		id4 = ID.idFactory();
		
		stringMessage 	= new String("Work homie");
		byteMessage		= "Twerk homie".getBytes();
		datagramMessage	= "this is twerking";
		
		size 			= ID.getLengthInBytes()+ID.getLengthInBytes()+ TimeToLive.getLengthInBytes() + datagramMessage.getBytes().length;
		//size 			= ID.getLengthInBytes()+ID.getLengthInBytes()+ TimeToLive.getLengthInBytes();
		//size = 500;
		
		buffer			= new byte[size];
		timeToLive 		= new TimeToLive(4);
		datagramPacket	= new DatagramPacket(buffer, buffer.length);
		
		
		
		
		
		
		//datagramPacket.setData("This is twerking!!!".getBytes());
		
		
		
		
		
		System.out.println((ID.getLengthInBytes() * 2) + TimeToLive.getLengthInBytes());
		System.out.println(UDPMessage.getMinimumPacketSizeInBytes());
		
		System.out.println(buffer.length);
		System.out.println(size);
		System.out.println("output: " + (ID.getLengthInBytes() * 2));
		System.out.println(timeToLive.getBytes());
		System.out.println(  TimeToLive.getLengthInBytes()    );
		System.out.println(  id3.getBytes()    );
		
		
		
		
		
		//ARGS: 		(1)  src obj, 	(2)srcPos,     (3) Object des, (4)destPos, (5)Length
		
		
		//System.arraycopy(datagramPacket, 0, 		this.id, 0, ID.getLengthInBytes());
		
		
		//need to set data
		//System.arraycopy(data, 0, this.id1.getBytes(), 0, ID.getLengthInBytes());
		System.arraycopy(id3.getBytes(), 0, buffer, 0, ID.getLengthInBytes());
		
		//Copy second ID
		//System.arraycopy(data, ID.getLengthInBytes(), this.id2.getBytes(), 0, ID.getLengthInBytes());
		System.arraycopy(id4.getBytes(), 0, buffer, ID.getLengthInBytes(), ID.getLengthInBytes());
		
		//Copy TimeToLive
		//System.arraycopy(data, (ID.getLengthInBytes() * 2), this.timeToLive.getBytes(), 0, TimeToLive.getLengthInBytes());
		System.arraycopy(timeToLive.getBytes(), 0, buffer, (ID.getLengthInBytes() * 2), TimeToLive.getLengthInBytes());
		
		//Copy message
		//System.arraycopy(data, (ID.getLengthInBytes() * 2) + TimeToLive.getLengthInBytes(), this.message, 0, UDPMessage.getMinimumPacketSizeInBytes());
		System.arraycopy(datagramMessage.getBytes(), 0, buffer, (ID.getLengthInBytes() * 2) + TimeToLive.getLengthInBytes(), datagramMessage.getBytes().length);
		
		//set the address (local host) and port on the datagramPacket
		datagramPacket.setAddress(InetAddress.getByName("192.168.141.1"));
		datagramPacket.setPort(54321);
		
		
		//NEED TO FORMAT DATAGRAM PACKET CORRECTLY IN ORDER TO GET THIS TO WORK
		datagramPacket.setData(buffer);
		
		message1 = new UDPMessage(id1, id2, timeToLive, stringMessage);
		message2 = new UDPMessage(id3, id4, timeToLive, byteMessage);
		message3 = new UDPMessage(datagramPacket);
		
		
		System.out.println();
		System.out.println("Message 1: " + message1.getMessage());
		System.out.println("Message 2: " + message2.getMessage());
		System.out.println("Message 3: " + message3.getMessage());
		
		String outPut1 = new String(message1.getMessage(), "UTF-8");
		String outPut2 = new String(message2.getMessage(), "UTF-8");
		String outPut3 = new String(message3.getMessage(), "UTF-8");
		
		
		System.out.println("Converted Message1: "+ outPut1);
		System.out.println("Converted Message2: "+ outPut2);
		System.out.println("Converted Message3: "+ outPut3);
		
	}

}
