package com.example.simple_todo.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.simple_todo.dao.Category_Dao;
import com.example.simple_todo.database.category_database;
import com.example.simple_todo.model.Category_Model;

import java.util.List;

public class Category_Repository {

    private final Category_Dao category_dao;
    private final LiveData<List<Category_Model>> getallcategory;

    public Category_Repository(Application application) {
        category_database category_database = com.example.simple_todo.database.category_database.getInstance(application);
        category_dao = category_database.category_dao();
        getallcategory = category_dao.getall();
    }

    public void delete (Category_Model category_model){
        new delete(category_dao).execute(category_model);
    }

    public LiveData<List<Category_Model>> getallcategory() {
        return getallcategory;
    }

    public void update_quntity_to_zero(){
        new update_quntity_tozero(category_dao).execute();
    }
    public void insert(Category_Model category_model) {
        new insert(category_dao).execute(category_model);
    }

    public void update_quntity(String category) {
        new update_quntity(category_dao, category).execute();
    }

    public void update(Category_Model category_model) {
        new update(category_dao).execute(category_model);
    }

    public static class delete extends AsyncTask<Category_Model , Void , Void>{
        private final Category_Dao category_dao;
        public delete(Category_Dao category_dao){
            this.category_dao = category_dao;
        }

        @Override
        protected Void doInBackground(Category_Model... category_models) {
            category_dao.delete(category_models[0]);
            return null;
        }
    }
    public static class insert extends AsyncTask<Category_Model, Void, Void> {
        private final Category_Dao category_dao;

        public insert(Category_Dao category_dao) {
            this.category_dao = category_dao;
        }

        @Override
        protected Void doInBackground(Category_Model... Category_Models) {
            category_dao.insert(Category_Models[0]);
            return null;
        }
    }

    public static class update extends AsyncTask<Category_Model, Void, Void> {
        private final Category_Dao category_dao;

        public update(Category_Dao category_dao) {
            this.category_dao = category_dao;
        }

        @Override
        protected Void doInBackground(Category_Model... Category_Models) {
            category_dao.update(Category_Models[0]);
            return null;
        }
    }

    public static class update_quntity_tozero extends AsyncTask<Void, Void, Void> {
        private final Category_Dao category_dao;

        public update_quntity_tozero(Category_Dao category_dao) {
            this.category_dao = category_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            category_dao.setzerotoquntity();
            return null;
        }
    }

    public static class add_default extends AsyncTask<Void, Void, Void> {
        private final Category_Dao category_dao;

        public add_default(Category_Dao category_dao) {
            this.category_dao = category_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            category_dao.insert(new Category_Model("All", 0));
            return null;
        }
    }

    public static class update_quntity extends AsyncTask<Void, Void, Void> {
        private final Category_Dao category_dao;
        private final String category;

        public update_quntity(Category_Dao category_dao, String category) {
            this.category_dao = category_dao;
            this.category = category;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            category_dao.update_quntinty(category);
            return null;
        }
    }

}
