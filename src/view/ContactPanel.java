package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.contact.Contact;
import controller.MainController;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactPanel.
 */
public class ContactPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant SCROLL_PANE_WIDTH. */
	private static final int SCROLL_PANE_WIDTH = 240;

	/** The Constant SCROLL_PANE_HEIGHT. */
	private static final int SCROLL_PANE_HEIGHT = 360;

	/** The Constant CONTACT_PANEL_WIDTH. */
	private static final int CONTACT_PANEL_WIDTH = 250;

	/** The Constant CONTACT_PANEL_HEIGHT. */
	private static final int CONTACT_PANEL_HEIGHT = 425;
	
	/** The Constant CONTACT_BUTTONS_PANEL_WIDTH. */
	private static final int CONTACT_BUTTONS_PANEL_WIDTH = 240;

	/** The Constant CONTACT_BUTTONS_PANEL_HEIGHT. */
	private static final int CONTACT_BUTTONS_PANEL_HEIGHT = 25;
	
	/** The scroll pane. */
	private JScrollPane scrollPane;
	
	/** The contact table model. */
	private DefaultTableModel contactTableModel;
	
	/** The contact table. */
	private JTable contactTable;
	
	/** The add contact button. */
	private JButton addContactButton;
	
	/** The delete contact button. */
	private JButton deleteContactButton;
	
	/** The popup menu. */
	private JPopupMenu popupMenu;
	
	/** The menu item connect. */
	private JMenuItem menuItemConnect;
	
	/** The menu item disconnect. */
	private JMenuItem menuItemDisconnect;
	
	/** The most recent pop up ip. */
	private String mostRecentPopUpIP;
	
	/** The column names. */
	private Object[] columnNames;
	
	/** The row data. */
	private Object[][] rowData;
	
	
	/**
	 * Instantiates a new contact panel.
	 */
	public ContactPanel() {
		setPreferredSize(new Dimension(CONTACT_PANEL_WIDTH, CONTACT_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		
		JPanel contactButtonPanel = new JPanel();
		contactButtonPanel.setPreferredSize(new Dimension(CONTACT_BUTTONS_PANEL_WIDTH, CONTACT_BUTTONS_PANEL_HEIGHT));
		contactButtonPanel.setLayout(new GridLayout(0,2));	
		contactButtonPanel.add(getAddContactButton());
		contactButtonPanel.add(getDeleteContactButton());	
		
		add(contactButtonPanel, BorderLayout.NORTH);
		add(getScrollPane(), BorderLayout.SOUTH);	
		
		setBorder(new EmptyBorder(10, 10, 10, 0));
	}
	

	/**
	 * Gets the scroll pane.
	 *
	 * @return the scroll pane
	 */
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getContactTable());
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setPreferredSize(new Dimension(SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT));
		}
		return scrollPane;
	}


	/**
	 * Gets the contact table.
	 *
	 * @return the contact table
	 */
	@SuppressWarnings("serial")
	private JTable getContactTable() {
		if (contactTable == null) {
			contactTableModel = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			contactTable = new JTable(contactTableModel) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int column)
	            {
	                return getValueAt(0, column).getClass();
	            }
				
				public String getToolTipText(MouseEvent event) {
					Point point = event.getPoint();
	                int rowIndex = rowAtPoint(point);
	                return "IP Address: "+((Contact)getValueAt(rowIndex, 1)).ipAddress;
				};

			};
			contactTable.setShowHorizontalLines(false);
			contactTable.setShowVerticalLines(false);	
			contactTable.setRowHeight(55);
			
			columnNames = new Object[3];
			columnNames[0] = "";
			columnNames[1] = "User Name";
			columnNames[2] = "Status";
			
			popupMenu = new JPopupMenu();
			menuItemConnect = new JMenuItem("Connect");
			menuItemConnect.setActionCommand("Connect");
			popupMenu.add(menuItemConnect);
			menuItemDisconnect = new JMenuItem("Disconnect");
			menuItemDisconnect.setActionCommand("Disconnect");
			popupMenu.add(menuItemDisconnect);
		}
		return contactTable;
	}
	
	
	/**
	 * Gets the adds the contact button.
	 *
	 * @return the adds the contact button
	 */
	private JButton getAddContactButton() {
		if (addContactButton == null) {
			addContactButton = new JButton("Add Contact");
			addContactButton.setActionCommand("AddContact");
		}
		return addContactButton;
	}

	/**
	 * Gets the delete contact button.
	 *
	 * @return the delete contact button
	 */
	private JButton getDeleteContactButton() {
		if (deleteContactButton == null) {
			deleteContactButton = new JButton("Delete Contact");
			deleteContactButton.setActionCommand("DeleteContact");
		}
		return deleteContactButton;
	}
	
	/**
	 * Sets the action listeners.
	 *
	 * @param controller the new action listeners
	 */
	public void setActionListeners(MainController controller) {
		addContactButton.addActionListener(controller);
		deleteContactButton.addActionListener(controller);
		contactTable.addMouseListener(controller);
		menuItemConnect.addActionListener(controller);
		menuItemDisconnect.addActionListener(controller);
	}


	/**
	 * Sets the contacts.
	 *
	 * @param onlineContactMap the online contact map
	 */
	public synchronized void setContacts(HashMap<Contact, Boolean> onlineContactMap) {
		Set<Contact> contacts = onlineContactMap.keySet();
		rowData = new Object[contacts.size()][3];
		if (contacts.size() == 0) {
			contactTableModel.setDataVector(rowData, columnNames);
			contactTable.repaint();
			return;
		}
		int contactIndex = 0;
		for (Contact contact : contacts) {
			String onlineStatus = onlineContactMap.get(contact) == true ? "(Online)" : "(Offline)";
			ImageIcon contactDisplayPicture = new ImageIcon("images/default_display_picture.png");
			rowData[contactIndex][0] = contactDisplayPicture;
			rowData[contactIndex][1] = contact;
			rowData[contactIndex][2] = onlineStatus;
			contactIndex++;
		}	
		SwingUtilities.invokeLater(new Runnable() {		
			public void run() {
				contactTableModel.setDataVector(rowData, columnNames);
				contactTable.repaint();
			}	
		});
	}


	/**
	 * Show pop up.
	 *
	 * @param event the event
	 */
	public void showPopUp(MouseEvent event) {
        JTable table = (JTable)event.getSource();
        int row = table.rowAtPoint(event.getPoint());
        int column = table.columnAtPoint(event.getPoint());
        if (!table.isRowSelected(row)) {
            table.changeSelection(row, column, false, false);
        }
        popupMenu.show(event.getComponent(), event.getX(), event.getY());	
        mostRecentPopUpIP = ((Contact)table.getValueAt(row, 1)).ipAddress;
	}


	/**
	 * Gets the most recent pop up ip.
	 *
	 * @return the most recent pop up ip
	 */
	public String getMostRecentPopUpIP() {
		return mostRecentPopUpIP;
	}
}
