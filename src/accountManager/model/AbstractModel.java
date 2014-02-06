package accountManager.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * AbstractModel is an abstract class that implements the Model interface
 * This class handles notifications that the model changed and maintains modelListeners
 * @author Miguel Cazares
 *
 */
public abstract class AbstractModel implements Model {
	
	private ArrayList<ModelListener> listeners = new ArrayList<ModelListener>(5);

	/**
	 * Sends out notification (that model changed) to all registered listeners
	 * @param event - event that encapsulates what happened
	 */
	public void notifyChanged(ModelEvent event){
		@SuppressWarnings("unchecked")
		ArrayList<ModelListener> list = (ArrayList<ModelListener>)listeners.clone();
		Iterator<ModelListener> it = list.iterator();
		while(it.hasNext()){
			ModelListener ml = (ModelListener)it.next();
			ml.modelChanged(event);
		}
	}
	
	public void addModelListener(ModelListener l){
		listeners.add(l);
	}
	public void removeModelListener(ModelListener l){
		listeners.remove(l);
	}
}
