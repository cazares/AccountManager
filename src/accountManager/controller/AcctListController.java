package accountManager.controller;

import accountManager.model.AcctException;
import accountManager.model.AcctListModel;
import accountManager.model.CriticalException;
import accountManager.view.AcctListView;

import java.awt.Point;
import java.awt.Window;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class holds functionality for the account list (e.g. dropDownMenu)
 * @author Miguel Cazares
 *
 */
public class AcctListController extends AbstractController{
	public static final String ERROR = "Error";
	public static final String DEPOSIT = "deposit";
	public static final String WITHDRAW = "withdraw";
	
	/**
	 * AcctListController constructor takes a String (inputFile) as argument and creates model and view for this account list.
	 * @param inputFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public AcctListController(String inputFile) throws FileNotFoundException, IOException{
		try{
			this.setModel(new AcctListModel(inputFile));
		}
		catch(AcctException exc){
			//this.setView(new AcctListView((AcctListModel)getModel(), this));
			new PopController(exc.getMessage(), new Point(0,0));
		}
		catch(CriticalException exc){
			new PopController(exc.getMessage(), new Point(0,0), ERROR);
			return;
		}
		this.setView(new AcctListView((AcctListModel)getModel(), this));
	}
	
	/**
	 * Operation is called when user presses a button in main "select account to edit" window
	 * @param option - contents of button user pressed
	 * @param selIndex - specifies index of selected account from JComboBox
	 */
	public void operation(String option, int selIndex){
		if(option.equals(AcctListView.YEN)){
			new AcctController(this.getModel(), ((AcctListModel)getModel()).getAccount(selIndex), option);
		}
		else if(option.equals(AcctListView.DOL)){
			new AcctController(this.getModel(), ((AcctListModel)getModel()).getAccount(selIndex), option);
		}
		else if(option.equals(AcctListView.EUR)){
			new AcctController(this.getModel(), ((AcctListModel)getModel()).getAccount(selIndex), option);
		}
		else if(option.equals(AcctListView.DEP_AGENT)){
			new AcctAgentController(this.getModel(), ((AcctListModel)getModel()).getAccount(selIndex), DEPOSIT);
		}
		else if(option.equals(AcctListView.WIT_AGENT)){
			new AcctAgentController(this.getModel(), ((AcctListModel)getModel()).getAccount(selIndex), WITHDRAW);
		}
		else if(option.equals(AcctListView.EXIT)){
			//EXIT, closes all open windows, then exits system
			if(acctsModified()){
				System.out.println("An account modified since last save, saving model before exiting");
				((AcctListModel)getModel()).saveModel();
			}
			else{
				System.out.println("No accounts modified since last save, not saving model, just exiting...");
			}
			System.gc();
			for (Window window : Window.getWindows()) {
			    window.dispose();
			}
			System.exit(0);
		}
		else if(option.equals(AcctListView.SAVE)){
			//SAVE
			setAcctsToSaved();
			((AcctListModel)getModel()).saveModel();
		}
		else{
			// NOTE: this code SHOULD NOT be reached
			System.err.println("Warning: a button was pressed that is not recognized!");
		}
	}
	
	/**
	 * Private helper method to determine if any account has been modified since last save
	 * @return boolean
	 */
	private boolean acctsModified(){
		for(int i=0; i < acctLModel().getNumAccts(); i++){
			if(acctLModel().getAccount(i).isModified())
				return true;
		}
		
		// no accounts have been modified, so return false
		return false;
	}
	
	private AcctListModel acctLModel(){
		return (AcctListModel)getModel();
	}
	
	/**
	 * Private helper method that sets all accounts to saved state
	 */
	private void setAcctsToSaved(){
		for(int i=0; i < acctLModel().getNumAccts(); i++){
			acctLModel().getAccount(i).setSaved();
		}
	}
}
