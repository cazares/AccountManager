package accountManager.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import accountManager.controller.AcctAgentController;
import accountManager.controller.AcctListController;
import accountManager.model.AcctAgentModel;
import accountManager.model.AcctModel;
import accountManager.model.Model;
import accountManager.model.ModelEvent;

@SuppressWarnings("serial")
public class AcctAgentView extends JFrameView{// implements Runnable{
	private JFrame startFrame;
	private JFrame agentFrame;
	private String type;
	
	private JTextField amount;
	private JTextField op;
	private JTextField state;
	private JTextField tran;
	private JTextField completed;
	
	private JButton stopAgent;
	private JButton dismissAgent;
	
	public static final String START_AGENT = "Start Agent";
	public static final String DISMISS = "Dismiss";

	public static final String STOP_AGENT = "Stop Agent";
	public static final String DISMISS_AGENT = "Dismiss Agent";
	
	private void setType(String type){this.type = type;}
	public String getType(){return type;}
	public void dismissStartFrame(){startFrame.dispose();}
	
	public void setToStart(){stopAgent.setText(START_AGENT);}
	public void setToStop(){stopAgent.setText(STOP_AGENT);}
	
	public void unGrayDismissButton(){dismissAgent.setEnabled(true);}
	public void grayDismissButton(){dismissAgent.setEnabled(false);}
	
	public void dismissAgentFrame(){agentFrame.dispose();}
	
	public AcctAgentView(Model model, AcctModel aModel, AcctAgentController aController, String type){
		super(aModel, aController);
		setType(type);
		genStartAgentWindow();
	}
	
	private void genStartAgentWindow(){
		String title = "Start " + getType() + " agent for account: " + getAcctModel().getID();
		startFrame = new JFrame(title);
		startFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Dimension d = new Dimension(400,200);
    	startFrame.setMinimumSize(d);
		
		populateAgentWindow(startFrame.getContentPane());
		
		//Dimension d = new Dimension(400, 0, 0, 0);
		
		System.out.println("Type: " + getType());
		if(getType().compareTo(AcctListController.DEPOSIT) == 0)
			startFrame.setBounds(0, 500, 0, 0);
		else if(getType().compareTo(AcctListController.WITHDRAW) == 0)
			startFrame.setBounds(500, 500, 0, 0);
		startFrame.pack();
		startFrame.setVisible(true);
	}
	
	private void populateAgentWindow(Container pane){
		pane.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	
    	JTextField agentIDStr = new JTextField("Agent ID: ");
    	agentIDStr.setEditable(false);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
    	pane.add(agentIDStr, c);
    	
    	final JTextField agentID = new JTextField();
    	c.gridx = 1;
    	c.gridy = 0;
    	pane.add(agentID, c);
    	
    	JTextField amountStr = new JTextField("Amount in $: ");
    	amountStr.setEditable(false);
    	c.gridx = 0;
    	c.gridy = 1;
    	pane.add(amountStr, c);
    	
    	final JTextField amount = new JTextField();
    	c.gridx = 1;
    	c.gridy = 1;
    	pane.add(amount, c);
    	
    	JTextField opStr = new JTextField("Operations per second: ");
    	opStr.setEditable(false);
    	c.gridx = 0;
    	c.gridy = 2;
    	pane.add(opStr, c);
    	
    	final JTextField op = new JTextField("0.0");
    	c.gridx = 1;
    	c.gridy = 2;
    	pane.add(op, c);
    	
    	JButton startAgent = new JButton(START_AGENT);
    	c.gridx = 0;
    	c.gridy = 3;
    	c.gridwidth = 2;
    	pane.add(startAgent, c);
    	startAgent.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		//System.out.println("amount in editable field: " + amount.getText());
        		getAgentController().setup(agentID.getText(), amount.getText(), op.getText(), START_AGENT);
        		getAgentController().start();
        		//amount.setText("0.0");
        	}
        });
    	
    	JButton dismissAgent = new JButton(DISMISS);
    	c.gridx = 0;
    	c.gridy = 4;
    	c.gridwidth = 2;
    	pane.add(dismissAgent, c);
    	dismissAgent.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		//System.out.println("amount in editable field: " + amount.getText());
        		getAgentController().setup(agentID.getText(), amount.getText(), op.getText(), DISMISS);
        		//amount.setText("0.0");
        	}
        });
    	
	}
	
	public void genAgentView(AcctAgentModel agentModel){
		this.setAgentModel(agentModel);
		
		String title = getAgentModel().getCapType() + " agent " + getAgentModel().getID() + " for account " + getAgentModel().getAcctID();
		agentFrame = new JFrame(title);
		agentFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Dimension d = new Dimension(400,200);
    	agentFrame.setMinimumSize(d);
		
		populateAgentView(agentFrame.getContentPane());
		
		if(getType().compareTo(AcctListController.DEPOSIT) == 0)
			agentFrame.setBounds(0, 500, 0, 0);
		else if(getType().compareTo(AcctListController.WITHDRAW) == 0)
			agentFrame.setBounds(500, 500, 0, 0);
		agentFrame.pack();
		agentFrame.setVisible(true);
	}
	
	private void populateAgentView(Container pane){
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	
    	JTextField amountStr = new JTextField("Amount in $: ");
    	amountStr.setEditable(false);
    	c.gridx = 0;
    	c.gridy = 0;
    	pane.add(amountStr, c);
    	
    	//JTextField 
    	amount = new JTextField("" + getAgentModel().getAmount());
    	amount.setEditable(false);
    	c.gridx = 1;
    	c.gridy = 0;
    	pane.add(amount, c);
    	
    	JTextField opStr = new JTextField("Operations per second: ");
    	opStr.setEditable(false);
    	c.gridx = 0;
    	c.gridy = 1;
    	pane.add(opStr, c);
    	
    	//JTextField 
    	op = new JTextField("" + getAgentModel().getOpRate());
    	op.setEditable(false);
    	c.gridx = 1;
    	c.gridy = 1;
    	pane.add(op, c);
		
    	JTextField stateStr = new JTextField("State: ");
    	stateStr.setEditable(false);
    	c.gridx = 0;
    	c.gridy = 2;
    	pane.add(stateStr, c);
    	
    	//JTextField 
    	state = new JTextField(getAgentModel().getState());
    	state.setEditable(false);
    	c.gridx = 1;
    	c.gridy = 2;
    	pane.add(state, c);
    	
    	JTextField tranStr = new JTextField("Amount in $ transferred: ");
    	tranStr.setEditable(false);
    	c.gridx = 0;
    	c.gridy = 3;
    	pane.add(tranStr, c);
    	
    	//JTextField 
    	tran = new JTextField("" + getAgentModel().getAmountTrans());
    	tran.setEditable(false);
    	c.gridx = 1;
    	c.gridy = 3;
    	pane.add(tran, c);
    	
    	JTextField completedStr = new JTextField("Operations completed: ");
    	completedStr.setEditable(false);
    	c.gridx = 0;
    	c.gridy = 4;
    	pane.add(completedStr, c);
    	
    	//JTextField 
    	completed = new JTextField("" + getAgentModel().getOpCount());
    	completed.setEditable(false);
    	c.gridx = 1;
    	c.gridy = 4;
    	pane.add(completed, c);
    	
    	//JButton 
    	stopAgent = new JButton(STOP_AGENT);
    	c.gridx = 0;
    	c.gridy = 5;
    	c.gridwidth = 2;
    	pane.add(stopAgent, c);
    	stopAgent.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		//System.out.println("amount in editable field: " + amount.getText());
        		getAgentController().stopOperation(stopAgent.getText());
        		//amount.setText("0.0");
        	}
        });
    	
    	//JButton 
    	dismissAgent = new JButton(DISMISS_AGENT);
    	dismissAgent.setEnabled(false);
    	c.gridx = 0;
    	c.gridy = 6;
    	c.gridwidth = 2;
    	pane.add(dismissAgent, c);
    	dismissAgent.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		//System.out.println("amount in editable field: " + amount.getText());
        		getAgentController().stopOperation(DISMISS_AGENT);
        		//amount.setText("0.0");
        	}
        });
	}
	
	public void modelChanged(ModelEvent event) {
		//double currBal = 0.0;
		//DecimalFormat df = new DecimalFormat("0.00");
		/*
		if(currency == DOL)
			currBal = event.getAmount();
		else if(currency == EUR)
			currBal = event.getEurAmount();
		else if(currency == YEN)
			currBal = event.getYenAmount();
		
		funds.setText(df.format(currBal));
		amount.setText("0.0");
		
		private JTextField amount;
		private JTextField op;
		private JTextField state;
		private JTextField tran;
		private JTextField completed;
		
		amount.setText("" + getAgentModel().getAmount());
		op.setText("" + getAgentModel().getOpRate());
		state.setText(getAgentModel().getState());
		tran.setText("" + getAgentModel().getAmountTrans());
		completed.setText("" + getAgentModel().getOpCount());	
		
		*/
		amount.setText("" + event.getAmt());
		op.setText("" + event.getOpRate());
		state.setText(event.getState());
		tran.setText("" + event.getTrans());
		completed.setText("" + event.getOpCount());	
	}
	
	//public void run(){
	//	genAgentView();
	//}
}
