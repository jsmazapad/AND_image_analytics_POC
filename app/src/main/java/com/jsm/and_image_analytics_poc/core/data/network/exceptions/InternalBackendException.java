package com.jsm.and_image_analytics_poc.core.data.network.exceptions;


import com.jsm.and_image_analytics_poc.core.exceptions.BaseException;

/**
 * Excepción para errores internos de backend
 */
public class InternalBackendException extends BaseException {

    public InternalBackendException(String message) {
        super(message, false);
    }
}