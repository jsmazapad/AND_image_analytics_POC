package com.jsm.and_image_analytics_poc.core.data.network;

import com.jsm.and_image_analytics_poc.core.exceptions.BaseException;

import retrofit2.Response;

/**
 * Interface para definir como se manejarán los errores en la aplicación
 */
public interface NetworkErrorTreatmentInterface {

    /**
     * Procesa el error obtenido y le asigna un mensaje y un tipo de excepción
     * @param response respuesta (en los casos en que la conexión se ha podido realizar y se ha obtenido una respuesta pero el status code no es 200)
     * @param t throwable en los casos en los que la conexión ha fallado por algún motivo y no se ha obtenido ninguna respuesta
     * @return
     */
     BaseException handleNetworkError(Response response, Throwable t);
}
