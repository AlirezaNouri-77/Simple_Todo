package com.example.simple_todo.dao;

import android.os.Parcelable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simple_todo.model.category_model;

import java.util.List;

@Dao
public interface category_dao {

    @Update
    void update(category_model category_model);


    @Delete
    void delete(category_model category_model);

    @Insert
    void insert(category_model category_model);

    @Query("SELECT * FROM category")
    LiveData<List<category_model>> getall();

}
