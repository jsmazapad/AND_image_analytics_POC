package com.jsm.and_image_analytics_poc.data.network.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jsm.and_image_analytics_poc.core.data.network.DeserializerListStrategyInterface;
import com.jsm.and_image_analytics_poc.core.data.network.responses.NetworkListResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Clase común para agregar todas las estrategias de deserialización que tengan que ver con listados
 * de esta manera se selecciona una u otra en función de E
 * @param <E> Tipo del objeto que se deserializa
 */
public class ListDeserializer<E> implements JsonDeserializer<NetworkListResponse<E>> {

    private static final String ENTRY = "salida";
    private DeserializerListStrategyInterface deserializerListStrategy;

    @Override
    public NetworkListResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonArray contentList = json.getAsJsonArray();
//        Agregar estrategias, ejemplo:
//        if (((ParameterizedType) typeOfT).getActualTypeArguments()[0] == Beer.class) {
//            deserializerListStrategy = new BeerListDeserializerStrategy();
//        } else {
//            throw new JsonParseException("Ha habido un error con la estructura de la respuesta");
//        }

        List result = deserializerListStrategy.readItems(contentList);
        return new NetworkListResponse<>(result);
    }


}
