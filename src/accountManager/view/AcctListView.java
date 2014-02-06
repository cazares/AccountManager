package accountManager.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import accountManager.controller.AcctListController;
import accountManager.model.AcctListModel;
import accountManager.model.ModelEvent;
//import acctManager.model.CalculatorModel;

/**
 * Defines look and listeners of main start-up window (with dropdown list of accounts)
 * @author Miguel Cazares
 *
 */
@SuppressWarnings("serial")
public class AcctListView extends JFrameView{

	public static final String DOL = "Edit account in $";
	public static final String EUR = "Edit account in Euros";
	public static final String YEN = "Edit account in Yen";
	
	public static final String DEP_AGENT = "Create deposit agent";
	public static final String WIT_AGENT = "Create withdraw agent";
	
	public static final String SAVE = "Save";
	public static final String EXIT = "Exit";

	private JTextField textField = new JTextField();
	
	/**
	 * Registers with model and controller, then creates window
	 * @param model
	 * @param controller
	 */
	public AcctListView(AcctListModel model, AcctListController controller) { 
		super(model, controller); 
		createAndShowGUI();
	 }
	
	/**
	 * Creates the frame and adds components to the frame via helper method
	 */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Account Manager: Select Account and Action");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = new Dimension(500,300);
        frame.setMinimumSize(d);
 
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Does most of the visual additions to the window
     * @param pane
     */
    public void addComponentsToPane(Container pane) {
        
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JTextField msg = new JTextField("Select account to edit:");
        msg.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(msg, c);
        
        String[] menu = new String[((AcctListModel)getModel()).getNumAccts()];
        menu = ((AcctListModel)getModel()).getNameAndIDList();
        
        final JComboBox dropDown = new JComboBox(menu);
        dropDown.setSelectedIndex(0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(dropDown, c);
        
        JButton dolButton = new JButton(DOL);
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(50,0,0,0);
        c.gridx = 0;
        c.gridy = 2;
        pane.add(dolButton, c);
        dolButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		((AcctListController)getController()).operation(DOL, dropDown.getSelectedIndex());
        	}
        });
     
        JButton eurButton = new JButton(EUR);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(50,0,0,0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(eurButton, c);
        eurButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		((AcctListController)getController()).operation(EUR, dropDown.getSelectedIndex());
        	}
        });
     
        JButton yenButton = new JButton(YEN);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(50,0,0,0);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 2;
        pane.add(yenButton, c);
        yenButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		((AcctListController)getController()).operation(YEN, dropDown.getSelectedIndex());
        	}
        });
        
        JButton createDepAgent = new JButton(DEP_AGENT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(15,0,0,0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        pane.add(createDepAgent, c);
        createDepAgent.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		((AcctListController)getController()).operation(DEP_AGENT, dropDown.getSelectedIndex());
        	}
        });
        
        JButton createWitAgent = new JButton(WIT_AGENT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(15,0,0,0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 3;
        pane.add(createWitAgent, c);
        createWitAgent.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		((AcctListController)getController()).operation(WIT_AGENT, dropDown.getSelectedIndex());
        	}
        });
     
        JButton saveButton = new JButton(SAVE);
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 4;       //third row
        pane.add(saveButton, c);
        saveButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		((AcctListController)getController()).operation(SAVE, dropDown.getSelectedIndex());
        	}
        });
        
        JButton exitButton = new JButton(EXIT);
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 2;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 4;       //third row
        pane.add(exitButton, c);
        exitButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent event){
        		((AcctListController)getController()).operation(EXIT, dropDown.getSelectedIndex());
        	}
        });
    }
    
	/**
	 * Implements the necessary event-handling code
	 */
	public void modelChanged(ModelEvent event) {
		String msg = event.getAmount() + "";
		textField.setText(msg);

	 }
}
