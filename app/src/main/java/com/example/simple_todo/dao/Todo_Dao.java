package com.example.simple_todo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simple_todo.model.Todo_Model;

import java.util.List;

@Dao
public interface Todo_Dao {

    @Update
    void update (Todo_Model todo_model);

    @Delete
    void delete (Todo_Model todo_model);

    @Insert
    void insert (Todo_Model todo_model);

    @Query("DELETE FROM todo_table WHERE Code = :text")
    void deleteall(String text);

    @Query("SELECT * FROM todo_table ORDER BY isfinish ASC")
    LiveData<List<Todo_Model>> getall();

    @Query("SELECT * FROM todo_table WHERE Code = :text ORDER BY isfinish ASC")
    LiveData<List<Todo_Model>> search(String text);

}