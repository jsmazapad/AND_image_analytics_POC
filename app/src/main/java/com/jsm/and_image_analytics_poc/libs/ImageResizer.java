package com.jsm.and_image_analytics_poc.libs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;

public class ImageResizer {

    public static void resizeImageFile(File originalImageFile, File resizedImageFile, int maxSize) {
        Bitmap bitmap = BitmapFactory.decodeFile(originalImageFile.getAbsolutePath());
        Bitmap resizedBitmap;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            resizedBitmap = Bitmap.createScaledBitmap(bitmap, maxSize, maxSize * bitmap.getHeight() / bitmap.getWidth(), false);
        } else {
            resizedBitmap = Bitmap.createScaledBitmap(bitmap, maxSize * bitmap.getWidth() / bitmap.getHeight(), maxSize, false);
        }

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(resizedImageFile);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            bitmap.recycle();
            resizedBitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
