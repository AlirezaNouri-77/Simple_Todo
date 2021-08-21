package com.example.simple_todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simple_todo.dao.Category_Dao;
import com.example.simple_todo.model.Category_Model;
import com.example.simple_todo.repo.Category_Repository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Category_Viewmodel extends AndroidViewModel {

    private final Category_Repository Category_Repository;
    private final LiveData<List<Category_Model>> getallcategory;

    public Category_Viewmodel(@NonNull @NotNull Application application) {
        super(application);
        Category_Repository = new Category_Repository(application);
        getallcategory = Category_Repository.getallcategory();
    }

    public LiveData<List<Category_Model>> getallcategory() {
        return getallcategory;
    }

    public void insert (Category_Model category_model){
        Category_Repository.insert(category_model);
    }

    public void update (Category_Model category_model){
        Category_Repository.update(category_model);
    }

    public void delete (Category_Model category_model){
        Category_Repository.delete(category_model);
    }

}
