package com.example.simple_todo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.simple_todo.dao.Todo_Dao;
import com.example.simple_todo.model.Todo_Model;

@Database(entities = Todo_Model.class, version = 2)
public abstract class Room_Database extends RoomDatabase {

    public abstract Todo_Dao todo_dao();
    private static Room_Database instance;

    public static synchronized Room_Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Room_Database.class, "todo_db").build();

        }
        return instance;
    }
}


