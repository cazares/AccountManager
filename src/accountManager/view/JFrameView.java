package accountManager.view;
import javax.swing.*;

import accountManager.controller.AcctAgentController;
import accountManager.controller.AcctController;
import accountManager.controller.Controller;
import accountManager.model.AbstractModel;
import accountManager.model.AcctAgentModel;
import accountManager.model.AcctModel;
import accountManager.model.Model;
import accountManager.model.ModelListener;

//import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * This class lets a view register with a controller and a model, along with getters
 * @author Miguel Cazares
 *
 */
@SuppressWarnings("serial")
abstract public class JFrameView extends JFrame implements View, ModelListener {
	private Model model;
	private Controller controller;
	private AcctModel acctModel;
	private AcctAgentModel agentModel;
	private AcctController acctController;
	private AcctAgentController agentController;
	
	public JFrameView (AcctController aController){setAcctController(aController);}
	
	public JFrameView (Model model, Controller controller){
		setModel(model);
		setController(controller);
	}
	public JFrameView (AcctModel aModel, AcctController aController){
		setAcct(aModel);
		setAcctController(aController);
	}
	public JFrameView (AcctModel aModel, AcctAgentController aController){
		setAcctWithoutRegistering(aModel);
		setAgentController(aController);
	}
	//public JFrameView (AcctAgentModel aModel, AcctAgentController aController){
	//	setAgentModel(aModel);
	//	setAgentController(aController);
	//}
	public void registerWithModel(){((AbstractModel)model).addModelListener(this);}
	public void registerWithAcctModel(){((AbstractModel)acctModel).addModelListener(this);}
	public void registerWithAgentModel(){((AbstractModel)agentModel).addModelListener(this);}
	
	public Controller getController(){return controller;}
	public void setController(Controller controller){this.controller = controller;}
	public void setAcctController(AcctController aController){this.acctController = aController;}
	public void setAgentController(AcctAgentController agController){this.agentController = agController;}
	public Model getModel(){return model;}
	public AcctModel getAcctModel(){return acctModel;}
	public AcctAgentModel getAgentModel(){return agentModel;}
	public AcctController getAcctController(){return acctController;}
	public AcctAgentController getAgentController(){return agentController;}
	
	public void setModel(Model model) {
		this.model = model;
		registerWithModel();
	}
	
	public void setAcct(AcctModel aModel){
		this.acctModel = aModel;
		registerWithAcctModel();
	}
	
	public void setAcctWithoutRegistering(AcctModel aModel){
		this.acctModel = aModel;
	}
	
	public void setAgentModel(AcctAgentModel aModel){
		this.agentModel = aModel;
		registerWithAgentModel();
	}
}
