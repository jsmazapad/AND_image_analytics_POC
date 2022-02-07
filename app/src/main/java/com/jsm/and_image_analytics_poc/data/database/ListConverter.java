package com.jsm.and_image_analytics_poc.data.database;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Converter para insertar listas de Strings,
 * Room no las entiende y hay que proporcionarle conversores de datos Custom
 */
public class ListConverter {

    private final static String DELIMITER = "@###@";

    @TypeConverter
    public static List<Double> toList(String stringedList) {
        List<Double> list = new ArrayList<>();


        if(stringedList != null) {
            String [] values = stringedList.split(DELIMITER);
            Double [] parsed = new Double[values.length];
            try {
                for (int i = 0; i < values.length; i++) parsed[i] = Double.parseDouble(values[i]);
                list = Arrays.asList(parsed);
            }catch (NumberFormatException e){
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
        return list;
    }

    @TypeConverter
    public static String toString(List<Double> list) {
        String stringedList = "";
        if(list != null) {
            for(Double item:list){
                stringedList+= item + DELIMITER;
            }
        }
        return stringedList;
    }
}
