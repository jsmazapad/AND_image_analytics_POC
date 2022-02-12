package com.jsm.and_image_analytics_poc.ui;

/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCaptureSession;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jsm.and_image_analytics_poc.R;
import com.jsm.and_image_analytics_poc.Repository;
import com.jsm.and_image_analytics_poc.config.ConfigConstants;
import com.jsm.and_image_analytics_poc.core.data.repositories.responses.ElementResponse;
import com.jsm.and_image_analytics_poc.libs.ImageResizer;
import com.jsm.and_image_analytics_poc.libs.camera.CameraProvider;
import com.jsm.and_image_analytics_poc.libs.camera.ImageReceivedCallback;
import com.jsm.and_image_analytics_poc.model.ImageEmbeddingVector;

import java.io.File;
import java.io.IOException;

public class Camera2BasicFragment extends Fragment
        implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback, ImageReceivedCallback {

    MutableLiveData<ElementResponse<ImageEmbeddingVector>> serverResponse = new MutableLiveData<>();
    CameraProvider cameraProvider;
    private AutoFitTextureView mTextureView;
    private File actualFile;



    /**
     * Shows a {@link Toast} on the UI thread.
     *
     * @param text The message to show
     */
    private void showToast(final String text) {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    public static Camera2BasicFragment newInstance() {
        return new Camera2BasicFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        serverResponse.observe(this, response ->{
            if (response != null){
                if (response.getError() != null){
                    Log.e("ERROR", response.getError().getLocalizedMessage());
                }else{
                    Log.d("RESPONSE", response.getResultElement().toString());
                    Repository.insertImageEmbedding(actualFile, response.getResultElement());
                }
            }
        });

        return inflater.inflate(R.layout.fragment_camera2_basic, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        view.findViewById(R.id.picture).setOnClickListener(this);
        view.findViewById(R.id.info).setOnClickListener(this);
        mTextureView = (AutoFitTextureView) view.findViewById(R.id.texture);
        cameraProvider = new CameraProvider(mTextureView, this, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        /**
         * Código para pruebas de carga rápidas de servidor
         */
//        try
//        {
//            File f=new File(getActivity().getExternalFilesDir(null), "Cat.jpg");
//            InputStream inputStream = getResources().openRawResource(R.raw.cat_4001);
//            OutputStream out=new FileOutputStream(f);
//            byte buf[]=new byte[1024];
//            int len;
//            while((len=inputStream.read(buf))>0)
//                out.write(buf,0,len);
//            out.close();
//            inputStream.close();
//            Repository.getEmbedding(serverResponse, f);
//        }
//        catch (IOException e){e.printStackTrace();}

    }

    @Override
    public void onResume() {
        super.onResume();
        cameraProvider.initCamera(mTextureView);
    }

    @Override
    public void onPause() {
        cameraProvider.closeCamera();

        super.onPause();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == ConfigConstants.REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ErrorDialog.newInstance(getString(R.string.request_permission))
                        .show(getChildFragmentManager(), ConfigConstants.FRAGMENT_DIALOG);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.picture: {
                File mFile = new File(getActivity().getExternalFilesDir(null), "pic.jpg");
                cameraProvider.takePicture(mFile);
                break;
            }
            case R.id.info: {
                Activity activity = getActivity();
                if (null != activity) {
                    new AlertDialog.Builder(activity)
                            .setMessage(R.string.intro_message)
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
                break;
            }
        }
    }

    @Override
    public void onImageReceived(File imageFile) {
        File targetFile = new File(getActivity().getExternalFilesDir(null), "pic_resized.jpg");
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(imageFile != null && targetFile != null) {
            ImageResizer.resizeImageFile(imageFile, targetFile, 299);
            actualFile = targetFile;
            Repository.getEmbedding(serverResponse, targetFile);
        }
    }

    @Override
    public void onErrorReceived(String error) {

    }


    /**
     * Shows an error message dialog.
     */
    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity.finish();
                        }
                    })
                    .create();
        }

    }

    /**
     * Shows OK/Cancel confirmation dialog about camera permission.
     */
    public static class ConfirmationDialog extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            parent.requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    ConfigConstants.REQUEST_CAMERA_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Activity activity = parent.getActivity();
                                    if (activity != null) {
                                        activity.finish();
                                    }
                                }
                            })
                    .create();
        }
    }



}

