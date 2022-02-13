package com.jsm.and_image_analytics_poc.core.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Manejador base para acciones de comunicación con otras Apps
 */
public class ExternalActionsManager {

    /**
     * Abre la aplicación de mail para redactar un email a la dirección indicada
     * @param uriString String con el email, puede ser el email sólo o haciendo uso del protocolo mailto
     * @param context Contexto donde se ejecuta la acción
     * @return Booleano indicando si la operación ha tenido éxito
     */
    public static boolean openEmail(String uriString, Context context) {

        try {
            String email = uriString.substring(uriString.indexOf(":") + 1);
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", email, null));

            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            context.startActivity(Intent.createChooser(intent, null));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * Abre el marcador del teléfono con el número de teléfono que se le pasa
     * @param uriString Número de teléfono
     * @param context Contexto donde se ejecuta la acción
     * @return Booleano indicando si la operación ha tenido éxito
     */
    public static boolean callPhone(String uriString, Context context) {
        final String TEL_SCHEME = "tel://";
        try {
            if(!uriString.contains(TEL_SCHEME))
            {
                uriString = TEL_SCHEME + uriString;
            }
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uriString));
            context.startActivity(intent);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * Abre el la página en el navegador web del dispositivo móvil
     * @param urlString Página a abrir
     * @param context Contexto donde se ejecuta la acción
     * @return Booleano indicando si la operación ha tenido éxito
     */
    public static boolean openUrlExternal(String urlString, Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            context.startActivity(intent);
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    /**
     * Comparte la url de playstores de la aplicación
     * @param context Contexto donde se ejecuta la acción
     * @return Booleano indicando si la operación ha tenido éxito
     */
    public static boolean shareApp(Context context) {
        try {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=es");
            context.startActivity(Intent.createChooser(i, "Compartir app"));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Comparte contenido con distintas aplicaciones
     * @param chooser Chooser que se utiliza para crear la acción
     * @param content Contenido a compartir
     * @param context Contexto donde se ejecuta la acción
     * @return Booleano indicando si la operación ha tenido éxito
     */
    public static boolean shareContent( String chooser, String content, Context context ) {
        try {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(Intent.createChooser(i, chooser));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Abre la aplicación de navegación/mapas del dispositivo con la ubicación que se le facilita
     * @param latitude latitud del recurso
     * @param longitude longitud del recurso
     * @param context Contexto donde se ejecuta la acción
     * @return Booleano indicando si la operación ha tenido éxito
     */
    public static boolean openNavigationTo(double latitude, double longitude, Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude + ""));
            context.startActivity(intent);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
