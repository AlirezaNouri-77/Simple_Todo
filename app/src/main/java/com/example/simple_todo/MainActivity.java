package com.example.simple_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import com.example.simple_todo.database.Entity_Todo;
import com.example.simple_todo.database.Todo_Viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements todo_recyclerview.Onitemclick {

    private Todo_Viewmodel todo_viewmodel;
    private todo_recyclerview adapter;
    private RecyclerView recyclerView;
    private List<Entity_Todo> list_entity = new ArrayList<>();

    Dialog dialog;
    Button dialog_button;
    EditText add;
    TextView textView;
    TextView empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empty = findViewById(R.id.textView2);
        recyclerView = findViewById(R.id.RV);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        dialog = new Dialog(this);

        dialog.setContentView(R.layout.add_dialog);
        add = dialog.findViewById(R.id.editTextTextPersonName);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        textView = dialog.findViewById(R.id.textView3);
        dialog_button = dialog.findViewById(R.id.button);

        todo_viewmodel = new ViewModelProvider(this).get(Todo_Viewmodel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new todo_recyclerview(this);

        recyclerView.setAdapter(adapter);


        todo_viewmodel.getAlltodo().observe(this, new Observer<List<Entity_Todo>>() {
            @Override
            public void onChanged(List<Entity_Todo> list) {

                if (list.size() == 0) {
                    empty.setText("List is Empty");
                    recyclerView.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
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
                list_entity = adapter.getCurrentList();
                Entity_Todo entity_todo = list_entity.get(viewHolder.getAdapterPosition());

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
                        break;

                }
            }
        }).attachToRecyclerView(recyclerView);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Add Todo");
                dialog_button.setText("Add");
                dialog.show();
                dialog_button.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteall:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Attention");
                alertDialog.setMessage("Are you sure for delete all Todo");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todo_viewmodel.deleteall();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
        }
        return true;
    }

    @Override
    public void onclick(int postion) {

        list_entity = adapter.getCurrentList();
        Entity_Todo entity_todo = list_entity.get(postion);

        textView.setText("Update Todo");
        dialog.show();
        add.setText(entity_todo.getTodo());
        dialog_button.setText("Update");
        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entity_todo.setTodo(add.getText().toString());
                todo_viewmodel.update(entity_todo);
                adapter.notifyItemChanged(postion);
                dialog.cancel();
            }
        });
    }

}