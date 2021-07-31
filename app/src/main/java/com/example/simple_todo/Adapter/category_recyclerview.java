package com.example.simple_todo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_todo.R;
import com.example.simple_todo.model.Entity_Todo;
import com.example.simple_todo.model.category_model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class category_recyclerview extends ListAdapter< category_model, category_recyclerview.category_viewholder> {

    Onitemclick onitemclick;

    public category_recyclerview(Onitemclick onitemclick) {
        super(diffCallback);
        this.onitemclick = onitemclick;
    }
    private static final DiffUtil.ItemCallback<category_model> diffCallback = new DiffUtil.ItemCallback<category_model>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull category_model oldItem, @NonNull @NotNull category_model newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull category_model oldItem, @NonNull @NotNull category_model newItem) {
            return oldItem.getCategory().equals(newItem.getCategory()) && oldItem.getQuntity() == newItem.getQuntity();
        }
    };


    @NonNull
    @NotNull
    @Override
    public category_viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recyclerview, parent, false);
        return new category_viewholder(view , onitemclick);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull category_viewholder holder, int position) {
        category_model category_model = getItem(position);
        holder.category.setText(category_model.getCategory());
        holder.quntity.setText(category_model.getQuntity()+" Todo");
    }

    public static class category_viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView category, quntity;
        Onitemclick onitemclick;

        public category_viewholder(@NonNull @NotNull View itemView , Onitemclick onitemclick) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            quntity = itemView.findViewById(R.id.quntity);

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
