package accountManager.model;
import java.awt.event.ActionEvent;

/**
 * ModelEvent encapsulates a model event
 * @author Miguel Cazares
 *
 */
@SuppressWarnings("serial")
public class ModelEvent extends ActionEvent {

	private double balance;
	private double amt = 0;
	private double opRate = 0;
	private String state = "";
	private double trans = 0;
	private int opCount = 0;
	
	private final double EUR_RT = 0.79;
	private final double YEN_RT = 94.1;
	
	public ModelEvent(Object obj, int id, String message, double balance){
		super(obj, id, message);
		this.balance = balance;
	}
	public ModelEvent(Object obj, int id, String message, double amt, double opRate, String state, double trans, int opCount){
		super(obj, id, message);
		this.amt = amt;
		this.opRate = opRate;
		this.state = state;
		this.trans = trans;
		this.opCount = opCount;
	}
	public double getAmount(){return balance;}
	public double getYenAmount(){return balance * YEN_RT;}
	public double getEurAmount(){return balance * EUR_RT;}
	public double getAmt() {
		return amt;
	}
	public double getOpRate() {
		return opRate;
	}
	public String getState() {
		return state;
	}
	public double getTrans() {
		return trans;
	}
	public int getOpCount() {
		return opCount;
	}
}
