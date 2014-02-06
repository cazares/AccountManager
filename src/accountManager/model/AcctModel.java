package accountManager.model;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import accountManager.model.AcctException;
import accountManager.view.AcctView;

/**
 * This class holds information and methods for a single account (e.g. name, id, balance)
 * @author Miguel Cazares
 *
 */
public class AcctModel extends AbstractModel{
	private String name;
	private String id;
	private double bal;
	
	private final double EUR_RT = 0.79;
	private final double YEN_RT = 94.1;
	private boolean modified = false;
	
	public void setSaved(){modified = false;}
	public void setModified(){ modified = true; }
	public boolean isModified(){ return modified; }
	
	public String getName(){return this.name;};
	public String getID(){return this.id;};
	
	/**
	 * Returns the balance of the account in the correct currency (type) specified
	 * @param type
	 * @return double
	 */
	public double getBal(String type){
		if(type == AcctView.DOL)
			return this.bal;
		else if(type == AcctView.EUR)
			return this.bal * EUR_RT;
		else if(type == AcctView.YEN)
			return this.bal * YEN_RT;
		else
			return 0.0;
	}
	
	/**
	 * Instantiates an AcctModel, with balance rounded to two decimal places
	 * @param a_name
	 * @param a_id
	 * @param a_bal
	 */
	public AcctModel(String a_name, String a_id, double a_bal){
		this.name = a_name;
		this.id = a_id;
		this.bal = roundTwoDecimals(a_bal);
	}
	
	/**
	 * This method actually changes state (data) of the account by depositing money into it
	 * Throws account exception when user enters negative number or wrong-format number
	 * @param depStr
	 * @throws AcctException
	 */
	public void deposit(String depStr) throws AcctException{
		double dep_amt;
		ModelEvent me; 
		
		if(matchesNumFormat(depStr)){
			dep_amt = Double.parseDouble(depStr);
		}
		else{
			me = new ModelEvent(this, 1, "", this.bal);
			notifyChanged(me);
			if(isNegative(depStr)){
				throw new AcctException("Deposit amount must be positive!");
			}
			else{
				throw new AcctException("Deposit amount must be in number format (e.g. # or #.##)");
			}
		}
		
		if(dep_amt > 1.0){
			this.bal += roundTwoDecimals(dep_amt);
			setModified();
			me = new ModelEvent(this, 1, "", this.bal);
			notifyChanged(me);
		}
		else if(dep_amt < 0.0){
			me = new ModelEvent(this, 1, "", this.bal);
			notifyChanged(me);
			throw new AcctException("Deposit amount must be positive");
		}
		else{
			//System.err.println("deposit amount must be positive!");
			me = new ModelEvent(this, 1, "", this.bal);
			notifyChanged(me);
			throw new AcctException("Deposit amount must be greater than 1");
		}
	}
	
	synchronized public void agentDeposit(AcctAgentModel agent){
		this.bal += roundTwoDecimals(agent.getAmount());
		setModified();
		//ModelEvent me = new ModelEvent(this, 1, "", this.bal, agent.getOpRate(), agent.getState(), agent.getAmountTrans(), agent.getOpCount());
		ModelEvent me = new ModelEvent(this, 1, "", this.bal);
		notifyChanged(me);
		notifyAll();
	}
	
	synchronized public void agentWithdraw(double amount){
		this.bal -= roundTwoDecimals(amount);
		setModified();
		ModelEvent me = new ModelEvent(this, 1, "", this.bal);
		notifyChanged(me);
	}
	
	/**
	 * This method actually changes state of account (data) by withdrawing out of it.
	 * Throws exception when user enters too high of number or wrong-format number
	 * @param witStr
	 * @throws AcctException
	 */
	public void withdraw(String witStr) throws AcctException{
		double wit_amt;
		ModelEvent me;
		
		if(matchesNumFormat(witStr)){
			wit_amt = Double.parseDouble(witStr);
		}
		else{
			me = new ModelEvent(this, 1, "", this.bal);
			notifyChanged(me);
			if(isNegative(witStr)){
				throw new AcctException("Withdraw amount must be positive!");
			}
			else{
				throw new AcctException("Withdraw amount must be in number format (e.g. # or #.##)");
			}
		}
		
		if(wit_amt > 1.0){
			if(wit_amt <= this.bal){
				this.bal -= roundTwoDecimals(wit_amt);
				setModified();
				me = new ModelEvent(this, 1, "", this.bal);
				notifyChanged(me);
			}
			else{
				throw new AcctException("Insufficient funds: amount to withdraw " + wit_amt + " is greater than available funds: " + this.bal);
			}
		}
		else if(wit_amt >= 0.0 && wit_amt <= 1.0){
			//System.err.println("withdraw amount must be less than balance on acct! curr bal: " + this.bal);
			me = new ModelEvent(this, 1, "", this.bal);
			notifyChanged(me);
			throw new AcctException("Withdraw amount must be greater than 1");
		}
		else{
			me = new ModelEvent(this, 1, "", this.bal);
			notifyChanged(me);
			throw new AcctException("Withdraw amount must be positive");
		}
	}
	
	/**
	 * Private helper method to round given double to two decimals
	 * @param double
	 * @return double
	 */
	private double roundTwoDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
    	return Double.valueOf(twoDForm.format(d));
	}
	
	/**
	 * Private helper method to check if given string matches number format (# or #.##)
	 * @param amount
	 * @return boolean
	 */
	private boolean matchesNumFormat(String amount){
		Pattern patternDouble = Pattern.compile("([0-9]*)\\.([0-9]*)");
		Matcher matcherDouble = patternDouble.matcher(amount);
		
		Pattern patternInt = Pattern.compile("([0-9]*)");
		Matcher matcherInt = patternInt.matcher(amount);
		
		return matcherDouble.matches() || matcherInt.matches();
	}
	
	/**
	 * Private helper method to check if given string matches negative number format (-# or -#.##)
	 * @param amount
	 * @return boolean
	 */
	private boolean isNegative(String amount){
		Pattern patternDouble = Pattern.compile("-([0-9]*)\\.([0-9]*)");
		Matcher matcherDouble = patternDouble.matcher(amount);
		
		Pattern patternInt = Pattern.compile("-([0-9]*)");
		Matcher matcherInt = patternInt.matcher(amount);
		
		return matcherDouble.matches() || matcherInt.matches();
	}
	public synchronized void waitForDeposit() throws InterruptedException {
			wait();
		
	}
}

