/*
 * @(#)UploaderException.java
 *
 * Copyright 2008 iMazing Studio. All rights reserved.
 */
package HTTPClient;

/**
 * Signals that an Uploader exception of some sort has occurred. This
 * class is the general class of exceptions produced by failed or
 * interrupted Uploader operations.
 *
 * @author  wannasoft
 * @version 1.00 20 Feb 2008
 */
public class UploaderException extends Exception {
    /**
     * Constructs an {@code UploaderException} with {@code null}
     * as its error detail message.
     */
    public UploaderException() {
	super();
    }

    /**
     * Constructs an {@code UploaderException} with the specified detail message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     */
    public UploaderException(String message) {
    	super(message);
    }
}
