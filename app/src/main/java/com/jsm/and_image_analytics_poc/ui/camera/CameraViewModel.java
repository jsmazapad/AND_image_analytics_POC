package com.jsm.and_image_analytics_poc.ui.camera;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.jsm.and_image_analytics_poc.Repository;
import com.jsm.and_image_analytics_poc.core.data.repositories.responses.ElementResponse;
import com.jsm.and_image_analytics_poc.core.ui.base.BaseViewModel;
import com.jsm.and_image_analytics_poc.libs.ImageResizer;
import com.jsm.and_image_analytics_poc.libs.camera.CameraProvider;
import com.jsm.and_image_analytics_poc.libs.camera.ImageReceivedCallback;
import com.jsm.and_image_analytics_poc.model.ImageEmbeddingVector;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CameraViewModel extends BaseViewModel implements ImageReceivedCallback {

    private MutableLiveData<ElementResponse<ImageEmbeddingVector>> serverResponse = new MutableLiveData<>();
    private File actualFile;
    Timer timer = new Timer();
    boolean isActive = false;


    public MutableLiveData<ElementResponse<ImageEmbeddingVector>> getServerResponse() {
        return serverResponse;
    }
    public File getActualFile() {
        return actualFile;
    }

    public CameraViewModel(@NonNull Application application) {
        super(application);
    }

    public void initCameraProvider(Context context, CameraPermissionsInterface cameraPermission, PreviewView previewView){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            cameraPermission.requestPermissions();
        }
        CameraProvider.getInstance().initCamera(context, previewView, this);
    }



    @Override
    public void onImageReceived(File imageFile) {
        File targetFile = new File(getApplication().getExternalFilesDir(null), "resized"+imageFile.getName());
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageResizer.resizeImageFile(imageFile, targetFile, 299);
        actualFile = targetFile;
        Repository.getEmbedding(serverResponse, targetFile);
    }

    @Override
    public void onErrorReceived(Exception error) {

    }


    public void takePicture(Context context){

       if (!isActive) {
           timer.scheduleAtFixedRate(new TimerTask() {
               @Override
               public void run() {
                   Date date = new Date();
                   File mFile = new File(context.getExternalFilesDir(null), date.toString()+"pic.jpg");

                   CameraProvider.getInstance().takePicture(mFile);
               }
           }, 0, 1000);
       }else{
           timer.cancel();
       }


    }

    public void selectCamera(Context context){
        CameraProvider.getInstance().switchCamera(context);
    }


}