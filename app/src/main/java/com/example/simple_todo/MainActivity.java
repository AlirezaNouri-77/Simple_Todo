package com.example.simple_todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simple_todo.Adapter.todo_recyclerview;
import com.example.simple_todo.database.Entity_Todo;
import com.example.simple_todo.database.Todo_Viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements todo_recyclerview.Onitemclick {

    private Todo_Viewmodel todo_viewmodel;
    todo_recyclerview adapter;
    private List<Entity_Todo> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todo_viewmodel = new ViewModelProvider(this).get(Todo_Viewmodel.class);


        RecyclerView recyclerView = findViewById(R.id.RV);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         adapter = new todo_recyclerview(this);

        recyclerView.setAdapter(adapter);

        todo_viewmodel.getAlltodo().observe(this, new Observer<List<Entity_Todo>>() {
            @Override
            public void onChanged(List<Entity_Todo> list) {
                adapter.settodo(list);
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entity_Todo entity_todo = new Entity_Todo();
                entity_todo.setTodo("This is Test 1");
                entity_todo.setIsfinish(false);
                todo_viewmodel.insert(entity_todo);
            }
        });
        floatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                todo_viewmodel.deleteall();
                return false;
            }
        });

    }

    @Override
    public void onclick(int postion) {

        list = adapter.getList();
        Entity_Todo entity_todo = list.get(postion);

        if (!entity_todo.isIsfinish()){
            entity_todo.setIsfinish(true);
            todo_viewmodel.update(entity_todo);
        }else {
            entity_todo.setIsfinish(false);
            todo_viewmodel.update(entity_todo);
        }


    }
}