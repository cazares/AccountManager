package accountManager.controller;
import accountManager.model.AcctAgentModel;
import accountManager.model.AcctModel;
import accountManager.model.Model;
import accountManager.view.AcctAgentView;
import accountManager.view.AcctView;
import accountManager.view.View;

/**
 * Abstract class for controller class in accountManager project.
 * Implements Controller
 * 
 * @author Miguel Cazares
 */
public abstract class AbstractController implements Controller {
	
	private Model model;
	private AcctModel aModel;
	private AcctAgentModel agentModel;
	
	private View view;
	private AcctView aView;
	private AcctAgentView agentView;
	
	public void setAcctModel(AcctModel account){this.aModel = account;}
	public AcctModel getAcctModel(){return aModel;}
	public void setModel(Model model){this.model = model;}
	
	public Model getModel(){return model;}
	
	public View getView(){return view;}
	
	public void setView(View view){this.view = view;}
	
	public void setAcctView(AcctView acctView){this.aView = acctView;}
	
	public void setAgentView(AcctAgentView agView){this.agentView = agView;}
	
	public AcctView getAcctView(){return aView;}
	
	public AcctAgentView getAgentView(){return agentView;}
	public AcctAgentModel getAgentModel() {return agentModel;}
	public void setAgentModel(AcctAgentModel agentModel) {this.agentModel = agentModel;}
}
