package com.example.simple_todo.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.example.simple_todo.dao.Category_Dao;
import com.example.simple_todo.database.category_database;
import com.example.simple_todo.model.Category_Model;

import java.util.List;

public class Category_Repository {

    private Category_Dao category_dao;
    private LiveData<List<Category_Model>> getallcategory;

    public Category_Repository(Application application) {
        category_database category_database = com.example.simple_todo.database.category_database.getInstance(application);
        category_dao = category_database.category_dao();
        getallcategory = category_dao.getall();
    }

    public LiveData<List<Category_Model>> getallcategory() {
        return getallcategory;
    }

    public void insert(Category_Model category_model) {
        new insert(category_dao).execute(category_model);
    }

    public void uodate_quntity (String category ){
         new up_qun(category_dao , category).execute();
    }

    public void update(Category_Model category_model) {
        new update(category_dao).execute(category_model);
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

    public static class add_default extends AsyncTask<Void, Void, Void> {
        private final Category_Dao category_dao;

        public add_default(Category_Dao category_dao) {
            this.category_dao = category_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            category_dao.insert(new Category_Model("All" , 0 ));
            return null;
        }
    }
    public static class up_qun extends AsyncTask<Void , Void , Void>{
        private final Category_Dao category_dao;
        private final String category;

        public up_qun(Category_Dao category_dao , String category){
            this.category_dao=category_dao;
            this.category=category;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            category_dao.update_quntinty( category);
            return null;
        }
    }
}
