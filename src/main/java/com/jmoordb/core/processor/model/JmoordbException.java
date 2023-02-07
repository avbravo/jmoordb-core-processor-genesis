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

    public JmoordbException() {
        super();
    }

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

