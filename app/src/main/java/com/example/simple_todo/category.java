package com.example.simple_todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simple_todo.model.category_model;

import java.util.ArrayList;
import java.util.List;

import com.example.simple_todo.Adapter.category_recyclerview;
import com.example.simple_todo.viewmodel.category_viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class category extends AppCompatActivity implements category_recyclerview.Onitemclick {

    TextView title_dialog;
    EditText edittext_dialog;
    Button add_dialog_button;

    List<category_model> mlist = new ArrayList<>();
    category_recyclerview category_recyclerview;

    Dialog add_category_dialog;

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

        category_recyclerview = new category_recyclerview((com.example.simple_todo.Adapter.category_recyclerview.Onitemclick) this);
        RecyclerView recyclerView = findViewById(R.id.category_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(category_recyclerview);


        category_viewmodel category_viewmodel = new ViewModelProvider(this).get(category_viewmodel.class);

        category_viewmodel.getallcategory().observe(this, new Observer<List<category_model>>() {
            @Override
            public void onChanged(List<category_model> category_models) {
                category_recyclerview.submitList(category_models);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_model category_model = new category_model();
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
        Intent intent = new Intent(category.this, MainActivity.class);
        mlist = category_recyclerview.getCurrentList();
        category_model category_model = mlist.get(postion);
        intent.putExtra("ss" , category_model);
        category.this.startActivity(intent);
    }
}