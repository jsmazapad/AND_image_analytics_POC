package com.jsm.and_image_analytics_poc.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageEmbeddingVector {

    @SerializedName("embedding")
    @Expose
    private List<Double> embedding = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ImageEmbeddingVector() {
    }

    /**
     *
     * @param embedding
     */
    public ImageEmbeddingVector(List<Double> embedding) {
        super();
        this.embedding = embedding;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ImageEmbeddingVector.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("embedding");
        sb.append('=');
        sb.append(((this.embedding == null)?"<null>":this.embedding));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
