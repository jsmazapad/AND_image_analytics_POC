package com.jsm.and_image_analytics_poc.data.network;

import android.util.Log;

import com.jsm.and_image_analytics_poc.config.StringConfig;
import com.jsm.and_image_analytics_poc.core.data.network.NetworkErrorTreatmentInterface;
import com.jsm.and_image_analytics_poc.core.data.network.exceptions.InternalBackendException;
import com.jsm.and_image_analytics_poc.core.data.network.exceptions.InvalidSessionException;
import com.jsm.and_image_analytics_poc.core.data.network.exceptions.NotFoundException;
import com.jsm.and_image_analytics_poc.core.exceptions.BaseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;


import retrofit2.Response;

/**
 * Clase con tratamiento de errores
 */
public class AppNetworkErrorTreatment implements NetworkErrorTreatmentInterface {

    /**
     * Procesa el error obtenido y le asigna un mensaje y un tipo de excepción
     * @param response respuesta (en los casos en que la conexión se ha podido realizar y se ha obtenido una respuesta pero el status code no es 200)
     * @param t throwable en los casos en los que la conexión ha fallado por algún motivo y no se ha obtenido ninguna respuesta
     * @return
     */
    @Override
    public BaseException handleNetworkError(Response response, Throwable t) {
        String error = null;
        BaseException exception = null;

        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            if (!jObjError.getString("message").isEmpty()) {
                error = jObjError.getString("message");
            }
        } catch (JSONException | IOException | NullPointerException e) {
            e.printStackTrace();
        }


        if (error == null) {
            error = StringConfig.SERVICE_NETWORK_ERROR;
        }


        if (t != null) {
            Log.d("throwable", Objects.requireNonNull(t.getMessage()).isEmpty() ? StringConfig.GENERAL_NETWORK_ERROR : t.getMessage());
            error = StringConfig.GENERAL_NETWORK_ERROR;
            exception = new BaseException(error, true);
        } else {
            if (response != null) {
                switch (response.code()) {
                    case 401:
                    case 403:
                        exception = new InvalidSessionException(StringConfig.INVALID_SESSION_ERROR);
                        break;
                    case 404:
                        exception = new NotFoundException(StringConfig.GENERAL_NOT_FOUND_ERROR);
                        break;
                    case 500:
                        exception = new InternalBackendException(StringConfig.GENERAL_BACKEND_ERROR);
                        break;
                    default:
                        exception = new BaseException(error, false);
                        break;
                }
            }
        }

        return exception;
    }

}
