package accountManager.controller;
import accountManager.model.Model;
import accountManager.view.View;

/**
 * Controller interface, gets implemented by AbstractController
 * @author Miguel Cazares
 *
 */
public interface Controller {
	void setModel(Model model);
	Model getModel();
	View getView();
	void setView(View view);
}
