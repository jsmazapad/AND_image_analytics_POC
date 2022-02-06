package com.jsm.and_image_analytics_poc.core.data.repositories.responses;

import com.jsm.and_image_analytics_poc.core.exceptions.BaseException;

import java.util.List;

/**
 * Clase Wrapper para un response de una petición que incluye un listado de elementos (json array)
 * Tiene dos parámetros, result que es el listado de objetos que se incluye en el response
 * y error, que es la excepción que produce en el caso de que la petición no obtenga una respuesta válida
 * @param <S> Tipo del objeto del listado que viene incluido en el response
 */
public class ListResponse<S> {

    private List<S> results;
    private BaseException error;

    public ListResponse(List<S> results) {
        this.results = results;
        this.error = null;
    }

    public ListResponse(BaseException error) {
        this.error = error;
        this.results = null;
    }

    public List<S> getResultList() {
        return results;
    }

    public void setResultList(List<S> posts) {
        this.results = posts;
    }

    public BaseException getError() {
        return error;
    }

    public void setError(BaseException error) {
        this.error = error;
    }
}
