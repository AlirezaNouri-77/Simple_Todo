package com.example.simple_todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simple_todo.dao.Todo_Dao;
import com.example.simple_todo.model.Todo_Model;
import com.example.simple_todo.repo.Todo_Repository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Todo_Viewmodel extends AndroidViewModel {

    private final Todo_Repository todo_repository;
    Todo_Dao todo_dao;
    private final LiveData<List<Todo_Model>> alltodo;

    public Todo_Viewmodel(@NonNull @NotNull Application application) {
        super(application);
        todo_repository = new Todo_Repository(application);
        alltodo = todo_repository.getall();
    }

    public void insert(Todo_Model todoModel) {
        todo_repository.insert(todoModel);
    }

    public void deleteall(String text) {
        todo_repository.delete(text);
    }

    public void update(Todo_Model todoModel) {
        todo_repository.update(todoModel);
    }

    public void delete(Todo_Model todoModel) {
        todo_repository.deleteitem(todoModel);
    }

    public LiveData<List<Todo_Model>> searchitem (String text) {
        return todo_repository.searchitem(text);
    }

    public LiveData<List<Todo_Model>> getAlltodo() {
        return alltodo;
    }

}
