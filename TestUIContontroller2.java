import java.io.IOException;
import java.net.SocketException;


public class TestUIContontroller2 {

	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub

		UIController 			peerController;
		PortNumberForSending 	portOut2;
		PortNumberForReceiving 	portIn2;
		
		portOut2 		= new PortNumberForSending(242);
		portIn2			= new PortNumberForReceiving(244);
		
	//	portOut2 		= new PortNumberForSending(242);
	//	portIn2			= new PortNumberForReceiving(244);
		peerController 	= new UIController(portIn2, portOut2, 100);
		
		peerController.start();
		
	}

}
