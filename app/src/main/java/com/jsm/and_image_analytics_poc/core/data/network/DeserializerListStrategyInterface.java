package com.jsm.and_image_analytics_poc.core.data.network;

import com.google.gson.JsonArray;

import java.util.List;

/**
 * Interface para definir una estrategia de deserialización de un listado
 * haciendo uso del patrón Strategy
 * @param <E> Tipo de objetos que contiene el listado
 */
public interface DeserializerListStrategyInterface<E> {

    List<E> readItems(final JsonArray jsonArray);

}
