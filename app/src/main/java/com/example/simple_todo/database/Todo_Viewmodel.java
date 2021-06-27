package com.example.simple_todo.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Todo_Viewmodel extends AndroidViewModel {

    private final Todo_Repo todo_repo;
    private final LiveData<List<Entity_Todo>> alltodo;


    public Todo_Viewmodel(@NonNull @NotNull Application application) {
        super(application);
        todo_repo = new Todo_Repo(application);
        alltodo = todo_repo.getall();
    }

    public void insert(Entity_Todo entity_todo) {
        todo_repo.insert(entity_todo);
    }

    public void deleteall() {
        todo_repo.deleteall();
    }

    public void update(Entity_Todo entity_todo) {
        todo_repo.update(entity_todo);
    }

    public void delete(Entity_Todo entity_todo) {
        todo_repo.deleteitem(entity_todo);
    }

    public LiveData<List<Entity_Todo>> getAlltodo() {
        return alltodo;
    }


}
