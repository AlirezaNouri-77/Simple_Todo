package com.example.simple_todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.simple_todo.dao.category_dao;
import com.example.simple_todo.model.category_model;
import com.example.simple_todo.repo.category_repo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class category_viewmodel extends AndroidViewModel {

    private category_repo category_repo;
    private category_dao category_dao;
    private LiveData<List<category_model>> getallcategory;

    public category_viewmodel(@NonNull @NotNull Application application) {
        super(application);
        category_repo  = new category_repo(application);
        getallcategory = category_repo.getallcategory();
    }

    public LiveData<List<category_model>> getallcategory() {
        return getallcategory;
    }
    public void insert (category_model category_model){
        category_repo.insert(category_model);
    }
    public void update (category_model category_model){
        category_repo.update(category_model);
    }
}
