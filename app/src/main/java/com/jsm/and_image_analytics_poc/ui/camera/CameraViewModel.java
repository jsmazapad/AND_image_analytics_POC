package com.jsm.and_image_analytics_poc.ui.camera;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
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

public class CameraViewModel extends BaseViewModel implements ImageReceivedCallback {

    private MutableLiveData<ElementResponse<ImageEmbeddingVector>> serverResponse = new MutableLiveData<>();
    private File actualFile;


    public MutableLiveData<ElementResponse<ImageEmbeddingVector>> getServerResponse() {
        return serverResponse;
    }
    public File getActualFile() {
        return actualFile;
    }

    public CameraViewModel(@NonNull Application application) {
        super(application);
    }

    public void initCameraProvider(Context context, PreviewView previewView){
        CameraProvider.getInstance().initCamera(context, previewView, this);
    }



    @Override
    public void onImageReceived(File imageFile) {
        File targetFile = new File(getApplication().getExternalFilesDir(null), "pic_resized.jpg");
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(imageFile != null) {
            ImageResizer.resizeImageFile(imageFile, targetFile, 299);
            actualFile = targetFile;
            Repository.getEmbedding(serverResponse, targetFile);
        }
    }

    @Override
    public void onErrorReceived(Exception error) {

    }


    public void takePicture(Context context){
        File mFile = new File(context.getExternalFilesDir(null), "pic.jpg");

        CameraProvider.getInstance().takePicture(mFile);

    }


}