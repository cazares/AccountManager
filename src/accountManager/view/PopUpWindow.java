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
 * This class deals with displaying pop up windows (used when catching acct exceptions)
 * @author Miguel Cazares
 *
 */
public class PopUpWindow{
	private JFrame frame;
	private PopController popController;
	
	public void dispose(){frame.dispose();}
	public void setController(PopController pController){this.popController = pController;}
	public PopController getController(){return popController;}
	
	public PopUpWindow(String msg, PopController pController, Point location){
		setController(pController);
		popUp(msg, location);
	}
	
	public void popUp(String msg, Point location){
		frame = new JFrame("Error Message");
		JTextField errMsg = new JTextField(msg);
		errMsg.setEditable(false);
		JPanel errPanel = new JPanel();
		JButton dismissButton = new JButton (AcctView.DISMISS);
		
		errPanel.setLayout(new GridLayout(4, 4, 5, 5));
		
		errPanel.add(errMsg, null);
		errPanel.add(dismissButton, null);
		dismissButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event){
    			getController().operation(AcctView.DISMISS);
    		}
    	});
		
		frame.getContentPane().add(errPanel, BorderLayout.CENTER);
		frame.setLocation(location);
		frame.pack();
		frame.setVisible(true);
	}
}
