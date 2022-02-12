package com.jsm.and_image_analytics_poc.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.jsm.and_image_analytics_poc.data.database.ListConverter;

import java.util.List;

@Entity(tableName = ImageRegister.TABLE_NAME)
public class ImageRegister {

    /** The name of the table. */
    public static final String TABLE_NAME = "imageRegisters";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private int internalId;

    private String imageName;
    @TypeConverters(ListConverter.class)
    private List<Double> embedding;
    private boolean remoteSynced;

    @Ignore
    public ImageRegister(String imageName, List<Double> embedding, boolean remoteSynced) {
        this.imageName = imageName;
        this.embedding = embedding;
        this.remoteSynced = remoteSynced;
    }

    public ImageRegister(int internalId, String imageName, List<Double> embedding, boolean remoteSynced) {
        this.internalId = internalId;
        this.imageName = imageName;
        this.embedding = embedding;
        this.remoteSynced = remoteSynced;
    }

    public int getInternalId() {
        return internalId;
    }

    public void setInternalId(int internalId) {
        this.internalId = internalId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }

    public boolean isRemoteSynced() {
        return remoteSynced;
    }

    public void setRemoteSynced(boolean remoteSynced) {
        this.remoteSynced = remoteSynced;
    }
}
