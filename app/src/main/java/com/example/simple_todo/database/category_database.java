package com.example.simple_todo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.simple_todo.dao.category_dao;
import com.example.simple_todo.model.category_model;

@Database(entities = category_model.class , version = 1)
public abstract class category_database extends RoomDatabase {

    public abstract category_dao category_dao();
    private static category_database instant;

    public static synchronized category_database getInstance(Context context){
        if (instant == null){
            instant = Room.databaseBuilder(context.getApplicationContext() , category_database.class , "category.db").build();
        }
        return instant;
    }
}
