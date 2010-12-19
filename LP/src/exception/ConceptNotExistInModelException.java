package exception;

public class ConceptNotExistInModelException extends Exception{
	private static final long serialVersionUID = 2L;
	public ConceptNotExistInModelException(){
		super();
	}
	public ConceptNotExistInModelException(String message){
		super(message);
	}
}
