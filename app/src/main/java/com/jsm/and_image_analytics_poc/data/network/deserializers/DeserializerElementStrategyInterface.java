package com.jsm.and_image_analytics_poc.data.network.deserializers;

import com.google.gson.JsonObject;

public interface DeserializerElementStrategyInterface<E> {

    E readItem(final JsonObject jsonObject);

}
