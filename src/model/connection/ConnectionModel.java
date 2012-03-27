package model.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import model.contact.Contact;
import controller.MainController;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionModel.
 */
public class ConnectionModel {
	
	/** The Constant LISTENER_PORT. */
	private static final int LISTENER_PORT = 1234;
	
	/** The controller. */
	private MainController controller;
	
	/** The sender sockets. */
	private HashMap<String, Socket> senderSockets = new HashMap<String, Socket>();
	
	/** The connection listener. */
	private ConnectionListener connectionListener;
		
	/**
	 * Instantiates a new connection model.
	 *
	 * @param controller the controller
	 */
	public ConnectionModel(MainController controller) {
		this.controller = controller;		
		connectionListener = new ConnectionListener(controller);
		connectionListener.setListenerPort(LISTENER_PORT);
		Thread connectionListenerThread = new Thread(connectionListener);
		connectionListenerThread.start();		
	}
	
		
	/**
	 * Send message.
	 *
	 * @param message the message
	 * @param ipAddress the ip address
	 */
	public void sendMessage(String message, String ipAddress) {
		Socket senderSocket = senderSockets.get(ipAddress);
		byte[] sendBuffer = message.getBytes();
		try {
			OutputStream outputStream = senderSocket.getOutputStream();
			outputStream.write(sendBuffer);
		} catch (IOException e) {
			controller.connectionClosed(ipAddress);
		}	
	}
	
	/**
	 * Connect client.
	 *
	 * @param ipAddress the ip address
	 */
	public void connectClient(String ipAddress) {
		InetSocketAddress inetAddress = new InetSocketAddress(ipAddress, LISTENER_PORT);
		Socket socket = new Socket();
		try {
			socket.connect(inetAddress, 2000);
		} catch (IOException e) {
			controller.connectionAttempted(false, ipAddress);
			return;
		} 
		senderSockets.put(ipAddress, socket);
		controller.connectionAttempted(true, ipAddress);		
	}

	/**
	 * Removes the sender.
	 *
	 * @param ipAddress the ip address
	 */
	public void removeSender(String ipAddress) {
		if (senderSockets.containsKey(ipAddress)) {
			senderSockets.remove(ipAddress);
		}
	}

	/**
	 * Gets the contacts online status.
	 *
	 * @param contacts the contacts
	 * @return the contacts online status
	 */
	public HashMap<Contact, Boolean> getContactsOnlineStatus(ArrayList<Contact> contacts) {
		HashMap<Contact, Boolean> onlineContactMap = new HashMap<Contact, Boolean>();
		for (Contact contact : contacts) {
			onlineContactMap.put(contact, senderSockets.containsKey(contact.ipAddress));	
		}
		return onlineContactMap;
	}


	/**
	 * Sender socket exists.
	 *
	 * @param ipAddress the ip address
	 * @return true, if successful
	 */
	public boolean senderSocketExists(String ipAddress) {
		return senderSockets.containsKey(ipAddress);
	}
	
	/**
	 * Checks if is client resolved.
	 *
	 * @param ipAddress the ip address
	 * @return true, if is client resolved
	 */
	public boolean isClientResolved(String ipAddress) {
		InetSocketAddress inetAddress = new InetSocketAddress(ipAddress, LISTENER_PORT);
		return !inetAddress.isUnresolved();	
	}


	/**
	 * Disconnect client.
	 *
	 * @param ipAddress the ip address
	 */
	public void disconnectClient(String ipAddress) {
		if (!senderSockets.containsKey(ipAddress)) {
			return;
		}
		Socket senderSocket = senderSockets.get(ipAddress);
		try {
			senderSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		removeSender(ipAddress);
	}


}
