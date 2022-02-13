package com.jsm.and_image_analytics_poc.core.utils;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Clase de utilidad para obtener de manera estática el almacen de SharedPreferences
 * Usa una variante del  patrón Singleton para controlar la creación de instancias
 */
public class PreferenceManager {
    private static SharedPreferences mSharedpreferences;

    private PreferenceManager() {
    }

    public static void initialize(Context context) {
        mSharedpreferences = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences() {
        return mSharedpreferences;
    }
}

