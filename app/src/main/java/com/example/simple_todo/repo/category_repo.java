package com.example.simple_todo.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.simple_todo.dao.category_dao;
import com.example.simple_todo.database.category_database;
import com.example.simple_todo.model.category_model;

import java.util.List;

public class category_repo {

    private category_dao category_dao;
    private LiveData<List<category_model>> getallcategory;

    public category_repo (Application application){
        category_database category_database = com.example.simple_todo.database.category_database.getInstance(application);
        category_dao = category_database.category_dao();
        getallcategory = category_dao.getall();
    }

    public category_repo() {}

    public LiveData<List<category_model>> getallcategory (){
        return getallcategory;
    }

    public void insert(category_model category_model){
        new insert(category_dao).execute(category_model);
    }

    public void update ( category_model category_model ){
        new update(category_dao).execute(category_model);
    }

    public class insert extends AsyncTask<category_model , Void , Void >{
        private category_dao category_dao;

        public insert(category_dao category_dao){
            this.category_dao = category_dao;
        }

        @Override
        protected Void doInBackground(category_model... category_models) {
            category_dao.insert(category_models[0]);
            return null;
        }
    }

    public class update extends AsyncTask<category_model , Void , Void >{
        private category_dao category_dao;

        public update(category_dao category_dao){
            this.category_dao = category_dao;
        }

        @Override
        protected Void doInBackground(category_model... category_models) {
            category_dao.update(category_models[0]);
            return null;
        }
    }
}
