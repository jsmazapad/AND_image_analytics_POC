package com.jsm.and_image_analytics_poc.data.network.deserializers;

import com.google.gson.JsonArray;

import java.util.List;

public interface DeserializerListStrategyInterface<E> {

    List<E> readItems(final JsonArray jsonArray);

}
