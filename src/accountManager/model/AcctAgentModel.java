package accountManager.model;

import accountManager.view.AcctView;

public class AcctAgentModel extends AbstractModel implements Runnable{
	private String type;
	private AcctModel accountModel;
	
	private static final String START_STATE = "Started";
	private static final String STOP_STATE = "Stopped";
	private static final String BLOCKED_STATE = "Blocked";
	
	private boolean stopRequested = false;
	
	private Thread agentThread;
	
	public void requestStop(){
		stopRequested = true;
		stopState();
		ModelEvent me = new ModelEvent(this, 1, "", getAmount(), getOpRate(), getState(), getAmountTrans(), getOpCount());
		notifyChanged(me);
		return;
	}
	private void resetStopRequest(){stopRequested = false;}
	
	private String agentID;
	private double amount;
	private double opRate;
	private String state;
	private double amountTrans;
	private int opCount;
	
	private void setID(String agentID){this.agentID = agentID;}
	public String getID(){return agentID;}
	
	public double getAmount() {
		return amount;
	}
	private void setAmount(double amount) {
		this.amount = amount;
	}
	public double getOpRate() {
		return opRate;
	}
	private void setOpRate(double opRate) {
		this.opRate = opRate;
	}
	public String getState() {
		return state;
	}
	private void setState(String state) {
		this.state = state;
	}
	public double getAmountTrans() {
		return amountTrans;
	}
	private void setAmountTrans(double amountTrans) {
		this.amountTrans = amountTrans;
	}
	public int getOpCount() {
		return opCount;
	}
	private void setOpCount(int opCount) {
		this.opCount = opCount;
	}
	
	private void startState(){setState(START_STATE);}
	private void stopState(){setState(STOP_STATE);}
	//private void blockedState(){setState(BLOCKED_STATE);}
	
	private void incAmtTrans(double amt){setAmountTrans(getAmountTrans() + amt);}
	private void incOpCount(){setOpCount(getOpCount() + 1);}
	
	private void setType(String aType){this.type = aType;}
	private void setAcctModel(AcctModel aModel){this.accountModel = aModel;}
	public String getType(){return type;}
	// returns type string with first letter capitalized
	public String getCapType(){return type.substring(0,1).toUpperCase() + type.substring(1);}
	public String getAcctID(){return getAcctModel().getID();}
	public AcctModel getAcctModel(){return accountModel;}
	
	public AcctAgentModel(AcctModel aModel, String agentID, String amountStr, String opStr, String agentType){
		// *** CHECK IF VALID! exception....
		setID(agentID);
		setAmount(Double.parseDouble(amountStr));
		setOpRate(Double.parseDouble(opStr));
		setState(STOP_STATE);
		setAmountTrans(0.0);
		setOpCount(0);
		
		this.setAcctModel(aModel);
		this.setType(agentType);
	}
	
	private void runDeposit(){
		resetStopRequest();
		startState();
		ModelEvent me = new ModelEvent(this, 1, "", 0);
		notifyChanged(me);
		while(true){
			
			if(stopRequested){
			//	System.out.println("Stop requested = true");
				stopState();
			//	notifyChanged(me);
				break;
			}
			
			getAcctModel().agentDeposit(this);
			incAmtTrans(getAmount());
			incOpCount();
			me = new ModelEvent(this, 1, "", getAmount(), getOpRate(), getState(), getAmountTrans(), getOpCount());
			notifyChanged(me);
			System.out.println("Deposited " + getAmount());
			try{
				Thread.sleep((long)(1000/opRate));
			}
			catch(InterruptedException exc){
				break;
			}
			//notifyAll();
		}
		stopState();
		me = new ModelEvent(this, 1, "", getAmount(), getOpRate(), getState(), getAmountTrans(), getOpCount());
		notifyChanged(me);
	}
	
	private void runWithdraw() throws InterruptedException{
		resetStopRequest();
		startState();
		ModelEvent me = new ModelEvent(this, 1, "", 0);
		notifyChanged(me);
		while(!Thread.interrupted()){
			
			if(stopRequested){
			//	System.out.println("Stop requested = true");
				stopState();
			//	notifyChanged(me);
				break;
			}
			
			while(getAmount() > getAcctModel().getBal(AcctView.DOL)){ 
				setState(BLOCKED_STATE);
				me = new ModelEvent(this, 1, "", getAmount(), getOpRate(), getState(), getAmountTrans(), getOpCount());
				notifyChanged(me);
				getAcctModel().waitForDeposit(); 
				setState(START_STATE);
				me = new ModelEvent(this, 1, "", getAmount(), getOpRate(), getState(), getAmountTrans(), getOpCount());
				notifyChanged(me);
			}
			
			getAcctModel().agentWithdraw(getAmount());
			incAmtTrans(getAmount());
			incOpCount();
			me = new ModelEvent(this, 1, "", getAmount(), getOpRate(), getState(), getAmountTrans(), getOpCount());
			notifyChanged(me);
			System.out.println("Withdrew " + getAmount());
			try{
				Thread.sleep((long)(1000/opRate));
			}
			catch(InterruptedException exc){
				break;
			}
		}
		stopState();
		me = new ModelEvent(this, 1, "", getAmount(), getOpRate(), getState(), getAmountTrans(), getOpCount());
		notifyChanged(me);
	}
	
	public void run() {
		if(getType().equals("deposit")){
			System.out.println("About to execute runDeposit()");
			runDeposit();
		}
		else if(getType().equals("withdraw")){
			System.out.println("About to execute runWithdraw()");
			try {
				runWithdraw();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.err.println("Error: unknown agent type!");
		}
	}

	public void restart(){
		//resetStopRequest();
		agentThread = new Thread(this);
		agentThread.run();
	}
}
