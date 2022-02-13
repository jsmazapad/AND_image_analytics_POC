package com.jsm.and_image_analytics_poc.core.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.Nullable;

import com.jsm.and_image_analytics_poc.R;


/**
 * Clase de utilidad para generar Dialogs (Ventanas modales)
 */
public class ModalMessage {

    /**
     * Crea un diálogo, si no se le pasa buttonOkLabel pone la etiqueta por defecto del botón Ok, si no se le pasa buttonCancelLabel
     * no genera el botón de cancelar
     *
     * @param context
     * @param title
     * @param message
     * @param buttonOkLabel
     * @param buttonOkListener
     * @param buttonCancelLabel
     * @param buttonCancelListener
     *
     */

    public static void showModalMessage(Context context, String title, String message, @Nullable String buttonOkLabel, @Nullable DialogInterface.OnClickListener buttonOkListener, @Nullable String buttonCancelLabel, @Nullable DialogInterface.OnClickListener buttonCancelListener) {

        String okLabel = context.getString(R.string.default_modal_okButton);
        if (buttonOkLabel != null) {
            okLabel = buttonOkLabel;
        }

        //Descomentar para customizar título
//        TextView textView = new TextView(context);
//        textView.setPadding(80,20,80,20);
//        textView.setTextSize(21.0f);
//        textView.setText(title);
        //Título custom para que pueda ocupar mas de dos líneas
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                //.setCustomTitle(textView)
                .setTitle(title)
                .setMessage(message).setPositiveButton(okLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(buttonOkListener != null) {
                    buttonOkListener.onClick(dialog, which);

                }

            }
        });
        if (buttonCancelLabel != null || buttonCancelListener != null) {
            if (buttonCancelLabel == null) {
                buttonCancelLabel = context.getString(R.string.default_modal_cancelButton);
            }

            dialogBuilder.setNegativeButton(buttonCancelLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(buttonCancelListener != null) {
                            buttonCancelListener.onClick(dialog, which);

                        }

                    }
                });

            dialogBuilder.setCancelable(false);


//            if (buttonCancelListener != null) {
//                dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        buttonCancelListener.onClick(dialog, 0);
//                    }
//                });
//            }


        }

        dialogBuilder.setCancelable(false);


        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    /**
     * Crea un diálogo de error, si no se le pasa buttonOkLabel pone la etiqueta por defecto del botón Ok, si no se le pasa buttonCancelLabel
     * no genera el botón de cancelar
     * @param context
     * @param message
     * @param buttonOkLabel
     * @param buttonOkListener
     * @param buttonCancelLabel
     * @param buttonCancelListener
     */
    public static void showError(Context context, String message, @Nullable String buttonOkLabel, @Nullable DialogInterface.OnClickListener buttonOkListener, @Nullable String buttonCancelLabel, @Nullable DialogInterface.OnClickListener buttonCancelListener) {
        ModalMessage.showModalMessage(context, "Error", message, buttonOkLabel, buttonOkListener, buttonCancelLabel, buttonCancelListener);
    }
}
