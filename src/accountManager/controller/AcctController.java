package accountManager.controller;

import accountManager.model.AcctException;
import accountManager.model.AcctModel;
import accountManager.model.Model;
import accountManager.view.AcctView;

/**
 * AcctController is a subclass of AbstractController and is used to hold a controller for a single account.
 * @author Miguel Cazares
 *
 */
public class AcctController extends AbstractController{
	
	/**
	 * Constructor for AcctController
	 * @param model - the model to retrieve updates from
	 * @param aModel - the account model
	 * @param editType - specifies which kind of view to open up
	 */
	public AcctController(Model model, AcctModel aModel, String editType){
		this.setAcctModel(aModel);
		this.setAcctView(new AcctView(model, aModel, this, editType));
	}
	
	/**
	 * Method operation is used to handle user press of buttons in AcctView
	 * @param option - deposit, withdraw, or dismiss
	 * @param amount - amount entered in editable textfield
	 */
	public void operation(String option, String amount){
		try{
			if(option.equals(AcctView.DEPOSIT)){
				getAcctModel().deposit(amount);
			}
			else if(option.equals(AcctView.WITHDRAW)){
				getAcctModel().withdraw(amount);
			}
			else if(option.equals(AcctView.DISMISS)){
			//System.out.println("dismiss pressed");
				getAcctView().dispose();
			}
		}
		catch(AcctException exc){
			//System.out.println("caught acctException");
			new PopController(exc.getMessage(), getAcctView().getLoc());
		}
		
	}
}
