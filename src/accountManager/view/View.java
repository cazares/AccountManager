package accountManager.view;
import accountManager.controller.Controller;
import accountManager.model.Model;

/**
 * Public interface to view components of MVC architecture of acctManager project
 * @author Miguel Cazares
 *
 */
public interface View {
	Controller getController();
	void setController(Controller controller);
	public Model getModel();
	void setModel(Model model);
}
