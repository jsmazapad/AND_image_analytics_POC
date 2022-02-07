package com.jsm.and_image_analytics_poc.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jsm.and_image_analytics_poc.model.ImageRegister;

import java.util.List;


/**
 * DAO para realizar operaciones con la BD,
 * a partir de esta interface se autogenera el código necesario
 */
@Dao
public interface ImageRegisterDao {

    /**
     * Selecciona todas las entidades de la BD
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT * FROM "+ ImageRegister.TABLE_NAME)
    List<ImageRegister> getImages();

    /**
     * Selecciona un registro mediante su id (externo)
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM "+ ImageRegister.TABLE_NAME + " WHERE _id" + " = :id LIMIT 1")
    ImageRegister selectById(int id);

    /**
     * Inserta un registro
     * @param beer
     */
    @Insert
    void insert(ImageRegister beer);

    /**
     * Elimina un registro usando su id (externo)
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + ImageRegister.TABLE_NAME + " WHERE _id = :id")
    int deleteById(int id);
}
