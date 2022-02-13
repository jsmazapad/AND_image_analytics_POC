package com.jsm.and_image_analytics_poc.core.ui;

/**
 * Interfaz para pasar callback de acción de reintento en métodos de procesamiento de errores
 */
public interface RetryAction {
    void retryAction();
}
