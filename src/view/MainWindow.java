package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.MainController;

// TODO: Auto-generated Javadoc
/**
 * The Class MainWindow.
 */
public class MainWindow extends JFrame implements WindowListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant MAIN_WINDOW_WIDTH. */
	private static final int MAIN_WINDOW_WIDTH = 700;

	/** The Constant MAIN_WINDOW_HEIGHT. */
	private static final int MAIN_WINDOW_HEIGHT = 500;
	
	/** The content pane. */
	private JPanel contentPane = new JPanel();
	
	/** The profile panel. */
	private ProfilePanel profilePanel;
	
	/** The contact panel. */
	private ContactPanel contactPanel;

	/** The text stage panel. */
	private TabbedPanel tabbedPanel;
	
	/** The file chooser. */
	private JFileChooser fileChooser = new JFileChooser();
	
	/** The controller. */
	private MainController controller;

	
	/**
	 * Instantiates a new main window.
	 *
	 * @param controller the controller
	 */
	public MainWindow(MainController controller) {
		this.controller = controller;
		setVisible(true);
		setPreferredSize(new Dimension(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));		
		int screenResolutionX = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenResolutionY = Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((screenResolutionX-MAIN_WINDOW_WIDTH)/2, 
					(screenResolutionY-MAIN_WINDOW_HEIGHT)/3);
		setTitle("Mert Guldur's Instant Messaging Application");
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		buildContentPane();
		fileChooser.setCurrentDirectory(new File("images"));
		addWindowListener(this);
		pack();
	}

	/**
	 * Builds the content pane.
	 */
	private void buildContentPane() {		
		contentPane.setLayout(new BorderLayout());	
		contentPane.add(getProfilePanel(), BorderLayout.NORTH);
		contentPane.add(getContactPanel(), BorderLayout.WEST);	
		contentPane.add(getTabbedPanel(), BorderLayout.EAST);			
		setContentPane(contentPane);
	}
 
	/**
	 * Gets the profile panel.
	 *
	 * @return the profile panel
	 */
	public ProfilePanel getProfilePanel() {
		if (profilePanel == null) {
			profilePanel = new ProfilePanel(this);
		}
		return profilePanel;
	}

	/**
	 * Gets the contact panel.
	 *
	 * @return the contact panel
	 */
	public ContactPanel getContactPanel() {
		if (contactPanel == null) {
			contactPanel = new ContactPanel();
		}
		return contactPanel;
	}

	/**
	 * Gets the text stage panel.
	 *
	 * @return the text stage panel
	 */
	public TabbedPanel getTabbedPanel() {
		if (tabbedPanel == null) {
			tabbedPanel = new TabbedPanel();
		}
		return tabbedPanel;
	}
	
	/**
	 * Open file chooser.
	 *
	 * @return the file
	 */
	public File openFileChooser() {
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		else {
			return new File("");
		}
	}
	
	/**
	 * Throw user name dialog.
	 *
	 * @param inputMessage the input message
	 * @return the string
	 */
	public String throwInputDialog(String inputMessage) {
		return JOptionPane.showInputDialog(inputMessage);
	}

	/**
	 * Throw error dialog.
	 *
	 * @param message the message
	 */
	public void throwMessageDialog(String message) {
		JOptionPane.showMessageDialog(this, message);		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		controller.applicationClosing();
		System.exit(0);
	}
	

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
