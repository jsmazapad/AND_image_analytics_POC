package com.jsm.and_image_analytics_poc.libs.camera;


import android.content.Context;

import androidx.camera.view.PreviewView;

import java.io.File;

public class CameraProvider {


    CameraXHandler cameraHandler;

    private static CameraProvider INSTANCE = null;

    // other instance variables can be here

    private CameraProvider() {
        cameraHandler = new CameraXHandler();
    };

    public static synchronized CameraProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CameraProvider();
        }
        return(INSTANCE);
    }


    public void initCamera(Context context, PreviewView previewView, ImageReceivedCallback imageReceivedCallback){
        cameraHandler.initCamera(context, previewView, imageReceivedCallback);
    }

    public void closeCamera(){

    }

    public void switchCamera(Context context){
        cameraHandler.switchLensFacing(context);
    }

    public void takePicture(File f){
        cameraHandler.takePicture(f);
    }



}
