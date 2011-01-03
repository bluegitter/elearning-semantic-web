package exception;
/****************************************************************
 * The Person is illegal, wrong password or illegal privilege
 * @author william
 *
 */
public class IllegallPersonException extends Exception{
	
	private static final long serialVersionUID = 1L;
	public IllegallPersonException(){
		super();
	}
	public IllegallPersonException(String message){
		super(message);
	}
}
