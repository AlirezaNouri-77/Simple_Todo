package com.example.simple_todo.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_todo.R;
import com.example.simple_todo.database.Entity_Todo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class todo_recyclerview extends RecyclerView.Adapter<todo_recyclerview.todo_viewholder> {

    Onitemclick onitemclick;
    public todo_recyclerview(Onitemclick onitemclick) {
        this.onitemclick = onitemclick;
    }

    private List<Entity_Todo> list = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public todo_viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);

        return new todo_viewholder(view , onitemclick);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull todo_viewholder holder, int position) {
        Entity_Todo entity_todo = list.get(position);
        holder.todo.setText(entity_todo.getTodo());

        if (entity_todo.isIsfinish()){
            holder.cardView.setBackgroundColor(Color.parseColor("#81c784"));
        }else {
            holder.cardView.setBackgroundColor(Color.parseColor("#f44336"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void settodo(List<Entity_Todo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<Entity_Todo> getList() {
        return list;
    }

    public class todo_viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView todo;
        private CardView cardView;
        Onitemclick onitemclick;

        public todo_viewholder(@NonNull @NotNull View itemView, Onitemclick onitemclick) {
            super(itemView);
            todo = itemView.findViewById(R.id.todo);
            cardView = itemView.findViewById(R.id.CV);
            this.onitemclick = onitemclick;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onitemclick.onclick(getAdapterPosition());
        }
    }

    public interface Onitemclick {
        void onclick(int postion);
    }
}
