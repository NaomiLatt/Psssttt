import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;


public class TestUIController 
{

	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		UIController 			uiController;
		PortNumberForSending 	portOut;
		PortNumberForReceiving 	portIn;
		

		
		portOut 		= new PortNumberForSending(241);
		portIn 			= new PortNumberForReceiving(243);
		uiController 	= new UIController(portIn, portOut, 100);
		
		
		uiController.start();
		
	}

}
