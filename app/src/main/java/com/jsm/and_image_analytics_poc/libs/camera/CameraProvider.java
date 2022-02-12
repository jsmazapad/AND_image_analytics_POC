package com.jsm.and_image_analytics_poc.libs.camera;


import androidx.fragment.app.Fragment;

import com.jsm.and_image_analytics_poc.ui.AutoFitTextureView;
import java.io.File;

public class CameraProvider {

    /**
     * Callback para pasar la imagen una vez recibida correctamente
     */
    ImageReceivedCallback imageReceivedCallback;

    Camera2Handler cameraHandler;



    public CameraProvider(AutoFitTextureView mTextureView, Fragment fragment, ImageReceivedCallback imageReceivedCallback) {

        cameraHandler = new Camera2Handler(mTextureView, fragment, imageReceivedCallback);

        this.imageReceivedCallback = imageReceivedCallback;
        
    }

    public void initCamera(AutoFitTextureView mTextureView){
        cameraHandler.initCamera(mTextureView);
    }

    public void closeCamera(){
        cameraHandler.closeCamera();
        cameraHandler.stopBackgroundThread();
    }

    public void takePicture(File f){
        cameraHandler.takePicture(f);
    }



}
