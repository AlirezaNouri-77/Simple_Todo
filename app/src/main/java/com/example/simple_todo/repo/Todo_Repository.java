package com.example.simple_todo.repo;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.example.simple_todo.dao.Todo_Dao;
import com.example.simple_todo.database.Room_Database;
import com.example.simple_todo.model.Todo_Model;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Todo_Repository {

    private final Todo_Dao todo_dao;
    private final LiveData<List<Todo_Model>> alltodo;

    public Todo_Repository(Application application) {
        Room_Database room_database = Room_Database.getInstance(application);
        todo_dao = room_database.todo_dao();
        alltodo = todo_dao.getall();
    }

    @MainThread
    public LiveData<List<Todo_Model>> searchitem(String text) {
        return todo_dao.search(text);
    }

    @WorkerThread
    public void delete(String text) {
        todo_dao.deleteall(text);
    }

    public void insert(Todo_Model _todoModel) {
        new inserttodo(todo_dao).execute(_todoModel);
    }

    public void update(Todo_Model _todoModel) {
        new update(todo_dao).execute(_todoModel);
    }

//    public void deleteall() {
//        new deleteall(todo_dao).execute();
//    }

    public void deleteitem(Todo_Model _todoModel) {
        new deleteitem(todo_dao).execute(_todoModel);
    }

    public LiveData<List<Todo_Model>> getall() {
        return alltodo;
    }

    private static class inserttodo extends AsyncTask<Todo_Model, Void, Void> {
        private final Todo_Dao todo_dao;

        private inserttodo(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Todo_Model... _todoModels) {
            todo_dao.insert(_todoModels[0]);
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

    private static class update extends AsyncTask<Todo_Model, Void, Void> {
        private final Todo_Dao todo_dao;

        private update(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Todo_Model... _todoModels) {
            todo_dao.update(_todoModels[0]);
            return null;
        }
    }

    private static class deleteitem extends AsyncTask<Todo_Model, Void, Void> {
        private final Todo_Dao todo_dao;

        private deleteitem(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Todo_Model... _todoModels) {
            todo_dao.delete(_todoModels[0]);
            return null;
        }
    }


}
