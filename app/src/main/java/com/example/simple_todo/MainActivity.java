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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.simple_todo.Adapter.Todo_Recyclerview_Adapter;
import com.example.simple_todo.model.Category_Model;
import com.example.simple_todo.model.Todo_Model;
import com.example.simple_todo.repo.Category_Repository;
import com.example.simple_todo.repo.Todo_Repository;
import com.example.simple_todo.viewmodel.Todo_Viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Todo_Recyclerview_Adapter.Onitemclick {

    private Dialog dialog_mainactiviy;
    private Button dialog_button;
    private EditText add;
    private TextView textView;
    private TextView empty;

    private Todo_Viewmodel todo_viewmodel;

    private Todo_Recyclerview_Adapter adapter;
    private RecyclerView recyclerView;

    private Category_Model category_model;
    private Category_Repository Category_Repository;
    private Todo_Repository todo_repository;

    private List<Todo_Model> list_entity = new ArrayList<>();

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category_model = getIntent().getParcelableExtra("Category_Code");

        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Todo_Recyclerview_Adapter(this);

        adapter.setcate(category_model.getCategory());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        todo_viewmodel = new ViewModelProvider(this).get(Todo_Viewmodel.class);

        Category_Repository = new Category_Repository(getApplication());
        todo_repository = new Todo_Repository(getApplication());

        if (category_model.getCategory().equals("All")) {

            gettodo_all_categoty("");

        } else {

            gettodos("");

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

                            Todo_Model todo_model = new Todo_Model();
                            String todo = add.getText().toString();
                            todo_model.setTodo(todo);
                            todo_model.setIsfinish(false);
                            todo_model.setCode(category_model.getCategory());
                            todo_viewmodel.insert(todo_model);

                            category_model.setQuntity(category_model.getQuntity() + 1);
                            Category_Repository.update(category_model);

                            add.getText().clear();

                        }

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(category_model.getCategory().equals("All")){
                    gettodo_all_categoty(newText);
                }else {
                    gettodos(newText);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.deleteall) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Attention");
            alertDialog.setMessage("Are you sure for delete all Todo");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {

                if (category_model.getCategory().equals("All")) {
                    todo_viewmodel.deleteall();
                    Category_Repository.update_quntity_to_zero();
                } else {
                    todo_repository.delete_all_bycode(category_model.getCategory());
                }

            });

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
        return true;
    }


    @Override
    public void clickon_itemview_recyclerview(int postion) {

        list_entity = adapter.getCurrentList();
        Todo_Model todo_model = list_entity.get(postion);

        textView.setText("Update Todo");
        dialog_mainactiviy.show();
        add.setText(todo_model.getTodo());
        dialog_button.setText("Update");
        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo_model.setTodo(add.getText().toString());
                todo_viewmodel.update(todo_model);
                adapter.notifyItemChanged(postion);
                dialog_mainactiviy.cancel();
            }
        });
    }

    @Override
    public void clickon_delete_recyclerview(int postion) {

        list_entity = adapter.getCurrentList();
        Todo_Model todo_model = list_entity.get(postion);

        if (category_model.getCategory().equals("All")) {

            Category_Repository.update_quntity(todo_model.getCode());
            todo_viewmodel.delete(todo_model);

        } else {

            category_model.setQuntity(category_model.getQuntity() - 1);
            Category_Repository.update(category_model);
            todo_viewmodel.delete(todo_model);

        }
    }


    @Override
    public void DoneOrNot(int postion) {

        list_entity = adapter.getCurrentList();
        Todo_Model todo_model = list_entity.get(postion);
        if (!todo_model.isIsfinish()) {
            todo_model.setIsfinish(true);
            todo_viewmodel.update(todo_model);
            adapter.notifyItemChanged(postion);
        }

    }

    public void gettodos(String search){
        todo_viewmodel.searchitem(category_model.getCategory() , search).observe(this, new Observer<List<Todo_Model>>() {
            @Override
            public void onChanged(List<Todo_Model> todo_models) {

                adapter.submitList(todo_models);

                if (todo_models.size() == 0) {
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

    public void gettodo_all_categoty (String newsearch){

        floatingActionButton.setVisibility(View.GONE);
        todo_viewmodel.getAlltodo(newsearch).observe(this, new Observer<List<Todo_Model>>() {
            @Override
            public void onChanged(List<Todo_Model> todo_models) {
                adapter.submitList(todo_models);

                if (todo_models.size() == 0) {
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

}