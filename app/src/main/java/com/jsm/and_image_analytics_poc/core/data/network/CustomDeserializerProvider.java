package com.jsm.and_image_analytics_poc.core.data.network;

import com.google.gson.GsonBuilder;

/**
 * Interface para definir los deserializadores que se registrarán
 */
public interface CustomDeserializerProvider {

    GsonBuilder registerCustomDeserializers(GsonBuilder gsonBuilder);
}
