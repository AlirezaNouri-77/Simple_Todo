package com.example.simple_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple_todo.Adapter.todo_recyclerview;
import com.example.simple_todo.database.Entity_Todo;
import com.example.simple_todo.database.Todo_Viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements todo_recyclerview.Onitemclick {

    private Todo_Viewmodel todo_viewmodel;
    todo_recyclerview adapter;
    private List<Entity_Todo> list = new ArrayList<>();

    Dialog dialog;
    Button button;
    EditText add;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(this);

        dialog.setContentView(R.layout.add_dialog);
        add = dialog.findViewById(R.id.editTextTextPersonName);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView = dialog.findViewById(R.id.textView3);
        button = dialog.findViewById(R.id.button);


        todo_viewmodel = new ViewModelProvider(this).get(Todo_Viewmodel.class);


        RecyclerView recyclerView = findViewById(R.id.RV);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new todo_recyclerview(this);

        recyclerView.setAdapter(adapter);

        todo_viewmodel.getAlltodo().observe(this, new Observer<List<Entity_Todo>>() {
            @Override
            public void onChanged(List<Entity_Todo> list) {
                adapter.submitList(list);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                list = adapter.getCurrentList();
                Entity_Todo entity_todo = list.get(viewHolder.getAdapterPosition());

                switch (direction) {
                    case ItemTouchHelper.RIGHT:
                        if (!entity_todo.isIsfinish()) {
                            entity_todo.setIsfinish(true);
                            todo_viewmodel.update(entity_todo);
                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        } else {
                            entity_todo.setIsfinish(false);
                            todo_viewmodel.update(entity_todo);
                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        }
                        break;

                    case ItemTouchHelper.LEFT:
                        todo_viewmodel.delete(entity_todo);
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        break;

                }
            }

        }).attachToRecyclerView(recyclerView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setText("Add Todo");
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!add.getText().toString().isEmpty()) {
                            Entity_Todo entity_todo = new Entity_Todo();
                            String todo = add.getText().toString();
                            entity_todo.setTodo(todo);
                            entity_todo.setIsfinish(false);
                            todo_viewmodel.insert(entity_todo);
                        }
                    }
                });
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

        list = adapter.getCurrentList();
        Entity_Todo entity_todo = list.get(postion);
        textView.setText("Update Todo");
        dialog.show();

        add.setText(entity_todo.getTodo());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entity_todo.setTodo(add.getText().toString());
                todo_viewmodel.update(entity_todo);
                adapter.notifyItemChanged(postion);
            }
        });
    }
}