package com.jsm.and_image_analytics_poc.ui.camera;


import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.util.Log;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import com.jsm.and_image_analytics_poc.R;
import com.jsm.and_image_analytics_poc.Repository;
import com.jsm.and_image_analytics_poc.core.data.repositories.responses.ElementResponse;
import com.jsm.and_image_analytics_poc.core.ui.base.BaseFragment;
import com.jsm.and_image_analytics_poc.databinding.CameraFragmentBinding;
import com.jsm.and_image_analytics_poc.model.ImageEmbeddingVector;

public class CameraFragment extends BaseFragment<CameraFragmentBinding, CameraViewModel> {

    PreviewView previewView;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }



    @Override
    protected CameraViewModel getViewModel() {
        CameraViewModel viewModel = new ViewModelProvider(this).get(CameraViewModel.class);
        viewModel.getServerResponse().observe(this, response ->{
            processEmbeddedServerResponse(viewModel, response);
        });


        return viewModel;
    }

    private void processEmbeddedServerResponse(CameraViewModel viewModel, ElementResponse<ImageEmbeddingVector> response) {
        if (response != null){
            if (response.getError() != null){
                Log.e("ERROR", response.getError().getLocalizedMessage());
            }else{
                Log.d("RESPONSE", response.getResultElement().toString());
                Repository.insertImageEmbedding(viewModel.getActualFile(), response.getResultElement());
            }
        }
    }

    @Override
    protected CameraFragmentBinding getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        CameraFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.camera_fragment, container, false);
        return binding;
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        viewModel.initCameraProvider(getContext(),binding.getRoot().findViewById(R.id.cameraPreview));

    }




}