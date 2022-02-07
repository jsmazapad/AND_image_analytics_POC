package com.jsm.and_image_analytics_poc.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jsm.and_image_analytics_poc.model.ImageRegister;


/**
 * Clase que representa a la base de datos
 */
@Database(entities = {ImageRegister.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Obtiene un DAO de la entidad Beer para realizar operaciones con la BD
     * @return
     */
    public abstract ImageRegisterDao imageDao();

}