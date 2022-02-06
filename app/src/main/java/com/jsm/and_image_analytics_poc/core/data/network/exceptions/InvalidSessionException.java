package com.jsm.and_image_analytics_poc.core.data.network.exceptions;


import com.jsm.and_image_analytics_poc.core.exceptions.BaseException;

/**
 * Excepción para errores de sesión
 */
public class InvalidSessionException extends BaseException {
    public InvalidSessionException(String generalSessionError) {
        super(generalSessionError, false);
    }
}
