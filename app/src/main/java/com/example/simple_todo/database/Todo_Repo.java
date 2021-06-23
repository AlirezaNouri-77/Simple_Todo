package com.example.simple_todo.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Todo_Repo {
    private Todo_Dao todo_dao;
    private LiveData<List<Entity_Todo>> alltodo;

    public Todo_Repo (Application application){
        Room_Database room_database = Room_Database.getInstance(application);
        todo_dao = room_database.todo_dao();
        alltodo = todo_dao.getall();
    }

    public void insert (Entity_Todo entity_todo){

    }

    public void update (Entity_Todo entity_todo){

    }

    public void deleteall (){

    }

    public LiveData<List<Entity_Todo>> getall (){
        return alltodo;
    }

}
