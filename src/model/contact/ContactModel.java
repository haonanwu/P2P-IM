package model.contact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactModel.
 */
public class ContactModel {
	
	/** The XM l_ fil e_ path. */
	private String XML_FILE_PATH = "xml/contacts.xml";
	
	/** The contacts. */
	private ArrayList<Contact> contacts;
	
	/** The self user name. */
	private String selfUserName;
	
	/** The self display picture path. */
	private String selfDisplayPicturePath;
	
	
	/**
	 * Instantiates a new contact model.
	 */
	public ContactModel() {
		parseXML();
	}
	
	/**
	 * Parses the xml.
	 */
	@SuppressWarnings("rawtypes")
	public void parseXML() {
		contacts = new ArrayList<Contact>();
		SAXReader saxReader = new SAXReader();
		File xmlFile = new File(XML_FILE_PATH);
		try {
			Document xmlDocument = saxReader.read(xmlFile);
			Node node = xmlDocument.selectSingleNode("//UserName");
			selfUserName = node.getText();
			node = xmlDocument.selectSingleNode("//DisplayPicturePath");
			selfDisplayPicturePath = node.getText();
			List list = xmlDocument.selectNodes("//Contact");
			Iterator contactIterator = list.iterator();
			while (contactIterator.hasNext()) {
				Element contactElement = (Element)contactIterator.next();
				Element userNameElement = contactElement.element("UserName");
				String userName = userNameElement.getText();
				Element ipAddressElement = contactElement.element("IpAddress");
				String ipAddress = ipAddressElement.getText();
				Contact contact = new Contact(userName, ipAddress);
				contacts.add(contact);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Write xml.
	 */
	public void writeXML() {
		Document xmlDocument = DocumentFactory.getInstance().createDocument();
		Element rootElement = xmlDocument.addElement("ContactList");
		Element selfElement = rootElement.addElement("Self");
		Element selfUserNameElement = selfElement.addElement("UserName");
		selfUserNameElement.addText(selfUserName);
		Element selfDisplayPicturePathElement = selfElement.addElement("DisplayPicturePath");
		selfDisplayPicturePathElement.addText(selfDisplayPicturePath);
		Element contactsElement = rootElement.addElement("Contacts");
		for (Contact contact : contacts) {
			Element contactElement = contactsElement.addElement("Contact");
			Element userNameElement = contactElement.addElement("UserName");
			userNameElement.addText(contact.userName);
			Element ipAddressElement = contactElement.addElement("IpAddress");
			ipAddressElement.addText(contact.ipAddress);
		}
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(XML_FILE_PATH);
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(fileOutputStream, format);
			writer.write(xmlDocument);
			writer.flush();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public ArrayList<Contact> getContacts() {
		return contacts;
	}
	
	/**
	 * Gets the self user name.
	 *
	 * @return the self user name
	 */
	public String getSelfUserName() {
		return selfUserName;
	}
	
	/**
	 * Gets the self display picture path.
	 *
	 * @return the self display picture path
	 */
	public String getSelfDisplayPicturePath() {
		return selfDisplayPicturePath;
	}

	/**
	 * Gets the contact with ip.
	 *
	 * @param ipAddress the ip address
	 * @return the contact with ip
	 */
	public Contact getContactWithIP(String ipAddress) {
		for (Contact contact : contacts) {
			if (contact.ipAddress.equals(ipAddress)) {
				return contact;
			}
		}
		return null;
	}
	
	/**
	 * Gets the contact with user name.
	 *
	 * @param userName the user name
	 * @return the contact with user name
	 */
	public Contact getContactWithUserName(String userName) {
		for (Contact contact : contacts) {
			if (contact.userName.equals(userName)) {
				return contact;
			}
		}
		return null;
	}
	
	/**
	 * Check if contact exists.
	 *
	 * @param ipAddress the ip address
	 * @return true, if successful
	 */
	public boolean checkIfContactExists(String ipAddress) {
		for (Contact contact : contacts) {
			if (contact.ipAddress.equals(ipAddress)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds the contact.
	 *
	 * @param userName the user name
	 * @param ipAddress the ip address
	 */
	public void addContact(String userName, String ipAddress) {
		Contact contact = new Contact(userName, ipAddress);
		contacts.add(contact);
	}

	/**
	 * Compare contact user name.
	 *
	 * @param ipAddress the ip address
	 * @param userNameToCompare the user name to compare
	 * @return true, if successful
	 */
	public boolean compareContactUserName(String ipAddress, String userNameToCompare) {
		for (Contact contact : contacts) {
			if (contact.ipAddress.equals(ipAddress) && contact.userName.equals(userNameToCompare)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the contact user name.
	 *
	 * @param userName the user name
	 * @param ipAddress the ip address
	 */
	public void setContactUserName(String userName, String ipAddress) {
		for (Contact contact : contacts) {
			if (contact.ipAddress.equals(ipAddress)) {
				contact.userName = userName;
			}
		}		
	}

	/**
	 * Delete contact.
	 *
	 * @param ipAddress the ip address
	 */
	public void deleteContact(String ipAddress) {
		for (Contact contact : contacts) {
			if (contact.ipAddress.equals(ipAddress)) {
				contacts.remove(contact);
				break;
			}
		}
		
	}

	/**
	 * Sets the self user name.
	 *
	 * @param newUserName the new self user name
	 */
	public void setSelfUserName(String newUserName) {
		selfUserName = newUserName;
	}

	/**
	 * Sets the self display picture.
	 *
	 * @param imageFile the new self display picture
	 */
	public void setSelfDisplayPicture(File imageFile) {
		selfDisplayPicturePath = imageFile.getAbsolutePath();
	}
	
	/**
	 * Sets the xML file path.
	 *
	 * @param filePath the new xML file path
	 */
	public void setXMLFilePath(String filePath) {
		XML_FILE_PATH = filePath;
	}
}
