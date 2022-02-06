package com.jsm.and_image_analytics_poc.core.data.network.exceptions;


import com.jsm.and_image_analytics_poc.core.exceptions.BaseException;

/**
 * Excepción para recursos no encontrados (código 404)
 */
public class NotFoundException extends BaseException {
    public NotFoundException(String notFoundError) {
        super(notFoundError, false);
    }
}
