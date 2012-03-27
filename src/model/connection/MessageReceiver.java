package model.connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import controller.MainController;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageReceiver.
 */
public class MessageReceiver implements Runnable {
	
	/** The Constant BUFFER_SIZE. */
	private static final int INPUT_BUFFER_SIZE = 1000;

	/** The receiver socket. */
	private Socket receiverSocket;
	
	/** The controller. */
	private MainController controller;
	
	/** The ip address. */
	private String ipAddress;
	
	/**
	 * Instantiates a new message receiver.
	 *
	 * @param receiverSocket the receiver socket
	 * @param controller the controller
	 */
	public MessageReceiver(Socket receiverSocket, MainController controller) {
		this.receiverSocket = receiverSocket;
		this.controller = controller;
		ipAddress = receiverSocket.getInetAddress().toString().replace("/", "");
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			InputStream inputStream = receiverSocket.getInputStream();
			byte[] inputBuffer = new byte[INPUT_BUFFER_SIZE];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(inputBuffer)) != -1) {
				byte[] actualInput = new byte[bytesRead];
				for (int i=0; i<bytesRead; i++) {
					actualInput[i] = inputBuffer[i];
				}
				String receivedMessage = new String(actualInput, "UTF-8"); 
				controller.messageRecieved(ipAddress, receivedMessage);		
				inputBuffer = new byte[INPUT_BUFFER_SIZE];
			}		
			receiverSocket.close();
			controller.connectionClosed(ipAddress);
		} catch (IOException e) {
			try {
				receiverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			controller.connectionClosed(ipAddress);
		} 		
	}

}
