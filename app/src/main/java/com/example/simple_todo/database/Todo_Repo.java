package com.example.simple_todo.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Todo_Repo {
    private Todo_Dao todo_dao;
    private LiveData<List<Entity_Todo>> alltodo;

    public Todo_Repo(Application application) {
        Room_Database room_database = Room_Database.getInstance(application);
        todo_dao = room_database.todo_dao();
        alltodo = todo_dao.getall();
    }

    public void insert(Entity_Todo entity_todo) {
        new inserttodo(todo_dao).execute(entity_todo);
    }

    public void update(Entity_Todo entity_todo) {

    }

    public void deleteall() {
        new deleteall(todo_dao).execute();
    }

    public LiveData<List<Entity_Todo>> getall() {
        return alltodo;
    }

    private static class inserttodo extends AsyncTask<Entity_Todo, Void, Void> {
        private Todo_Dao todo_dao;

        private inserttodo(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Entity_Todo... entity_todos) {
            todo_dao.insert(entity_todos[0]);
            return null;
        }
    }

    private static class deleteall extends AsyncTask<Void, Void, Void> {
        private Todo_Dao todo_dao;

        private deleteall(Todo_Dao todo_dao) {
            this.todo_dao = todo_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todo_dao.deleteall();
            return null;
        }
    }

}
