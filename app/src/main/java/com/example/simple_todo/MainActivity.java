package com.example.simple_todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.simple_todo.Adapter.todo_recyclerview;
import com.example.simple_todo.database.Entity_Todo;
import com.example.simple_todo.database.Todo_Viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Todo_Viewmodel todo_viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.RV);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        todo_recyclerview adapter = new todo_recyclerview();
        recyclerView.setAdapter(adapter);

        todo_viewmodel = new ViewModelProvider(this).get(Todo_Viewmodel.class);
        todo_viewmodel.getAlltodo().observe(this, new Observer<List<Entity_Todo>>() {
            @Override
            public void onChanged(List<Entity_Todo> entity_todos) {
                adapter.settodo(entity_todos);
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entity_Todo entity_todo = new Entity_Todo();
                entity_todo.setTodo("This is Test 1");
                todo_viewmodel.insert(entity_todo);
            }
        });
    }
}