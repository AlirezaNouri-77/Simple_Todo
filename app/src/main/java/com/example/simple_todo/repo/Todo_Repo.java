package com.example.simple_todo.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.example.simple_todo.dao.Todo_Dao;
import com.example.simple_todo.database.Room_Database;
import com.example.simple_todo.model.Entity_Todo;

import java.util.List;

public class Todo_Repo {

    private final Todo_Dao todo_dao;
  //  private final List<Entity_Todo> alltodo;

    public Todo_Repo(Application application) {
        Room_Database room_database = Room_Database.getInstance(application);
        todo_dao = room_database.todo_dao();
        //alltodo = todo_dao.getall();
    }

    @MainThread
    public LiveData<List<Entity_Todo>> searchitem (String text){
        return todo_dao.search(text);
    }

    @WorkerThread
    public void delete (String text){ todo_dao.deleteall(text);}

    public void insert(Entity_Todo entity_todo) {
        new inserttodo(todo_dao).execute(entity_todo);
    }

    public void update(Entity_Todo entity_todo) {
        new update(todo_dao).execute(entity_todo);
    }

//    public void deleteall() {
//        new deleteall(todo_dao).execute();
//    }

    public void deleteitem(Entity_Todo entity_todo) {
        new deleteitem(todo_dao).execute(entity_todo);
    }

//    public List<Entity_Todo> getall() {
//        return alltodo;
//    }

    private static class inserttodo extends AsyncTask<Entity_Todo, Void, Void> {
        private final Todo_Dao todo_dao;

        private inserttodo(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Entity_Todo... entity_todos) {
            todo_dao.insert(entity_todos[0]);
            return null;
        }
    }

//    private static class deleteall extends AsyncTask<String, Void, Void> {
//        private final Todo_Dao todo_dao;
//
//        private deleteall(Todo_Dao todo_dao) {
//            this.todo_dao = todo_dao;
//        }
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            todo_dao.deleteall(strings[0]);
//            return null;
//        }
//    }

    private static class update extends AsyncTask<Entity_Todo, Void, Void> {
        private final Todo_Dao todo_dao;

        private update(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Entity_Todo... entity_todos) {
            todo_dao.update(entity_todos[0]);
            return null;
        }
    }

    private static class deleteitem extends AsyncTask<Entity_Todo, Void, Void> {
        private final Todo_Dao todo_dao;

        private deleteitem(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Entity_Todo... entity_todos) {
            todo_dao.delete(entity_todos[0]);
            return null;
        }
    }


}
