package exception.jena;

/********************************************************************
 * The individual already exists in the model
 * @author william
 *
 ********************************************************************/
public class IndividualExistException extends Exception {

    private static final long serialVersionUID = 3L;

    public IndividualExistException() {
        super();
    }

    public IndividualExistException(String message) {
        super(message);
    }
}
