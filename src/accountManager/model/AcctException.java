package accountManager.model;

/**
 * This simple class is used for handling of general account exceptions (non-critical exceptions)
 * @author Miguel Cazares
 *
 */
@SuppressWarnings("serial")
public class AcctException extends Exception{
	public AcctException() { super(); }
	public AcctException(String msg) { super(msg); }
}
