package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.connection.ConnectionModel;
import model.contact.Contact;
import model.contact.ContactModel;
import view.ContactPanel;
import view.MainWindow;
import view.ProfilePanel;
import view.TabbedPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class MainController.
 */
public class MainController implements ActionListener, KeyListener, MouseListener {
	
	/** The main window. */
	private MainWindow mainWindow = new MainWindow(this);
	
	/** The profile panel. */
	private ProfilePanel profilePanel;
	
	/** The contact panel. */
	private ContactPanel contactPanel;
	
	/** The text stage panel. */
	private TabbedPanel tabbedPanel;
	
    /** The contact model. */
    private ContactModel contactModel = new ContactModel();
    
    /** The connection model. */
    private ConnectionModel connectionModel = new ConnectionModel(this);
	
    /** The pressed keys. */
    private final Set<Integer> pressedKeys = new HashSet<Integer>();
       

	/**
	 * Instantiates a new main controller.
	 */
	public MainController() {
		profilePanel = mainWindow.getProfilePanel();
		contactPanel = mainWindow.getContactPanel();
		tabbedPanel = mainWindow.getTabbedPanel();
		profilePanel.setActionListeners(this);
		contactPanel.setActionListeners(this);
		tabbedPanel.setActionListeners(this);
		
		String selfUserName = contactModel.getSelfUserName();
		String selfDisplayPicturePath = contactModel.getSelfDisplayPicturePath();	
		profilePanel.setUserName(selfUserName);
		profilePanel.setDisplayPicture(selfDisplayPicturePath);
		
		ArrayList<Contact> contacts = contactModel.getContacts();
		contactPanel.setContacts(connectionModel.getContactsOnlineStatus(contacts));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Send")) {
			processSendButton();
		}
		else if (e.getActionCommand().equals("SetUserName")) {
			processSetUserNameButton();
		}
		else if (e.getActionCommand().equals("SetDisplayPicture")) {
			processSetDisplayPictureButton();
		}
		else if (e.getActionCommand().equals("AddContact")) {
			processAddContactButton();
		}
		else if (e.getActionCommand().equals("DeleteContact")) {
			processDeleteContactButton();
		}
		else if (e.getActionCommand().equals("Connect")) {
			processConnectCommand();
		}
		else if (e.getActionCommand().equals("Disconnect")) {
			processDisconnectCommand();
		}
	}
	
	/**
	 * Process send button.
	 */
	private void processSendButton() {
		String sentText = tabbedPanel.getSentText();
		String userName = profilePanel.getUserName();
		String ownMessage = userName + ": " + sentText + "\n";
		String contactUserName = tabbedPanel.getContactUserName();
		Contact contact = contactModel.getContactWithUserName(contactUserName);
		connectionModel.sendMessage(ownMessage, contact.ipAddress);
		tabbedPanel.addTextToViewTextArea(ownMessage);
		tabbedPanel.clearSendTextArea();	
	}
	
	/**
	 * Process set user name button.
	 */
	private void processSetUserNameButton() {
		String newUserName = mainWindow.throwInputDialog("Type the new user name");
		if (newUserName == null || newUserName.equals("")) {
			return;
		}
		profilePanel.setUserName(newUserName);
		contactModel.setSelfUserName(newUserName);
	}

	/**
	 * Process set display picture button.
	 */
	private void processSetDisplayPictureButton() {
		File imageFile = mainWindow.openFileChooser();
		profilePanel.setDisplayPicture(imageFile);
		contactModel.setSelfDisplayPicture(imageFile);
	}
	

	/**
	 * Process add contact button.
	 */
	private void processAddContactButton() {
		String ipAddress = mainWindow.throwInputDialog("Type the IP address");
		if (ipAddress == null) {
			return;
		}
		if (!connectionModel.isClientResolved(ipAddress)) {
			throwMessage("The given IP address is not valid.");
			return;
		}
		if (contactModel.checkIfContactExists(ipAddress)) {
			throwMessage("There is already a contact with this IP address");
			return;
		}
		String userName = "Unknown";
		contactModel.addContact(userName, ipAddress);
		ArrayList<Contact> contacts = contactModel.getContacts();
		contactPanel.setContacts(connectionModel.getContactsOnlineStatus(contacts));
	}

	/**
	 * Process delete contact button.
	 */
	private void processDeleteContactButton() {
		String userName = mainWindow.throwInputDialog("Type the user name of the contact");
		if (userName == null) {
			return;
		}
		if (contactModel.getContactWithUserName(userName) == null) {
			throwMessage("There is no contact with this IP address");
			return;
		}		
		if (connectionModel.senderSocketExists(contactModel.getContactWithUserName(userName).ipAddress)) {
			throwMessage("Can't delete a connected contact.");
			return;
		}
		String ipAddress = contactModel.getContactWithUserName(userName).ipAddress;
		contactModel.deleteContact(ipAddress);
		connectionModel.removeSender(ipAddress);
		ArrayList<Contact> contacts = contactModel.getContacts();
		contactPanel.setContacts(connectionModel.getContactsOnlineStatus(contacts));
	}
	
	/**
	 * Process connect command.
	 */
	private void processConnectCommand() {
		String ipAddress = contactPanel.getMostRecentPopUpIP();
		if (connectionModel.senderSocketExists(ipAddress)) {
			throwMessage("Already connected with this contact");
			return;
		}
		connectionModel.connectClient(ipAddress);
	}

	/**
	 * Process disconnect command.
	 */
	private void processDisconnectCommand() {
		String ipAddress = contactPanel.getMostRecentPopUpIP();
		if (!connectionModel.senderSocketExists(ipAddress)) {
			throwMessage("Already disconnected with this contact");
			return;
		}
		connectionModel.disconnectClient(ipAddress);
		tabbedPanel.closeTab(contactModel.getContactWithIP(ipAddress).userName);
		ArrayList<Contact> contacts = contactModel.getContacts();
		contactPanel.setContacts(connectionModel.getContactsOnlineStatus(contacts));
		throwMessage("Connection closed with IP: " + ipAddress);
	}
	
	/**
	 * Connection attempted.
	 *
	 * @param success the success
	 * @param ipAddress the ip address
	 */
	public synchronized void connectionAttempted(boolean success, String ipAddress) {
		ArrayList<Contact> contacts = contactModel.getContacts();
		contactPanel.setContacts(connectionModel.getContactsOnlineStatus(contacts));
		if (success) {
			Contact contact = contactModel.getContactWithIP(ipAddress);
			tabbedPanel.addTab(contact.userName);
		}
		String alertMessage = success ? "Connection established with IP: " + ipAddress :
			"Connection failed with IP: " + ipAddress;
		throwMessage(alertMessage);
	}

	/**
	 * Receiver added.
	 *
	 * @param ipAddress the ip address
	 */
	public synchronized void receiverAdded(String ipAddress) {
		if (connectionModel.senderSocketExists(ipAddress)) {
			return;
		}
		String userName = "Unknown";
		if (!contactModel.checkIfContactExists(ipAddress)) {
			contactModel.addContact(userName, ipAddress);
		}
		connectionModel.connectClient(ipAddress);
	}
	
	/**
	 * Message recieved.
	 *
	 * @param ipAddress the ip address
	 * @param receivedMessage the received message
	 */
	public synchronized void messageRecieved(String ipAddress, String receivedMessage) {
		tabbedPanel.addTextToViewTextArea(receivedMessage);
		String receivedUserName = receivedMessage.substring(0, receivedMessage.indexOf(": "));
		if (!contactModel.compareContactUserName(ipAddress, receivedUserName)) {
			String userName = receivedUserName;
			String oldUserName = contactModel.getContactWithIP(ipAddress).userName;
			contactModel.setContactUserName(userName, ipAddress);
			ArrayList<Contact> contacts = contactModel.getContacts();
			contactPanel.setContacts(connectionModel.getContactsOnlineStatus(contacts));
			tabbedPanel.setUserName(userName, oldUserName);
		}
	}
	
	/**
	 * Connection closed.
	 *
	 * @param ipAddress the ip address
	 */
	public synchronized void connectionClosed(String ipAddress) {
		Contact contact = contactModel.getContactWithIP(ipAddress);
		if (contact == null) {
			return;
		}
		connectionModel.removeSender(ipAddress);
		ArrayList<Contact> contacts = contactModel.getContacts();
		contactPanel.setContacts(connectionModel.getContactsOnlineStatus(contacts));
		tabbedPanel.closeTab(contactModel.getContactWithIP(ipAddress).userName);
		throwMessage("Connection closed with IP: " + ipAddress);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
		// Only Enter key is pressed
		if (pressedKeys.size() == 1 && pressedKeys.contains(10)) {
			processSendButton();	
		}
		// Shift and Enter keys are pressed
		else if (pressedKeys.size() == 2 && pressedKeys.contains(10) && pressedKeys.contains(16)) {
			tabbedPanel.addTextToSendTextArea("\n");
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public synchronized void keyReleased(KeyEvent e) {
		if (pressedKeys.size() == 1 && pressedKeys.contains(10)) {
			tabbedPanel.clearSendTextArea();	
		}	
		pressedKeys.remove(e.getKeyCode());
	}
	
	/**
	 * Application closing.
	 */
	public void applicationClosing() {
		contactModel.writeXML();
	}
	
	/**
	 * Throw message.
	 *
	 * @param message the message
	 */
	public void throwMessage(String message) {
		mainWindow.throwMessageDialog(message);
	}
	
	// For Windows
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent event) {
        if (event.isPopupTrigger()) {
        	contactPanel.showPopUp(event);
        }		
	}
	
	// For Mac OS
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent event) {
        if (event.isPopupTrigger()) {
        	contactPanel.showPopUp(event);
        }		
	}
	
	/**
	 * Gets the connection model.
	 *
	 * @return the connection model
	 */
	public ConnectionModel getConnectionModel() {
		return connectionModel;
	}
	
	/**
	 * Gets the contact model.
	 *
	 * @return the contact model
	 */
	public ContactModel getContactModel() {
		return contactModel;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// Not used
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
