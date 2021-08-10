package com.example.simple_todo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simple_todo.model.Category_Model;

import java.util.List;

@Dao
public interface Category_Dao {

    @Update
    void update(Category_Model category_model);

    @Delete
    void delete(Category_Model category_model);

    @Insert
    void insert(Category_Model category_model);


    @Query("SELECT * FROM category_table")
    LiveData<List<Category_Model>> getall();

    @Query("UPDATE category_table SET quntity=quntity-1 WHERE category =:newcategory ")
            void update_quntinty( String newcategory);

}
