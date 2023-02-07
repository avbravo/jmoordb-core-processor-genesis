/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmoordb.core.processor.model;

/**
 *
 * @author avbravo
 */
public class JmoordbException extends Throwable {

    @java.io.Serial
    static final long serialVersionUID = -3387516993124229948L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public JmoordbException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public JmoordbException(String message) {
        super(message);
    }


    public JmoordbException(String message, Throwable cause) {
        super(message, cause);
    }

 
    public JmoordbException(Throwable cause) {
        super(cause);
    }

  
    protected JmoordbException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

