package com.jsm.and_image_analytics_poc.core.data.network.responses;

/**
 * Clase base para respuesta de servicio web con un objeto json
 * @param <E> Tipo del objeto devuelto
 */
public class NetworkElementResponse<E> {

    private E element;

    public NetworkElementResponse(E element) {
        this.element = element;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }
}
