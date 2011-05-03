/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exception.jena;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class HasNoPropertyValueException extends Exception {

    private static final long serialVersionUID = 5L;

    public HasNoPropertyValueException() {
        super();
    }

    public HasNoPropertyValueException(String message) {
        super(message);
    }
}
