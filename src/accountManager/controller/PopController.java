package accountManager.controller;

import java.awt.Point;
import java.awt.Window;

import accountManager.view.AcctView;
import accountManager.view.CritWindow;
import accountManager.view.PopUpWindow;

/**
 * PopController class used to control PopUpWindow and CritWindow functionalities
 * @author Miguel Cazares
 *
 */
public class PopController {
	
	private PopUpWindow popUp;
	
	public void setPopUpWindow(PopUpWindow pWin){this.popUp = pWin;}
	public PopUpWindow getPopUpWindow(){return popUp;}
	
	/**
	 * Used to instantiate a new PopController which instantiates a new pop up window
	 * @param msg
	 * @param location (of pop up window)
	 */
	public PopController(String msg, Point location){
		setPopUpWindow(new PopUpWindow(msg, this, location));
	}
	
	/**
	 * Used when handling a critical exception that must exit the program
	 * @param msg - prints to window textfield
	 * @param location - of critical error window
	 * @param option
	 */
	public PopController(String msg, Point location, String option){
		if(option.equals(AcctListController.ERROR))
			new CritWindow(msg, this, location);
	}
	
	public void operation(String option){
		if(option.equals(AcctView.DISMISS)){
			getPopUpWindow().dispose();
		}
	}
	
	/**
	 * This method is called when a CritWindow "dismiss" is pressed, should exit all windows because of CriticalException
	 */
	public void exitSystem(){
		System.gc();
		for (Window window : Window.getWindows()) {
		    window.dispose();
		}
		System.exit(0);
	}
}
