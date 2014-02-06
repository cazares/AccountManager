package accountManager.model;

/**
 * This simple class is used for handling of critical account exceptions
 * @author Miguel Cazares
 *
 */
@SuppressWarnings("serial")
public class CriticalException extends Exception{
	public CriticalException() { super(); }
	public CriticalException(String msg) { super(msg); }
}
