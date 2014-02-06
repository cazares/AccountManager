package accountManager.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import accountManager.controller.AcctController;
import accountManager.model.AcctModel;
import accountManager.model.Model;
import accountManager.model.ModelEvent;

/**
 * This class deals with the single account window that pops up after pressing "Edit account in ..." in main window
 * @author Miguel Cazares
 *
 */
@SuppressWarnings("serial")
public class AcctView extends JFrameView{
	private JFrame frame;
	private int bounds[] = new int[4];

	public static final String DOL = AcctListView.DOL;
	public static final String EUR = AcctListView.EUR;
	public static final String YEN = AcctListView.YEN;

	public static final String DEPOSIT = "Deposit";
	public static final String WITHDRAW = "Withdraw";
	public static final String DISMISS = "Dismiss";

	private String currency;
	private JTextField funds;
	private final JTextField amount = new JTextField("0.0");
	
	public void dispose(){frame.dispose();}
	public void setVisible(boolean option){frame.setVisible(option);}
	public Point getLoc(){return frame.getLocation();}
	
	public AcctView(Model model, AcctModel aModel, AcctController aController, String editType){
		super(aModel, aController);
		showEditWindow(editType);
	}
	
    public void showEditWindow(String type) {
    	currency = type;
    	String title = getAcctModel().getName() + " " + getAcctModel().getID() + "; Operations in ";
    	String titleStr = "";
    	  	
    	if(type == YEN){
    		titleStr = title + "Yen";
    		bounds[0] = 500;
    		bounds[1] = 0;
    		bounds[2] = 400;
    		bounds[3] = 200;

    	}
    	else if(type == DOL){
    		titleStr = title + "$";
			bounds[0] = 0;
			bounds[1] = 300;
			bounds[2] = 400;
			bounds[3] = 200;
    	}
    	else if(type == EUR){
    		titleStr = title + "Euros";
			bounds[0] = 500;
			bounds[1] = 300;
			bounds[2] = 400;
			bounds[3] = 200;
    	}
    	
    	frame = new JFrame(titleStr);
    	frame.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
    	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	Dimension d = new Dimension(400,200);
    	frame.setMinimumSize(d);
    	addToEditWindow(frame.getContentPane(), type);
    	frame.pack();
    	frame.setVisible(true);
    }
    
    public void addToEditWindow(Container pane, String type){
    	pane.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	double balance = 0.0;
    	String amtStr = "Enter amount in ";
    	
    	if(type == YEN){
    		type = " yen";
    		balance = getAcctModel().getBal(YEN);
    		amtStr += "Yen: ";
    	}
    	else if(type == DOL){
    		type = " dollars";
    		balance = getAcctModel().getBal(DOL);
    		amtStr += "$: ";
    	}
    	else if(type == EUR){
    		type = " euros";
    		balance = getAcctModel().getBal(EUR);
    		amtStr += "Euros: ";
    	}
    	else{
    		type = " unrecognized currency!";
    	}
    	DecimalFormat df = new DecimalFormat("0.00");
    	//df.setMinimumFractionDigits(2);
    	JTextField msg = new JTextField("Available funds: ");
    	msg.setEditable(false);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
    	pane.add(msg, c);
    	
    	funds = new JTextField(df.format(balance));
    	funds.setEditable(false);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 1;
    	c.gridx = 1;
    	c.gridy = 0;
    	pane.add(funds, c);
    	
    	JTextField typeStr = new JTextField(type);
    	typeStr.setEditable(false);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 1;
    	c.gridx = 2;
    	c.gridy = 0;
    	pane.add(typeStr, c);
    	
    	msg = new JTextField(amtStr);
    	msg.setEditable(false);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 1;
    	pane.add(msg, c);
    	
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 1;
    	c.gridx = 2;
    	c.gridy = 1;
    	pane.add(amount, c);
    	
    	JButton deposit = new JButton(DEPOSIT);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridx = 0;
    	c.gridy = 2;
    	pane.add(deposit, c);
        deposit.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		//System.out.println("amount in editable field: " + amount.getText());
        		getAcctController().operation(DEPOSIT, amount.getText());
        		amount.setText("0.0");
        	}
        });
    	
    	JButton withdraw = new JButton(WITHDRAW);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridx = 1;
    	c.gridy = 2;
    	pane.add(withdraw, c);
    	withdraw.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event){
    			getAcctController().operation(WITHDRAW, amount.getText());
    			amount.setText("0.0");
    		}
    	});
    	
    	JButton dismiss = new JButton(DISMISS);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridx = 2;
    	c.gridy = 2;
    	pane.add(dismiss, c);    	
    	dismiss.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event){
    			getAcctController().operation(DISMISS, amount.getText());
    		}
    	});
    }
	
	public void modelChanged(ModelEvent event) {
		double currBal = 0.0;
		DecimalFormat df = new DecimalFormat("0.00");
		
		if(currency == DOL)
			currBal = event.getAmount();
		else if(currency == EUR)
			currBal = event.getEurAmount();
		else if(currency == YEN)
			currBal = event.getYenAmount();
		
		funds.setText(df.format(currBal));
		amount.setText("0.0");
	}
}
