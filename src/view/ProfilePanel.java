package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.MainController;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfilePanel.
 */
public class ProfilePanel extends JPanel {
	
	/** The Constant DISPLAY_PICTURE_WIDTH. */
	private static final int DISPLAY_PICTURE_WIDTH = 50;

	/** The Constant DISPLAY_PICTURE_HEIGHT. */
	private static final int DISPLAY_PICTURE_HEIGHT = 50;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant PROFILE_PANEL_WIDTH. */
	private static final int PROFILE_PANEL_WIDTH = 700;

	/** The Constant PROFILE_PANEL_HEIGHT. */
	private static final int PROFILE_PANEL_HEIGHT = 60;

	/** The Constant DEFAULT_DISPLAY_PICTURE_PATH. */
	private static final String DEFAULT_DISPLAY_PICTURE_PATH = "images/default_display_picture.png";
	
	/** The main window. */
	private MainWindow mainWindow;
	
	/** The display picture label. */
	private JLabel displayPictureLabel;
	
	/** The user name label. */
	private JLabel userNameLabel;
	
	/** The set user name button. */
	private JButton setUserNameButton;
	
	/** The set display picture button. */
	private JButton setDisplayPictureButton;
	
	/** The display picture. */
	private ImageIcon displayPicture;
	
		
	
	/**
	 * Instantiates a new profile panel.
	 *
	 * @param mainWindow the main window
	 */
	public ProfilePanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setPreferredSize(new Dimension(PROFILE_PANEL_WIDTH, PROFILE_PANEL_HEIGHT));
		setLayout(new BorderLayout());	
		
		JPanel leftPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		leftPanel.setLayout(new FlowLayout());
		leftPanel.add(getDisplayPictureLabel());
		leftPanel.add(getUserNameLabel());
		centerPanel.setPreferredSize(new Dimension(400, 60));
		rightPanel.add(getSetUserNameButton());
		rightPanel.add(getSetDisplayPictureButton());
			
		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}

	/**
	 * Gets the display picture label.
	 *
	 * @return the display picture label
	 */
	private JLabel getDisplayPictureLabel() {
		if (displayPictureLabel == null) {
			displayPicture = new ImageIcon(DEFAULT_DISPLAY_PICTURE_PATH);
			displayPictureLabel = new JLabel(displayPicture);
			displayPictureLabel.setBorder(BorderFactory.
					createTitledBorder(BorderFactory.createLineBorder(Color.BLACK)));
			displayPictureLabel.setPreferredSize(new Dimension(50, 50));
			displayPictureLabel.setVerticalAlignment(SwingConstants.CENTER);
		}
		return displayPictureLabel;
	}

	/**
	 * Gets the user name label.
	 *
	 * @return the user name label
	 */
	private JLabel getUserNameLabel() {
		if (userNameLabel == null) {
			userNameLabel = new JLabel("User Name");
			userNameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			userNameLabel.setVerticalAlignment(SwingConstants.CENTER);
		}
		return userNameLabel;
	}

	/**
	 * Gets the sets the user name button.
	 *
	 * @return the sets the user name button
	 */
	private JButton getSetUserNameButton() {
		if (setUserNameButton == null) {
			setUserNameButton = new JButton("Set User Name");
			setUserNameButton.setVerticalAlignment(SwingConstants.CENTER);
			setUserNameButton.setActionCommand("SetUserName");
		}
		return setUserNameButton;
	}


	/**
	 * Gets the sets the display picture button.
	 *
	 * @return the sets the display picture button
	 */
	private JButton getSetDisplayPictureButton() {
		if (setDisplayPictureButton == null) {
			setDisplayPictureButton = new JButton("Set Display Picture");
			setDisplayPictureButton.setVerticalAlignment(SwingConstants.CENTER);
			setDisplayPictureButton.setActionCommand("SetDisplayPicture");
		}
		return setDisplayPictureButton;
	}
	
	/**
	 * Sets the action listeners.
	 *
	 * @param controller the new action listeners
	 */
	public void setActionListeners(MainController controller) {
		setUserNameButton.addActionListener(controller);
		setDisplayPictureButton.addActionListener(controller);
	}
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userNameLabel.getText();
	}
	
	/**
	 * Sets the user name.
	 *
	 * @param newUserName the new user name
	 */
	public void setUserName(String newUserName) {
		userNameLabel.setText(newUserName);
		userNameLabel.repaint();
	}

	/**
	 * Sets the display picture.
	 *
	 * @param imageFile the new display picture
	 */
	public void setDisplayPicture(File imageFile) {
		String fileName = imageFile.getName();
		if (imageFile.getName().equals("")) {
			return;
		}
		
		int jpgExtensionIndex =  fileName.indexOf(".jpg");
		int bmpExtensionIndex =  fileName.indexOf(".bmp");
		int pngExtensionIndex =  fileName.indexOf(".png");
		
		if (!(jpgExtensionIndex > 0 && jpgExtensionIndex == fileName.length()-4) &&
			!(bmpExtensionIndex > 0 && bmpExtensionIndex == fileName.length()-4) &&
			!(pngExtensionIndex > 0 && pngExtensionIndex == fileName.length()-4)) {
			mainWindow.throwMessageDialog("The display picture must be in jpg, bmp or png format");
			return;
		}
		
		ImageIcon newDisplayPicture = new ImageIcon(imageFile.getAbsolutePath());
		
		if (newDisplayPicture.getIconWidth() != DISPLAY_PICTURE_WIDTH || 
			newDisplayPicture.getIconHeight() != DISPLAY_PICTURE_HEIGHT) {
			mainWindow.throwMessageDialog("The display picture must be 50x50 pixels");
			return;
		}
		displayPicture = newDisplayPicture;
		displayPictureLabel.setIcon(displayPicture);
		displayPictureLabel.repaint();	
	}

	/**
	 * Sets the display picture.
	 *
	 * @param displayPicturePath the new display picture
	 */
	public void setDisplayPicture(String displayPicturePath) {
		displayPicture = new ImageIcon(displayPicturePath);
		displayPictureLabel.setIcon(displayPicture);
		displayPictureLabel.repaint();
	}
	
}
