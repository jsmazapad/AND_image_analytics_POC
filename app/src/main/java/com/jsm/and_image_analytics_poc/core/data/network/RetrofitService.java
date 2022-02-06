package com.jsm.and_image_analytics_poc.core.data.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsm.and_image_analytics_poc.core.data.network.responses.NetworkElementResponse;
import com.jsm.and_image_analytics_poc.core.data.network.responses.NetworkListResponse;
import com.jsm.and_image_analytics_poc.core.data.network.responses.XMLObjectResponse;
import com.jsm.and_image_analytics_poc.core.data.repositories.responses.ElementResponse;
import com.jsm.and_image_analytics_poc.core.data.repositories.responses.ListResponse;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Clase base para crear servicios de retrofit de manera dinámica y crear distintos callbacks
 * haciendo uso de callbacks o programación reactiva
 */
public abstract class RetrofitService {

    protected RetrofitService() {

    }

    /**
     * Mapa que contiene los servicios retrofit que se crean
     */
    private static HashMap<Class, Retrofit> retrofitMap = new HashMap<>();
    /**
     * Objeto que se usa para el tratamiento de errores en la capa de red
     */
    private static NetworkErrorTreatmentInterface errorTreatment;
    /**
     * Objeto que se usa para proporcionar un proveedor de deserializadores para los datos obtenidos en las peticiones
     */
    private static CustomDeserializerProvider deserializerProvider;

    /**
     * Crea un servicio retrofit si este no existe ya y lo añade al mapa de servicios existentes, si existe lo obtiene del mapa de servicios
     * @param serviceClass Clase del servicio
     * @param errorTreatment Objeto que se encarga del tratamiento de errores
     * @param deserializerProvider Proveedor de deserializadores
     * @param baseUrl Url base del servicio
     * @param <E> Tipo usado para la especialización de la clase del servicio
     * @return
     */
    public static <E> E createService(Class<E> serviceClass, NetworkErrorTreatmentInterface errorTreatment, CustomDeserializerProvider deserializerProvider, String baseUrl) {

        RetrofitService.errorTreatment = errorTreatment;
        RetrofitService.deserializerProvider = deserializerProvider;
        Retrofit retrofit;
        if (!retrofitMap.containsKey(serviceClass)) {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(baseUrl);
            //Si tiene proveedor es json
            if(deserializerProvider != null){
                retrofitBuilder.addConverterFactory(createGsonConverter());
                //En caso contrario es xml
            }else{
                retrofitBuilder.addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
                        new Persister(new AnnotationStrategy())));
            }




            retrofit = retrofitBuilder.build();
            retrofitMap.put(serviceClass, retrofit);
        } else {
            retrofit = retrofitMap.get(serviceClass);
        }

        return retrofit.create(serviceClass);
    }

    /**
     * Crea un callback para devolver un listado de elementos haciendo uso de livedata (programación reactiva)
     * @param responseClass Clase de la respuesta
     * @param liveDataVar Livedata que contiene el listado donde se devolverá la respuesta
     * @param <E> Tipo de objeto que contiene el listado de la respuesta
     * @return
     */
    public static <E> Callback<NetworkListResponse<E>> createListCallBack(Class<E> responseClass, MutableLiveData<ListResponse<E>> liveDataVar) {

        return new Callback<NetworkListResponse<E>>() {

            @Override
            public void onResponse(Call<NetworkListResponse<E>> call,
                                   Response<NetworkListResponse<E>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    liveDataVar.setValue(new ListResponse(response.body().getElements()));

                } else {
                    liveDataVar.setValue(new ListResponse<E>(errorTreatment.handleNetworkError(response, null)));
                }
            }

            @Override
            public void onFailure(Call<NetworkListResponse<E>> call, Throwable t) {
                t.printStackTrace();
                liveDataVar.setValue(new ListResponse<E>(errorTreatment.handleNetworkError(null, t)));
            }
        };

    }

    /**
     * Crea un callback para devolver un elemento haciendo uso de livedata (programación reactiva)
     * @param responseClass Clase de la respuesta
     * @param liveDataVar Livedata que contiene el objeto que se devuelve
     * @param <E> Tipo de objeto que se devuelve
     * @return
     */
    public static <E> Callback<NetworkElementResponse<E>> createElementCallBack(Class<E> responseClass, MutableLiveData<ElementResponse<E>> liveDataVar) {

        return new Callback<NetworkElementResponse<E>>() {
            @Override
            public void onResponse(Call<NetworkElementResponse<E>> call,
                                   Response<NetworkElementResponse<E>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call", response.raw().request().url().toString());
                    liveDataVar.setValue(new ElementResponse(response.body().getElement()));
                } else {
                    try {
                        Log.d("Network Error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    liveDataVar.setValue(new ElementResponse<E>(errorTreatment.handleNetworkError(response, null)));
                }
            }

            @Override
            public void onFailure(Call<NetworkElementResponse<E>> call, Throwable t) {
                t.printStackTrace();
                liveDataVar.setValue(new ElementResponse<E>(errorTreatment.handleNetworkError(null, t)));
            }
        };

    }

    /**
     * Crea un callback para devolver un listado de elementos haciendo uso de livedata (programación reactiva) y sin método de deserialización customizado (auto conversión)
     * @param responseClass Clase de la respuesta
     * @param liveDataVar Livedata que contiene el listado donde se devolverá la respuesta
     * @param <E> Tipo de objeto que contiene el listado de la respuesta
     * @return
     */
    public static <E> Callback<List<E>> createListAutoConversionCallBack(Class<E> responseClass, MutableLiveData<ListResponse<E>> liveDataVar) {

        return new Callback<List<E>>() {
            @Override
            public void onResponse(Call<List<E>> call,
                                   Response<List<E>> response) {
                if (response.isSuccessful()) {
                    liveDataVar.setValue(new ListResponse<E>(response.body()));
                } else {
                    try {
                        Log.d("Network Error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    liveDataVar.setValue(new ListResponse<E>(errorTreatment.handleNetworkError(response, null)));
                }
            }

            @Override
            public void onFailure(Call<List<E>> call, Throwable t) {
                t.printStackTrace();
                liveDataVar.setValue(new ListResponse<E>(errorTreatment.handleNetworkError(null, t)));
            }
        };

    }
    /**
     * Crea un callback para devolver un elemento haciendo uso de livedata (programación reactiva)  y sin método de deserialización customizado (auto conversión)
     * @param responseClass Clase de la respuesta
     * @param liveDataVar Livedata que contiene el objeto que se devuelve
     * @param <E> Tipo de objeto que se devuelve
     * @return
     */
    public static <E> Callback<E> createElementAutoConversionCallBack(Class<E> responseClass, MutableLiveData<ElementResponse<E>> liveDataVar) {

        return new Callback<E>() {
            @Override
            public void onResponse(Call<E> call,
                                   Response<E> response) {
                if (response.isSuccessful()) {
                    liveDataVar.setValue(new ElementResponse<E>(response.body()));
                } else {
                    try {
                        Log.d("Network Error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    liveDataVar.setValue(new ElementResponse<E>(errorTreatment.handleNetworkError(response, null)));
                }
            }

            @Override
            public void onFailure(Call<E> call, Throwable t) {
                t.printStackTrace();
                liveDataVar.setValue(new ElementResponse<E>(errorTreatment.handleNetworkError(null, t)));
            }
        };

    }

    public static <O, E extends XMLObjectResponse<O>> Callback<E> createXMLElementToListAutoConversionCallBack(Class<E> responseClass, MutableLiveData<ListResponse<O>> liveDataVar) {

        return new Callback<E>() {
            @Override
            public void onResponse(Call<E> call,
                                   Response<E> response) {
                if (response.isSuccessful()) {
                    liveDataVar.setValue(new ListResponse<O>(response.body().getItems()));
                } else {
                    try {
                        Log.d("Network Error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    liveDataVar.setValue(new ListResponse<O>(errorTreatment.handleNetworkError(response, null)));
                }
            }

            @Override
            public void onFailure(Call<E> call, Throwable t) {
                t.printStackTrace();
                liveDataVar.setValue(new ListResponse<O>(errorTreatment.handleNetworkError(null, t)));
            }
        };

    }


    /**
     * Create un convertidor GSON y registro los métodos de deserialización custom
     * @return Fábrica de conversión GSON
     */
    private static Converter.Factory createGsonConverter() {
        System.out.println("Añade converter");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder = deserializerProvider.registerCustomDeserializers(gsonBuilder);
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }


}
