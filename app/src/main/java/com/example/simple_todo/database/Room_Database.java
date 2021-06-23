package com.example.simple_todo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Entity_Todo.class, version = 1)
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
