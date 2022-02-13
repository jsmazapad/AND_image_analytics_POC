package com.jsm.and_image_analytics_poc.libs.camera;

import java.io.File;

public interface ImageReceivedCallback {

    public void onImageReceived(File imageFile);
    public void onErrorReceived(Exception error);


}
