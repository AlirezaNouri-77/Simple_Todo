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
import com.example.simple_todo.model.Category_Model;

import org.jetbrains.annotations.NotNull;

public class Category_Recyclerview_Adapter extends ListAdapter<Category_Model, Category_Recyclerview_Adapter.category_viewholder> {

    Onitemclick onitemclick;

    public Category_Recyclerview_Adapter(Onitemclick onitemclick) {
        super(diffCallback);
        this.onitemclick = onitemclick;
    }

    private static final DiffUtil.ItemCallback<Category_Model> diffCallback = new DiffUtil.ItemCallback<Category_Model>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Category_Model oldItem, @NonNull @NotNull Category_Model newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Category_Model oldItem, @NonNull @NotNull Category_Model newItem) {
            return oldItem.getCategory().equals(newItem.getCategory()) && oldItem.getQuntity() == newItem.getQuntity();
        }
    };


    @NonNull
    @NotNull
    @Override
    public category_viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recyclerview, parent, false);
        return new category_viewholder(view, onitemclick);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull category_viewholder holder, int position) {
        //  Category_Model category_model = getItem(position);
        holder.category.setText(getItem(position).getCategory());
        if (getItem(position).getCategory().equals("All")) {
            holder.quntity.setText("");
        } else {
            holder.quntity.setText(getItem(position).getQuntity() + " Todo");
        }
    }

    public static class category_viewholder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener {

        TextView category, quntity;
        Onitemclick onitemclick;

        public category_viewholder(@NonNull @NotNull View itemView, Onitemclick onitemclick) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            quntity = itemView.findViewById(R.id.quntity);

            this.onitemclick = onitemclick;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onitemclick.onclick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onitemclick.onlongitem(getAdapterPosition());
            return false;
        }
    }

    public interface Onitemclick {
        void onclick(int postion);

        void onlongitem(int postion);
    }
}
