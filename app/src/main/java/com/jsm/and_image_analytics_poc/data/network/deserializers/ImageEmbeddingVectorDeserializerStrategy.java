package com.jsm.and_image_analytics_poc.data.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jsm.and_image_analytics_poc.model.ImageEmbeddingVector;


class ImageEmbeddingVectorDeserializerStrategy implements DeserializerElementStrategyInterface<ImageEmbeddingVector> {

    @Override
    public ImageEmbeddingVector readItem(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject, ImageEmbeddingVector.class);
    }
}
