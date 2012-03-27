package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.MainController;

// TODO: Auto-generated Javadoc
/**
 * The Class TextStagePanel.
 */
public class TextStagePanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant TEXT_STAGE_PANEL_WIDTH. */
	private static final int TEXT_STAGE_PANEL_WIDTH = 420;
	
	/** The Constant TEXT_STAGE_PANEL_HEIGHT. */
	private static final int TEXT_STAGE_PANEL_HEIGHT = 415;
	
	/** The Constant VIEW_SCROLL_PANE_WIDTH. */
	private static final int VIEW_SCROLL_PANE_WIDTH = 410;

	/** The Constant VIEW_SCROLL_PANE_HEIGHT. */
	private static final int VIEW_SCROLL_PANE_HEIGHT = 300;

	/** The Constant BOTTOM_PANEL_WIDTH. */
	private static final int BOTTOM_PANEL_WIDTH = 450;

	/** The Constant BOTTOM_PANEL_HEIGHT. */
	private static final int BOTTOM_PANEL_HEIGHT = 50;

	/** The Constant SEND_SCROLL_PANE_WIDTH. */
	private static final int SEND_SCROLL_PANE_WIDTH = 350;

	/** The Constant SEND_SCROLL_PANE_HEIGHT. */
	private static final int SEND_SCROLL_PANE_HEIGHT = 50;
	
	/** The view scroll pane. */
	private JScrollPane viewScrollPane;
	
	/** The send scroll pane. */
	private JScrollPane sendScrollPane;
	
	/** The view text area. */
	private JTextArea viewTextArea;
	
	/** The send text area. */
	private JTextArea sendTextArea;
	
	/** The send button. */
	private JButton sendButton;
	
	/** The user name. */
	private String userName;
		
	
	/**
	 * Instantiates a new text stage panel.
	 *
	 * @param userName the user name
	 */
	public TextStagePanel(String userName) {
		this.userName = userName;
		setPreferredSize(new Dimension(TEXT_STAGE_PANEL_WIDTH, TEXT_STAGE_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(BOTTOM_PANEL_WIDTH, BOTTOM_PANEL_HEIGHT));
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(getSendScrollPane(), BorderLayout.WEST);
		bottomPanel.add(getSendButton(), BorderLayout.EAST);
	
		add(getViewScrollPane(), BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);	
	}

	/**
	 * Gets the view scroll pane.
	 *
	 * @return the view scroll pane
	 */
	private JScrollPane getViewScrollPane() {
		if (viewScrollPane == null) {
			viewScrollPane = new JScrollPane(getViewTextArea());
			viewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			viewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			viewScrollPane.setPreferredSize(new Dimension(VIEW_SCROLL_PANE_WIDTH, VIEW_SCROLL_PANE_HEIGHT));
		}
		return viewScrollPane;
	}
	
	/**
	 * Gets the send scroll pane.
	 *
	 * @return the send scroll pane
	 */
	private JScrollPane getSendScrollPane() {
		if (sendScrollPane == null) {
			sendScrollPane = new JScrollPane(getSendTextArea());
			sendScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			sendScrollPane.setPreferredSize(new Dimension(SEND_SCROLL_PANE_WIDTH, SEND_SCROLL_PANE_HEIGHT));
		}
		return sendScrollPane;
	}

	/**
	 * Gets the view text area.
	 *
	 * @return the view text area
	 */
	private JTextArea getViewTextArea() {
		if (viewTextArea == null) {
			viewTextArea = new JTextArea();
			viewTextArea.setMinimumSize(new Dimension(VIEW_SCROLL_PANE_WIDTH, VIEW_SCROLL_PANE_HEIGHT));
			viewTextArea.setEditable(false);
		}
		return viewTextArea;
	}


	/**
	 * Gets the send text area.
	 *
	 * @return the send text area
	 */
	private JTextArea getSendTextArea() {
		if (sendTextArea == null) {
			sendTextArea = new JTextArea();
			sendTextArea.setMinimumSize(new Dimension(SEND_SCROLL_PANE_WIDTH, SEND_SCROLL_PANE_HEIGHT));	
		}
		return sendTextArea;
	}


	/**
	 * Gets the send button.
	 *
	 * @return the send button
	 */
	private JButton getSendButton() {
		if (sendButton == null) {
			sendButton = new JButton("Send");
			sendButton.setActionCommand("Send");
		}
		return sendButton;
	}
	
	/**
	 * Sets the action listeners.
	 *
	 * @param controller the new action listeners
	 */
	public void setActionListeners(MainController controller) {
		sendButton.addActionListener(controller);
		sendTextArea.addKeyListener(controller);
	}
	
	/**
	 * Gets the sent text.
	 *
	 * @return the sent text
	 */
	public String getSentText() {
		return sendTextArea.getText();
	}

	/**
	 * Adds the text to view text area.
	 *
	 * @param newText the new text
	 */
	public void addTextToViewTextArea(String newText) {
		viewTextArea.setText(viewTextArea.getText() + newText);
		viewTextArea.repaint();
	}

	/**
	 * Adds the text to send text area.
	 *
	 * @param newText the new text
	 */
	public void addTextToSendTextArea(String newText) {
		sendTextArea.setText(sendTextArea.getText() + newText);
		sendTextArea.repaint();	
	}
	
	/**
	 * Clear send text area.
	 */
	public void clearSendTextArea() {
		sendTextArea.setText("");
		sendTextArea.repaint();
	}
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
