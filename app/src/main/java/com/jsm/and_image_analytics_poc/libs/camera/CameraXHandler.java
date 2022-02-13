package com.jsm.and_image_analytics_poc.libs.camera;

import android.content.Context;
import android.util.Log;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraXHandler {

    /**
     * Use case to take a picture
     */
    private ImageCapture imageCapture;
    /**
     * Callback to pass the image result
     */
    ImageReceivedCallback imageReceivedCallback;

    public void initCamera(Context context, PreviewView previewView, ImageReceivedCallback imageReceivedCallback){


        this.imageReceivedCallback = imageReceivedCallback;


        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(context);

        cameraProviderFuture.addListener(() -> {
            try {
                // Camera provider is now guaranteed to be available
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Set up the view finder use case to display camera preview
                Preview preview = new Preview.Builder().build();

                // Set up the capture use case to allow users to take photos
                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                int lensFacing = CameraSelector.LENS_FACING_BACK;
                // Choose the camera by requiring a lens facing
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(lensFacing)
                        .build();

                // Unbind use cases before rebinding
                cameraProvider.unbindAll();

                // Attach use cases to the camera with the same lifecycle owner
                Camera camera = cameraProvider.bindToLifecycle(
                        ((LifecycleOwner)context),
                        cameraSelector,
                        preview,
                        imageCapture);

                // Connect the preview use case to the previewView
                preview.setSurfaceProvider(
                        previewView.getSurfaceProvider());
            } catch (InterruptedException | ExecutionException e) {
                // Currently no exceptions thrown. cameraProviderFuture.get()
                // shouldn't block since the listener is being called, so no need to
                // handle InterruptedException.
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void takePicture(File mFile){
        Executor cameraExecutor =  Executors.newSingleThreadExecutor();
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(mFile).build();
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                        // insert your code here.
                        Log.d("ImageSaved",outputFileResults.getSavedUri().getPath());
                        imageReceivedCallback.onImageReceived(new File(outputFileResults.getSavedUri().getPath()));
                    }
                    @Override
                    public void onError(ImageCaptureException error) {
                        // insert your code here.
                        error.printStackTrace();
                        imageReceivedCallback.onErrorReceived(error);
                    }
                }
        );
    }
}
