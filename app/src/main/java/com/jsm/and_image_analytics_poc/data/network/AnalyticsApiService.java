package com.jsm.and_image_analytics_poc.data.network;

import com.jsm.and_image_analytics_poc.core.data.network.responses.NetworkElementResponse;
import com.jsm.and_image_analytics_poc.model.ImageEmbeddingVector;

import okhttp3.RequestBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Interface con la definición de todas las llamadas al servicio web
 */
public interface AnalyticsApiService {

    /**
     *
     * @param algorithm Algoritmo que se utilizará
     * @return Objeto que representa la llamada al servicio web
     */
    //@Multipart
    @POST("image/{alg}")
    //@Headers("Content-Type: image/jpeg")
    Call<NetworkElementResponse<ImageEmbeddingVector>> getEmbedding(@Path("alg") String algorithm, @Body RequestBody image);

}
