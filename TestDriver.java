import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
/*UNIT TESTS
 * 
 * this driver tests to make sure the enQueue and deQueue work correctly are set correctly
 * 
 * Test 1: LinkedListQue
 * Test 2: SynchronizedPacketQueue
 * Test 3: SynnchronizedPacketQueue : OutgoingPacketQueue
 * Test 4: SynnchronizedPacketQueue : IncomingPacketQueue
 * Test 5: console
 **/



public class TestDriver {

	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		System.out.println("*********************************************************************************  Test LinkedListQue");
		LinkedListQueue que;
		LinkedListQueue que2;
		//System.out.print("working");
		
		
		que2 = new LinkedListQueue();
		que = new LinkedListQueue();
		
		que.enQueue(1);
		que.enQueue(3);
		que.enQueue(5);
		que.enQueue(7);
		que.enQueue(9);
		
		
		
		que2.enQueue(2);
		que2.enQueue(4);
		que2.enQueue(6);
		que2.enQueue(8);
		que2.enQueue(10);
		
		
		que.enQueue(que2);
		
		System.out.println("Que1 is empty ture or false: "+ que.isEmpty());
		
		Object data;
		//what to return Node's next node
		
		//this tests deQueue and enQueue(data)
		data = que.deQueue();
		System.out.printf("deQueue of que1 %d", data);

		
		System.out.print("Que1 is empty ture or false: " + que.isEmpty());
		while( !que.isEmpty() )
		{
			System.out.println("made it in queue");
			System.out.println(que.deQueue());
			
			
			System.out.println(que.isEmpty());
		}
		
		
		System.out.println("********************************************************************************* Test SynchronizedPacketQueue : OutgoingPacketQueue");
		
		
		SynchronizedPacketQueue que5;
		SynchronizedPacketQueue que6;
		
		byte[] buffer = new byte[3];
		
		DatagramPacket datapacket1 = new DatagramPacket(buffer, 1);
		DatagramPacket datapacket2 = new DatagramPacket(buffer, 1);
		DatagramPacket datapacket3 = new DatagramPacket(buffer, 1);
		DatagramPacket datapacket4 = new DatagramPacket(buffer, 1);
		DatagramPacket datapacket5 = new DatagramPacket(buffer, 1);
		DatagramPacket datapacket6 = new DatagramPacket(buffer, 1);
		
		System.out.print("working1");
		
		que5 = new OutgoingPacketQueue();
		que6 = new OutgoingPacketQueue();
		System.out.print("working2");
		que5.enQueue(datapacket1);
		que5.enQueue(datapacket3);
		que5.enQueue(datapacket5);

		
		System.out.print("working3");
		
		que6.enQueue(datapacket2);
		que6.enQueue(datapacket4);
		que6.enQueue(datapacket6);

		
		System.out.println("working4");
		que5.enQueue(que6);
		
		System.out.println("Que5 is empty true or false: "+ que5.isEmpty());
		
		DatagramPacket data2;
		//what to return Node's next node
		
		//this tests deQueue and enQueue(data)
		data2 = que5.deQueue();
		System.out.println("deQueue of que1 " + data2.getLength());

		
		System.out.println("Que5 is empty ture or false: " + que5.isEmpty());
		while( !que5.isEmpty() )
		{
			System.out.println("made it in queue");
			System.out.println(que5.deQueue());
			
			
			System.out.println(que5.isEmpty());
		}
		
		
		System.out.println("********************************************************************************* Test SynchronmizedPackeTQueue: IncomingPacketQueue");
	
		SynchronizedPacketQueue que7;
		SynchronizedPacketQueue que8;
		
		byte[] buffer2 = new byte[3];
		
		DatagramPacket dp1 = new DatagramPacket(buffer2, 1);
		DatagramPacket dp2 = new DatagramPacket(buffer2, 1);
		DatagramPacket dp3 = new DatagramPacket(buffer2, 1);
		DatagramPacket dp4 = new DatagramPacket(buffer2, 1);
		DatagramPacket dp5 = new DatagramPacket(buffer2, 1);
		DatagramPacket dp6 = new DatagramPacket(buffer2, 1);
		
		System.out.print("working1");
		
		que7 = new OutgoingPacketQueue();
		que8 = new OutgoingPacketQueue();
		System.out.print("working2");
		que7.enQueue(dp1);
		que7.enQueue(dp3);
		que7.enQueue(dp5);

		
		System.out.print("working3");
		
		que8.enQueue(dp2);
		que8.enQueue(dp4);
		que8.enQueue(dp6);

		
		System.out.println("working4");
		que7.enQueue(que8);
		
		System.out.println("Que7 is empty true or false: "+ que7.isEmpty());
		
		DatagramPacket data3;
		//what to return Node's next node
		
		//this tests deQueue and enQueue(data)
		data3 = que7.deQueue();
		System.out.println("deQueue of que1 " + data3.getLength());

		
		System.out.println("Que7 is empty ture or false: " + que7.isEmpty());
		while( !que7.isEmpty() )
		{
			System.out.println("made it in queue");
			System.out.println(que7.deQueue());

			System.out.println(que7.isEmpty());
		}
		
	
	//*********************************************************************************************Test console
	
		//console one
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String");
        String s = br.readLine();
        System.out.print("Enter Integer:");
        try{
            int i = Integer.parseInt(br.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }

	}// end main

}
