package com.example.simple_todo.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.simple_todo.dao.Todo_Dao;
import com.example.simple_todo.database.Room_Database;
import com.example.simple_todo.model.Todo_Model;

import java.util.List;

public class Todo_Repository {

    private final Todo_Dao todo_dao;
    private final LiveData<List<Todo_Model>> alltodo;

    public Todo_Repository(Application application) {
        Room_Database room_database = Room_Database.getInstance(application);
        todo_dao = room_database.todo_dao();
        alltodo = todo_dao.getall();
    }

    @WorkerThread
    public LiveData<List<Todo_Model>> get_all_todo_bycategory( String text , String searchtext ) {
        return todo_dao.get_all_todo_bycategory(text , searchtext);
    }

    public void delete_all_bycode (String Code){
        new delete_all_bycode(todo_dao , Code).execute();
    }

    public void insert(Todo_Model todo_model) {
        new insert_new_todo(todo_dao).execute(todo_model);
    }

    public void update(Todo_Model todo_model) {
        new update(todo_dao).execute(todo_model);
    }

    public void deleteall() {
        new delete_all(todo_dao).execute();
    }

    public void deleteitem(Todo_Model _todoModel) {
        new delete_an_item(todo_dao).execute(_todoModel);
    }

    public LiveData<List<Todo_Model>> getall() {
        return alltodo;
    }

    private static class delete_all_bycode extends AsyncTask<Void, Void, Void> {
        private final Todo_Dao todo_dao;
        private final String Code;

        private delete_all_bycode(Todo_Dao todo_dao , String Code) {
            this.todo_dao = todo_dao;
            this.Code = Code;
        }

        @Override
        protected Void doInBackground(Void ... voids) {
            todo_dao.deleteanitem(Code);
            return null;
        }
    }

    private static class insert_new_todo extends AsyncTask<Todo_Model, Void, Void> {
        private final Todo_Dao todo_dao;

        private insert_new_todo(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Todo_Model ... todo_models) {
            todo_dao.insert(todo_models[0]);
            return null;
        }
    }

    private static class delete_all extends AsyncTask<Void , Void , Void >{
        private final Todo_Dao todo_dao;

        private delete_all(Todo_Dao todo_dao){
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todo_dao.deletealltodo();
            return null;
        }
    }

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

    private static class delete_an_item extends AsyncTask<Todo_Model, Void, Void> {
        private final Todo_Dao todo_dao;

        private delete_an_item(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Todo_Model... _todoModels) {
            todo_dao.delete(_todoModels[0]);
            return null;
        }
    }

}
