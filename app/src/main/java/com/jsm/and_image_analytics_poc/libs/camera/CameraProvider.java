package com.jsm.and_image_analytics_poc.libs.camera;


import android.content.Context;

import androidx.camera.core.ImageCapture;
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

    public void changeFlash(Context context, FlashModes mode){
        cameraHandler.changeFlash(context, mode);
    }

    public FlashModes getFlashMode(){
        return cameraHandler.getFlashMode();
    }

    public void switchCamera(Context context){
        cameraHandler.switchLensFacing(context);
    }

    public void takePicture(File f){
        cameraHandler.takePicture(f);
    }


    public enum FlashModes {
        OFF(ImageCapture.FLASH_MODE_OFF),
        AUTO(ImageCapture.FLASH_MODE_AUTO),
        ON(ImageCapture.FLASH_MODE_ON);

        public final int imageCaptureMode;


        FlashModes(int imageCaptureMode) {
            this.imageCaptureMode = imageCaptureMode;
        }

        public static FlashModes chooseByImageCaptureMode(int imageCaptureMode){
            FlashModes returnValue = null;
            for (FlashModes mode: FlashModes.values()) {
                if (mode.imageCaptureMode == imageCaptureMode)
                {
                    returnValue = mode;
                }

            }
            return returnValue;
        }



    }
}
