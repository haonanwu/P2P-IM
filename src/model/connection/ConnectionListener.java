package model.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.MainController;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving socket events.
 * The class that is interested in processing a socket
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSocketListener<code> method. When
 * the socket event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SocketEvent
 */
public class ConnectionListener implements Runnable {
	
	/** The server socket. */
	private ServerSocket serverSocket;
	
	/** The controller. */
	private MainController controller;


	/**
	 * Instantiates a new socket listener.
	 *
	 * @param controller the controller
	 */
	public ConnectionListener(MainController controller) {
		this.controller = controller;
	}
	
	/**
	 * Sets the server port.
	 *
	 * @param port the new listener port
	 */
	public void setListenerPort(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			while (true) {
				Socket receiverSocket = serverSocket.accept();
				MessageReceiver messageReceiver = 
						new MessageReceiver(receiverSocket, controller);
				Thread messageReceiverThread = new Thread(messageReceiver);
				messageReceiverThread.start();
				String ipAddress = receiverSocket.getInetAddress().toString().replace("/", "");
				controller.receiverAdded(ipAddress);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
