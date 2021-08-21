package com.example.simple_todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple_todo.model.Category_Model;

import java.util.ArrayList;
import java.util.List;

import com.example.simple_todo.Adapter.Category_Recyclerview_Adapter;
import com.example.simple_todo.repo.Category_Repository;
import com.example.simple_todo.repo.Todo_Repository;
import com.example.simple_todo.viewmodel.Category_Viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Category_Activity extends AppCompatActivity implements Category_Recyclerview_Adapter.Onitemclick {

    TextView title_dialog;
    EditText edittext_dialog;
    Button add_dialog_button;

    List<Category_Model> mlist = new ArrayList<>();
    Category_Recyclerview_Adapter Category_Recyclerview_Adapter;

    Dialog add_category_dialog;

    Category_Viewmodel category_viewmodel;
    Todo_Repository todo_repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        FloatingActionButton actionButton = findViewById(R.id.floatingActionButton2);

        add_category_dialog = new Dialog(this);
        add_category_dialog.setContentView(R.layout.add_dialog);
        title_dialog = add_category_dialog.findViewById(R.id.textView3);
        edittext_dialog = add_category_dialog.findViewById(R.id.editTextTextPersonName);
        add_dialog_button =add_category_dialog.findViewById(R.id.button);
        add_category_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        add_category_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Category_Recyclerview_Adapter = new Category_Recyclerview_Adapter((Category_Recyclerview_Adapter.Onitemclick) this);
        RecyclerView recyclerView = findViewById(R.id.category_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Category_Recyclerview_Adapter);


        category_viewmodel = new ViewModelProvider(this).get(Category_Viewmodel.class);
        todo_repository = new Todo_Repository(getApplication());

        category_viewmodel.getallcategory().observe(this, new Observer<List<Category_Model>>() {
            @Override
            public void onChanged(List<Category_Model> Category_Models) {
                Category_Recyclerview_Adapter.submitList(Category_Models);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category_Model category_model = new Category_Model();
                title_dialog.setText("Enter new category");
                add_dialog_button.setText("Add");
                add_category_dialog.show();
                add_dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        category_model.setCategory(edittext_dialog.getText().toString());
                        category_viewmodel.insert(category_model);
                    }
                });
            }
        });
    }

    @Override
    public void onclick(int postion) {

        Intent intent = new Intent(Category_Activity.this, MainActivity.class);
        mlist = Category_Recyclerview_Adapter.getCurrentList();
        Category_Model category_model = mlist.get(postion);
        intent.putExtra("Category_Code" , category_model);
        Category_Activity.this.startActivity(intent);

    }

    @Override
    public void onlongitem(int postion) {

        mlist = Category_Recyclerview_Adapter.getCurrentList();
        Category_Model category_model = mlist.get(postion);
        if(category_model.getCategory().equals("All")){
            return;
        }else {
            category_viewmodel.delete(category_model);
            todo_repository.delete_all_bycode(category_model.getCategory());
        }
    }

}