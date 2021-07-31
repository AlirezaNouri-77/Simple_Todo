package com.example.simple_todo.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_todo.R;
import com.example.simple_todo.model.Entity_Todo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class todo_recyclerview extends ListAdapter<Entity_Todo, todo_recyclerview.todo_viewholder> {

    private final Onitemclick onitemclick;

    public todo_recyclerview(Onitemclick onitemclick) {
        super(diffCallback);
        this.onitemclick = onitemclick;
    }

    private static final DiffUtil.ItemCallback<Entity_Todo> diffCallback = new DiffUtil.ItemCallback<Entity_Todo>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Entity_Todo oldItem, @NonNull @NotNull Entity_Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Entity_Todo oldItem, @NonNull @NotNull Entity_Todo newItem) {
            return oldItem.getTodo().equals(newItem.getTodo()) && oldItem.isIsfinish() == newItem.isIsfinish();
        }
    };

    @NonNull
    @NotNull
    @Override
    public todo_viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);
        return new todo_viewholder(view, onitemclick);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull todo_viewholder holder, int position) {

        Entity_Todo entity_todo = getItem(position);
        holder.todo.setText(entity_todo.getTodo());

        if (!entity_todo.isIsfinish()) {
            holder.imageView.setImageResource(R.drawable.ic_twotone_close_24);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_twotone_done_24);
        }
    }

    public static class todo_viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView todo;
        private ImageView imageView , delete;
        Onitemclick onitemclick;

        public todo_viewholder(@NonNull @NotNull View itemView, Onitemclick onitemclick) {
            super(itemView);
            todo = itemView.findViewById(R.id.todo_textview);
            imageView = itemView.findViewById(R.id.done_or_not_iamgeview);
            delete = itemView.findViewById(R.id.delete_imageview);

            this.onitemclick = onitemclick;
            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v == itemView) {
                onitemclick.onclick(getAdapterPosition());
            } else if (v == delete) {
                onitemclick.delete(getAdapterPosition());
            } else if (v == imageView) {
                onitemclick.dene_or_not(getAdapterPosition());
            }

        }
    }

    public interface Onitemclick {
        void onclick(int postion);
        void delete(int postion);
        void dene_or_not(int postion);
    }
}
