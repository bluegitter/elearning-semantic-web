/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exception.lp;

/**
 *
 * @author william
 */
public class DeleteOperationException extends Exception {

    private static final long serialVersionUID = 4L;

    public DeleteOperationException() {
        super();
    }

    public DeleteOperationException(String message) {
        super(message);
    }
}
