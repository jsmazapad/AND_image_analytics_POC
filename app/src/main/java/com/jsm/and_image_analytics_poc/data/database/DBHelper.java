package com.jsm.and_image_analytics_poc.data.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.jsm.and_image_analytics_poc.model.ImageRegister;

import java.util.List;


/**
 * Helper para manipular la BD
 * nos abstrae de la tecnología empleada para la capa de datos
 */
public class DBHelper {

    private static DBHelper sharedDBPool;
    private static String DB_NAME = "db_experiments";
    private static AppDatabase appDatabase;
    private DBHelper() {}

    public static void initialize(Context context) {
        sharedDBPool= new DBHelper();
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    /*
    IMAGE REGISTERS
     */


    public static void insertImageRegister(final ImageRegister imageRegister, MutableLiveData<Boolean> ended) {
            //TODO Migrar a Executors + handlers
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    appDatabase.imageDao().insert(imageRegister);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    ended.setValue(true);
                }
            }.execute();

    }

    public static List<ImageRegister> getImages() {
        return appDatabase.imageDao().getImages();
    }


    public static ImageRegister getImageById(ImageRegister beer) {
        return appDatabase.imageDao().selectById(beer.getInternalId());
    }

    public static void deleteImagesById(ImageRegister beer) {
        appDatabase.imageDao().deleteById(beer.getInternalId());
    }



}
