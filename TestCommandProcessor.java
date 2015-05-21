import java.net.SocketException;
import java.net.UnknownHostException;


public class TestCommandProcessor {

	public static void main(String[] args) throws SocketException, UnknownHostException 
	{
		// TODO Auto-generated method stub
		
		//will need to test the delimiter option
		CommandProcessor 		commandProcessor;
		UIController 			controller;
		
		controller = new UIController(new PortNumberForReceiving(6543), new PortNumberForSending(34567), 100);
		
	}

}
