package com.example.simple_todo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Todo_Dao {


    @Update
    void update (Entity_Todo entity_todo);

    @Delete
    void delete (Entity_Todo entity_todo);

    @Insert
    void insert (Entity_Todo entity_todo);

    @Query("DELETE FROM todo_table")
    void deleteall();

    @Query("SELECT * FROM todo_table")
    LiveData<List<Entity_Todo>> getall();


}