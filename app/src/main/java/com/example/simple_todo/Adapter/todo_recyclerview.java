package com.example.simple_todo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_todo.R;
import com.example.simple_todo.database.Entity_Todo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class todo_recyclerview extends RecyclerView.Adapter<todo_recyclerview.todo_viewholder> {

    public todo_recyclerview (){}

    private List<Entity_Todo> list = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public todo_viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);

        return new todo_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull todo_viewholder holder, int position) {
    Entity_Todo entity_todo = list.get(position);
    holder.todo.setText(entity_todo.getTodo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void settodo (List<Entity_Todo> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public class todo_viewholder extends RecyclerView.ViewHolder {
        private TextView todo;

        public todo_viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            todo = itemView.findViewById(R.id.todo);
        }
    }
}
