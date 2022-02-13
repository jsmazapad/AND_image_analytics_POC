package com.jsm.and_image_analytics_poc.core.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.jsm.and_image_analytics_poc.BR;
import com.jsm.and_image_analytics_poc.core.utils.ModalMessage;


/**
 * Clase base para extender y crear fragmentos simples con arquitectura MVVM que incluyan Databinding entre ViewModel y Layout
 * Incluye mecanismo básico para mostrar errores
 * @param <BT> Data binding asociado a la vista
 * @param <VM> ViewModel asociado a este fragmento, extiende de la clase Base propia para ViewModels
 */
public abstract class BaseFragment<BT extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    protected BT binding;
    protected VM viewModel;

    /**
     * {@inheritdoc}
     * Incluye asociación del binding, el ViewModel y el fragmento
     * El binding sólo se asocia una vez para evitar problemas de duplicidades de instancias
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (binding == null) {
            viewModel = getViewModel();
            binding = getDataBinding(inflater, container);
            //Equivalente a binding.setViewModel(viewModel) genérico (viewModel es generado como variable)
            //Para que funcione, hay que tener en el proyecto al menos un layout con una variable viewModel y que se haya generado el código
            //Si da problemas, comentar, hacer un make project para que se genere el código y descomentar
            binding.setVariable(BR.viewModel, viewModel);

            binding.setLifecycleOwner(this);
            executeExtraActionsInsideBindingInit();
        }

        return binding.getRoot();
    }

    /**
     * Ejecuta acciones extra cuando está inicializando el binding
     * Si no se anula se suscribe para mostrar errores con un modal de un sólo botón y sin acciones
     * NOTA: Este proceso no ocurre siempre que se llama a onCreateView
     */
    public void executeExtraActionsInsideBindingInit(){
        viewModel.getError().observe(getViewLifecycleOwner(), error ->{
            if(error != null)
            {
                ModalMessage.showError(getContext(), error.getMessage(), null, null, null, null );
            }
        });
    }

    /**
     * Devuelve una instancia del ViewModel asociado al Fragment para usarlo en onCreateView y vincularla al binding
     * @return ViewModel asociado al Fragment
     */
    protected abstract VM getViewModel();

    /**
     * Devuelve una instancia del DataBinding asociado al Fragment para usarlo en onCreateView y vincularla al ViewModel y el Fragment
     * @return DataBinding asociado al Fragment
     */
    protected abstract BT getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container);


}
