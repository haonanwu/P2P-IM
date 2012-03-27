package model.contact;

// TODO: Auto-generated Javadoc
/**
 * The Class Contact.
 */
public class Contact {
	
	/** The user name. */
	public String userName;
	
	/** The ip address. */
	public String ipAddress;
			
	/**
	 * Instantiates a new contact.
	 *
	 * @param userName the user name
	 * @param ipAddress the ip address
	 */
	public Contact(String userName, String ipAddress) {
		this.userName = userName;
		this.ipAddress = ipAddress;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Contact) {
			Contact otherContact = (Contact)obj;
			if (userName.equals(otherContact.userName) && ipAddress.equals(otherContact.ipAddress) &&
					this.hashCode() == otherContact.hashCode()) {
				return true;
			}	
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return userName;
	}

}
