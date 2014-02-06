package accountManager.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import accountManager.controller.PopController;

/**
 * This is a critical pop up window class, used for critical exception display that will exit upon dismissal
 * @author aguina
 *
 */
public class CritWindow{
	
	private JFrame frame;
	private PopController popController;
	
	public void dispose(){frame.dispose();}
	public void setController(PopController pController){this.popController = pController;}
	public PopController getController(){return popController;}
	public CritWindow(String msg, PopController pController, Point location){
		setController(pController);
		critWindow(msg, location);
	}
	
	/**
	 * Note that a critWindow will exit the system upon pressing button "dismiss"
	 * @param msg
	 * @param location
	 */
	public void critWindow(String msg, Point location){
		frame = new JFrame("Critical Error Message");
		JTextField errMsg = new JTextField(msg);
		errMsg.setEditable(false);
		JPanel errPanel = new JPanel();
		JButton dismissButton = new JButton (AcctView.DISMISS);
		
		errPanel.setLayout(new GridLayout(4, 4, 5, 5));
		
		errPanel.add(errMsg, null);
		errPanel.add(dismissButton, null);
		dismissButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event){
    			getController().exitSystem();
    		}
    	});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(errPanel, BorderLayout.CENTER);
		frame.setLocation(location);
		frame.pack();
		frame.setVisible(true);
	}
}

