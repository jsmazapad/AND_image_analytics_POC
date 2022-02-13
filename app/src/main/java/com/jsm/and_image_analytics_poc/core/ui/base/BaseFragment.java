package com.jsm.and_image_analytics_poc.core.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.jsm.and_image_analytics_poc.BR;
import com.jsm.and_image_analytics_poc.core.exceptions.BaseException;
import com.jsm.and_image_analytics_poc.core.ui.BaseUIErrorHandler;
import com.jsm.and_image_analytics_poc.core.ui.RetryAction;
import com.jsm.and_image_analytics_poc.core.ui.SetShowDialogFalse;
import com.jsm.and_image_analytics_poc.core.ui.UIErrorHandler;
import com.jsm.and_image_analytics_poc.core.utils.ModalMessage;


/**
 * Clase base para extender y crear fragmentos simples con arquitectura MVVM que incluyan Databinding entre ViewModel y Layout
 * Incluye mecanismo básico para mostrar errores
 * @param <BT> Data binding asociado a la vista
 * @param <VM> ViewModel asociado a este fragmento, extiende de la clase Base propia para ViewModels
 */
public abstract class BaseFragment<BT extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements RetryAction, SetShowDialogFalse {

    protected BT binding;
    protected VM viewModel;
    /**
     * Manejador de errores, sirve para realizar las acciones adecuadas a cada uno de ellos
     */

    protected UIErrorHandler errorHandler = new BaseUIErrorHandler();

    /**
     * Flag utilizado para asegurar en procesos complejos con programación reactiva que sólo se muestra un diálogo de error a la vez
     */
    private boolean showingDialog = false;

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

    /**
     * Maneja los errores
     * @param exception Excepción con la información del error
     * @param context Contexto donde se procesará el error
     */
    public void handleError(BaseException exception, Context context, RetryAction retryAction){
        errorHandler.handleError(exception, context, retryAction, this);
    }

    /**
     * Reintenta la operación tras un error
     */
    public void retryAction(){
        setShowDialogFalse();
    }

    /**
     * Flag para indicar si se está mostrando actualmente un dialog
     * @return booleano indicado si se está mostrando el diálogo
     */
    private boolean isShowingDialog() {
        return showingDialog;
    }

    private void setShowingDialog(boolean showingDialog) {
        this.showingDialog = showingDialog;
    }

    /**
     * Setea la opción de mostrar diálogo a false para controlar que no se muestre mas de un diálogo a la vez
     */
    public void setShowDialogFalse() {
        setShowingDialog(false);
    }


}
