package exception;

public class UserNotExistInModelException extends Exception{
	private static final long serialVersionUID = 3L;
	public UserNotExistInModelException(){
		super();
	}
	public UserNotExistInModelException(String message){
		super(message);
	}
}
