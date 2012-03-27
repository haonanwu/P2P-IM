package view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controller.MainController;

// TODO: Auto-generated Javadoc
/**
 * The Class TabbedPanel.
 */
public class TabbedPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant VIEW_SCROLL_PANE_WIDTH. */
	private static final int TABBED_PANEL_WIDTH = 450;

	/** The Constant VIEW_SCROLL_PANE_HEIGHT. */
	private static final int TABBED_PANEL_HEIGHT = 405;
	
	/** The tabbed pane. */
	private JTabbedPane tabbedPane;
		
	/** The controller. */
	private MainController controller;
	
	/**
	 * Instantiates a new tabbed panel.
	 */
	public TabbedPanel() {
		setPreferredSize(new Dimension(TABBED_PANEL_WIDTH, TABBED_PANEL_HEIGHT));
		add(getTabbedPane());
		setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	/**
	 * Gets the tabbed pane.
	 *
	 * @return the tabbed pane
	 */
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.setPreferredSize(new Dimension(TABBED_PANEL_WIDTH, TABBED_PANEL_HEIGHT));
		}
		return tabbedPane;
	}
	
	/**
	 * Adds the tab.
	 *
	 * @param userName the user name
	 */
	public void addTab(String userName) {
		TextStagePanel textStagePanel = new TextStagePanel(userName);
		textStagePanel.setActionListeners(controller);
		tabbedPane.addTab(userName, textStagePanel);
	}
	
	/**
	 * Gets the sent text.
	 *
	 * @return the sent text
	 */
	public String getSentText() {
		return ((TextStagePanel)tabbedPane.getSelectedComponent()).getSentText();
	}
	
	/**
	 * Adds the text to view text area.
	 *
	 * @param newText the new text
	 */
	public void addTextToViewTextArea(String newText) {
		((TextStagePanel)tabbedPane.getSelectedComponent()).addTextToViewTextArea(newText);
	}

	/**
	 * Adds the text to send text area.
	 *
	 * @param newText the new text
	 */
	public void addTextToSendTextArea(String newText) {
		((TextStagePanel)tabbedPane.getSelectedComponent()).addTextToSendTextArea(newText);	
	}
	
	/**
	 * Clear send text area.
	 */
	public void clearSendTextArea() {
		((TextStagePanel)tabbedPane.getSelectedComponent()).clearSendTextArea();
	}

	/**
	 * Sets the action listeners.
	 *
	 * @param controller the new action listeners
	 */
	public void setActionListeners(MainController controller) {
		this.controller = controller;
	}

	/**
	 * Gets the contact user name.
	 *
	 * @return the contact user name
	 */
	public String getContactUserName() {
		return ((TextStagePanel)tabbedPane.getSelectedComponent()).getUserName();
	}

	/**
	 * Close tab.
	 *
	 * @param userName the user name
	 */
	public void closeTab(String userName) {
		Component[] textStagePanels = tabbedPane.getComponents();
		for (int i=0; i<textStagePanels.length; i++) {
			if (((TextStagePanel) textStagePanels[i]).getUserName().equals(userName)) {
				tabbedPane.remove(i);
				tabbedPane.repaint();
				return;
			}
		}
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the user name
	 * @param oldUserName the old user name
	 */
	public void setUserName(String userName, String oldUserName) {
		Component[] textStagePanels = tabbedPane.getComponents();
		int index = 0;
		for (int i=0; i<textStagePanels.length; i++) {
			if (((TextStagePanel) textStagePanels[i]).getUserName().equals(oldUserName)) {
				index = i;
				((TextStagePanel) textStagePanels[i]).setUserName(userName);
				break;
			}
		}
		tabbedPane.setTitleAt(index, userName);
		tabbedPane.repaint();
	}
}

