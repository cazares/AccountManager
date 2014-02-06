package accountManager.controller;

import accountManager.model.AcctAgentModel;
import accountManager.model.AcctModel;
import accountManager.model.Model;
import accountManager.view.AcctAgentView;

public class AcctAgentController extends AbstractController{
	private Thread agentThread;
	
	public AcctAgentController(Model model, AcctModel aModel, String type){
		this.setAcctModel(aModel);
		this.setAgentView(new AcctAgentView(model, this.getAcctModel(), this, type));
	}
	
	public void setup(String agentID, String amountStr, String opStr, String option){
		if(option.equals(AcctAgentView.START_AGENT)){
			this.getAgentView().dismissStartFrame();
			this.setAgentModel(new AcctAgentModel(getAcctModel(), agentID, amountStr, opStr, getAgentView().getType()));
			//Thread viewThread = new Thread(this.getAgentView());
			this.getAgentView().genAgentView(this.getAgentModel());
			//this.getAgentView().setAgentModel(this.getAgentModel());
			//viewThread.start();
			
			//getAgentModel().run();
			//Thread 
			//agentThread = new Thread(getAgentModel());
			//agentThread.start();
		}
		else if(option.equals(AcctAgentView.DISMISS)){
			this.getAgentView().dismissStartFrame();
		}
		else{
			// this code should NOT be reached
			System.err.println("Warning: a button was pressed that is not recognized!");
		}
	}
	
	public void start(){
		agentThread = new Thread(getAgentModel());
		agentThread.start();
	}
	
	public void stopOperation(String option){
		//boolean grayedDismiss = true;
		if(option.equals(AcctAgentView.STOP_AGENT)){
			getAgentModel().requestStop();
			//getAgentModel().
			getAgentView().setToStart();
			getAgentView().unGrayDismissButton();
		}
		else if(option.equals(AcctAgentView.START_AGENT)){
			System.out.println("Start agent button pressed");
			//agentThread.notifyAll();
			agentThread = new Thread(getAgentModel());
			agentThread.start();
			
			
			getAgentView().setToStop();
			getAgentView().grayDismissButton();
			
			//getAgentModel().restart();
		}
		else if(option.equals(AcctAgentView.DISMISS_AGENT)){
			getAgentView().dismissAgentFrame();
			getAgentModel().requestStop();
		}
		else{
			// NOTE: this code SHOULD NOT be reached
			System.err.println("Warning: a button was pressed that is not recognized!");
		}
		
		
	}
}
