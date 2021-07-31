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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simple_todo.Adapter.todo_recyclerview;
import com.example.simple_todo.model.Entity_Todo;
import com.example.simple_todo.model.category_model;
import com.example.simple_todo.repo.category_repo;
import com.example.simple_todo.viewmodel.Todo_Viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements todo_recyclerview.Onitemclick {

    Dialog dialog_mainactiviy;
    Button dialog_button;
    EditText add;
    TextView textView;
    TextView empty;
    String category;

    private Todo_Viewmodel todo_viewmodel;

    private todo_recyclerview adapter;
    private RecyclerView recyclerView;

    category_model category_model;
    category_repo category_repo;


    private List<Entity_Todo> list_entity = new ArrayList<>();
    //private List<Entity_Todo> list_entity2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category_repo = new category_repo(getApplication());

        category_model = getIntent().getParcelableExtra("ss");


        TextView categorytextview = findViewById(R.id.categoty_textview);
        categorytextview.setText(category_model.getCategory());


        empty = findViewById(R.id.textView2);
        recyclerView = findViewById(R.id.RV);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        dialog_mainactiviy = new Dialog(this);
        dialog_mainactiviy.setContentView(R.layout.add_dialog_mainactivity);
        add = dialog_mainactiviy.findViewById(R.id.editTextTextPersonName);
        textView = dialog_mainactiviy.findViewById(R.id.textView3);
        dialog_button = dialog_mainactiviy.findViewById(R.id.button);
        dialog_mainactiviy.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_mainactiviy.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        todo_viewmodel = new ViewModelProvider(this).get(Todo_Viewmodel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new todo_recyclerview(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);


        todo_viewmodel.searchitem(category_model.getCategory()).observe(this, new Observer<List<Entity_Todo>>() {
            @Override
            public void onChanged(List<Entity_Todo> entity_todos) {

                adapter.submitList(entity_todos);

                if (entity_todos.size() == 0) {
                    empty.setText("List is Empty");
                    recyclerView.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }
        });


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
                            Entity_Todo entity_todo = new Entity_Todo();
                            String todo = add.getText().toString();
                            entity_todo.setTodo(todo);
                            entity_todo.setIsfinish(false);

                            entity_todo.setCode(category_model.getCategory());

                            category_model.setQuntity(category_model.getQuntity() + 1);
                            category_repo.update(category_model);

                            todo_viewmodel.insert(entity_todo);
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
        Entity_Todo entity_todo = list_entity.get(postion);

        textView.setText("Update Todo");
        dialog_mainactiviy.show();
        add.setText(entity_todo.getTodo());
        dialog_button.setText("Update");
        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entity_todo.setTodo(add.getText().toString());
                todo_viewmodel.update(entity_todo);
                adapter.notifyItemChanged(postion);
                dialog_mainactiviy.cancel();
            }
        });
    }


    @Override
    public void delete(int postion) {
        list_entity = adapter.getCurrentList();
        Entity_Todo entity_todo = list_entity.get(postion);
        category_model.setQuntity(category_model.getQuntity() - 1);
        category_repo.update(category_model);
        todo_viewmodel.delete(entity_todo);
    }


    @Override
    public void dene_or_not(int postion) {
        list_entity = adapter.getCurrentList();
        Entity_Todo entity_todo = list_entity.get(postion);
        if (!entity_todo.isIsfinish()) {
            entity_todo.setIsfinish(true);
            todo_viewmodel.update(entity_todo);
            adapter.notifyItemChanged(postion);
        }
    }

}