package com.jsm.and_image_analytics_poc.data.network;

import com.google.gson.GsonBuilder;
import com.jsm.and_image_analytics_poc.core.data.network.CustomDeserializerProvider;
import com.jsm.and_image_analytics_poc.core.data.network.responses.NetworkElementResponse;
import com.jsm.and_image_analytics_poc.data.network.deserializers.ElementDeserializer;
import com.jsm.and_image_analytics_poc.model.ImageEmbeddingVector;

public class AppDeserializerProvider implements CustomDeserializerProvider {

    public GsonBuilder registerCustomDeserializers(GsonBuilder gsonBuilder) {

        gsonBuilder.registerTypeAdapter(NetworkElementResponse.class, new ElementDeserializer<ImageEmbeddingVector>());

        return gsonBuilder;
    }

}
