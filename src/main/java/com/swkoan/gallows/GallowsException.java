package com.swkoan.gallows;

/**
 *
 */
public class GallowsException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>GallowsException</code> without detail message.
     */
    public GallowsException() {
    }

    /**
     * Constructs an instance of
     * <code>GallowsException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public GallowsException(String msg) {
        super(msg);
    }
    
    public GallowsException(String msg, Throwable t) {
        super(msg, t);
    }
}
