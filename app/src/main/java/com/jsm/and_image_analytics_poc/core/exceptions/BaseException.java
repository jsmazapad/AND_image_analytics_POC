package com.jsm.and_image_analytics_poc.core.exceptions;

/**
 * Excepción base general para errores en la aplicación
 */
public class BaseException extends Exception {
    /**
     * Indica si esta excepción ha sido provocada por una operación que permite reintento
     */
    boolean retryable;
    public BaseException(String message, boolean retryable) {
        super(message);
        this.retryable = retryable;
    }

    public boolean isRetryable() {
        return retryable;
    }
}
