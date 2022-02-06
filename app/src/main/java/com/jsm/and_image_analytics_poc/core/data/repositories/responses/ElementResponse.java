package com.jsm.and_image_analytics_poc.core.data.repositories.responses;


import com.jsm.and_image_analytics_poc.core.exceptions.BaseException;

/**
 * Clase Wrapper para un response de una petición que sólo incluye un elemento (json object)
 * Tiene dos parámetros, result que es el el objeto que se incluye en el response
 * y error, que es la excepción que produce en el caso de que la petición no obtenga una respuesta válida
 * @param <S> Tipo del objeto que viene incluido en el response
 */
public class ElementResponse<S> {

    private S result;
    private BaseException error;

    public ElementResponse(S result) {
        this.result = result;
        this.error = null;
    }

    public ElementResponse(BaseException error) {
        this.error = error;
        this.result = null;
    }

    public S getResultElement() {
        return result;
    }

    public void setResultElement(S element) {
        this.result = element;
    }

    public BaseException getError() {
        return error;
    }

    public void setError(BaseException error) {
        this.error = error;
    }
}