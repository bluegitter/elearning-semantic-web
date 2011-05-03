package exception.jena;

/********************************************************************
 * The individual does not exist in the model
 * @author william
 *
 */
public class IndividualNotExistException extends Exception {

    private static final long serialVersionUID = 4L;

    public IndividualNotExistException() {
        super();
    }

    public IndividualNotExistException(String message) {
        super(message);
    }
}
