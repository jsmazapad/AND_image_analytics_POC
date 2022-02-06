package com.jsm.and_image_analytics_poc.core.data.network.responses;


import java.util.List;
/**
 * Clase base para respuesta de servicio web con un array json
 * @param <T> Tipo de los objetos que contiene el array
 */
public class NetworkListResponse<T> {


    private List<T> elements;


    public NetworkListResponse(List<T> elements) {
        this.elements = elements;
    }


    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

}
