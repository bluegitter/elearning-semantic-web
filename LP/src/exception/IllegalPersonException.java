package exception;
/****************************************************************
 * The Person is illegal, wrong password or illegal privilege
 * @author william
 *
 */
public class IllegalPersonException extends Exception{
	
	private static final long serialVersionUID = 1L;
	public IllegalPersonException(){
		super();
	}
	public IllegalPersonException(String message){
		super(message);
	}
}
