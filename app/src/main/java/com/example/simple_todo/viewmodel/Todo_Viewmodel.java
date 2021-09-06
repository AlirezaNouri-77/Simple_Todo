package com.example.simple_todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.simple_todo.model.Todo_Model;
import com.example.simple_todo.repo.Todo_Repository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Todo_Viewmodel extends AndroidViewModel {

    private final Todo_Repository todo_repository;
   // private final LiveData<List<Todo_Model>> alltodo;

    MutableLiveData<String> search = new MutableLiveData<>("");


    public Todo_Viewmodel(@NonNull @NotNull Application application) {
        super(application);
        todo_repository = new Todo_Repository(application);
       // alltodo = todo_repository.getall();
    }

    public void deleteall() {
        todo_repository.deleteall();
    }

    public void insert(Todo_Model todoModel) {
        todo_repository.insert(todoModel);
    }

    public void update(Todo_Model todoModel) {
        todo_repository.update(todoModel);
    }

    public void delete(Todo_Model todoModel) {
        todo_repository.deleteitem(todoModel);
    }

    public LiveData<List<Todo_Model>> searchitem(String text, String searchview) {

        if (searchview.equals("")){
            search.setValue("");
        }else {
            search.setValue(searchview);
        }

     return Transformations.switchMap(search, String  -> todo_repository.get_all_todo_bycategory(text , search.getValue()));

    }

    // get all todos for all category
    public LiveData<List<Todo_Model>> getAlltodo(String newsearch) {
        return Transformations.switchMap(search , String -> todo_repository.getall(newsearch));
    }

}
