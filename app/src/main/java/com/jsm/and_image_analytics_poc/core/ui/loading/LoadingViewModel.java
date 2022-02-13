package com.jsm.and_image_analytics_poc.core.ui.loading;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jsm.and_image_analytics_poc.core.ui.base.BaseViewModel;


/**
 * Clase base para viewModel que provee de la funcionalidad de carga
 */
public abstract class LoadingViewModel extends BaseViewModel {

    protected MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LoadingViewModel(Application app) {
        super(app);
    }


}
