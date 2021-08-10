package com.example.simple_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simple_todo.Adapter.Todo_Recyclerview_Adapter;
import com.example.simple_todo.model.Category_Model;
import com.example.simple_todo.model.Todo_Model;
import com.example.simple_todo.repo.Category_Repository;
import com.example.simple_todo.viewmodel.Todo_Viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements Todo_Recyclerview_Adapter.Onitemclick {

    Dialog dialog_mainactiviy;
    Button dialog_button;
    EditText add;
    TextView textView;
    TextView empty;
    String category;

    String getCategory;

    private Todo_Viewmodel todo_viewmodel;

    private Todo_Recyclerview_Adapter adapter;
    private RecyclerView recyclerView;

    Category_Model category_model;
    Category_Repository Category_Repository;


    private List<Todo_Model> list_entity = new ArrayList<>();
    private List<Category_Model> category_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category_model = getIntent().getParcelableExtra("ss");

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Todo_Recyclerview_Adapter(this);

        adapter.setcate(category_model.getCategory());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);



        todo_viewmodel = new ViewModelProvider(this).get(Todo_Viewmodel.class);

        Category_Repository = new Category_Repository(getApplication());



        if (category_model.getCategory().equals("All")) {
            floatingActionButton.setVisibility(View.GONE);
            todo_viewmodel.getAlltodo().observe(this, new Observer<List<Todo_Model>>() {
                @Override
                public void onChanged(List<Todo_Model> todo_models) {
                    adapter.submitList(todo_models);
                }
            });
        } else {

            todo_viewmodel.searchitem(category_model.getCategory()).observe(this, new Observer<List<Todo_Model>>() {
                @Override
                public void onChanged(List<Todo_Model> _todoModels) {

                    adapter.submitList(_todoModels);

                    if (_todoModels.size() == 0) {
                        empty.setText("List is Empty");
                        recyclerView.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                    }
                }
            });
        }

        TextView categorytextview = findViewById(R.id.categoty_textview);
        categorytextview.setText(category_model.getCategory());

        empty = findViewById(R.id.textView2);



        dialog_mainactiviy = new Dialog(this);
        dialog_mainactiviy.setContentView(R.layout.add_dialog_mainactivity);
        add = dialog_mainactiviy.findViewById(R.id.editTextTextPersonName);
        textView = dialog_mainactiviy.findViewById(R.id.textView3);
        dialog_button = dialog_mainactiviy.findViewById(R.id.button);
        dialog_mainactiviy.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_mainactiviy.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Add Todo");
                dialog_button.setText("Add");
                dialog_mainactiviy.show();
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!add.getText().toString().isEmpty()) {
                            Todo_Model _todoModel = new Todo_Model();
                            String todo = add.getText().toString();
                            _todoModel.setTodo(todo);
                            _todoModel.setIsfinish(false);

                            _todoModel.setCode(category_model.getCategory());

                            category_model.setQuntity(category_model.getQuntity() + 1);
                            Category_Repository.update(category_model);

                            todo_viewmodel.insert(_todoModel);
                        }
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.deleteall) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Attention");
            alertDialog.setMessage("Are you sure for delete all Todo");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todo_viewmodel.deleteall(category);
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
        return true;
    }


    @Override
    public void onclick(int postion) {

        list_entity = adapter.getCurrentList();
        Todo_Model _todoModel = list_entity.get(postion);

        Log.d("TAG", "onclick: " + _todoModel.getCode());
        textView.setText("Update Todo");
        dialog_mainactiviy.show();
        add.setText(_todoModel.getTodo());
        dialog_button.setText("Update");
        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _todoModel.setTodo(add.getText().toString());
                todo_viewmodel.update(_todoModel);
                adapter.notifyItemChanged(postion);
                dialog_mainactiviy.cancel();
            }
        });
    }


    @Override
    public void delete(int postion) {

        list_entity = adapter.getCurrentList();
        Todo_Model todoModel = list_entity.get(postion);

        if (category_model.getCategory().equals("All")){

            Category_Repository.uodate_quntity(todoModel.getCode());
            todo_viewmodel.delete(todoModel);
        }else {
            category_model.setQuntity(category_model.getQuntity() - 1);
            Category_Repository.update(category_model);
            todo_viewmodel.delete(todoModel);
        }

    }


    @Override
    public void dene_or_not(int postion) {
        list_entity = adapter.getCurrentList();
        Todo_Model _todoModel = list_entity.get(postion);
        if (!_todoModel.isIsfinish()) {
            _todoModel.setIsfinish(true);
            todo_viewmodel.update(_todoModel);
            adapter.notifyItemChanged(postion);
        }
    }

}