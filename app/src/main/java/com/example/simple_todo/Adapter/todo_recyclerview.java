package com.example.simple_todo.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_todo.R;
import com.example.simple_todo.model.Entity_Todo;

import org.jetbrains.annotations.NotNull;

public class todo_recyclerview extends ListAdapter<Entity_Todo, todo_recyclerview.todo_viewholder> {

    private Onitemclick onitemclick;

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
            holder.imageView.setBackgroundColor(Color.parseColor("#e53935"));
            holder.imageView.setImageResource(R.drawable.ic_twotone_close_24);
        } else {
            holder.imageView.setBackgroundColor(Color.parseColor("#4caf50"));
            holder.imageView.setImageResource(R.drawable.ic_twotone_done_24);
        }
    }

    public static class todo_viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView todo;
        private final ImageView imageView;
        Onitemclick onitemclick;

        public todo_viewholder(@NonNull @NotNull View itemView, Onitemclick onitemclick) {
            super(itemView);
            todo = itemView.findViewById(R.id.todo);
            imageView = itemView.findViewById(R.id.imageView4);
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
