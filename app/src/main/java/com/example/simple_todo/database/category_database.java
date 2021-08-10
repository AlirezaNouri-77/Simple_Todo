package com.example.simple_todo.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.simple_todo.dao.Category_Dao;
import com.example.simple_todo.model.Category_Model;
import com.example.simple_todo.repo.Category_Repository;

@Database(entities = Category_Model.class , version = 1)
public abstract class category_database extends RoomDatabase {

    public abstract Category_Dao category_dao();
    private static category_database instant;


    public static synchronized category_database getInstance(Context context){
        if (instant == null){
            instant = Room.databaseBuilder(context.getApplicationContext() , category_database.class , "category.db").addCallback(callback).build();
        }
        return instant;
    }

    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback() {


        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Category_Repository.add_default(instant.category_dao()).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
