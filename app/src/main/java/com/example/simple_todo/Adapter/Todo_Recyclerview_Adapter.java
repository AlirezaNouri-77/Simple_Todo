package com.example.simple_todo.Adapter;

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
import com.example.simple_todo.model.Todo_Model;

import org.jetbrains.annotations.NotNull;

public class Todo_Recyclerview_Adapter extends ListAdapter<Todo_Model, Todo_Recyclerview_Adapter.todo_viewholder> {

    private final Onitemclick onitemclick;
    String cate;


    public Todo_Recyclerview_Adapter(Onitemclick onitemclick) {
        super(diffCallback);
        this.onitemclick = onitemclick;
    }

    public void setcate(String cate) {
        this.cate = cate;
    }

    private static final DiffUtil.ItemCallback<Todo_Model> diffCallback = new DiffUtil.ItemCallback<Todo_Model>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Todo_Model oldItem, @NonNull @NotNull Todo_Model newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Todo_Model oldItem, @NonNull @NotNull Todo_Model newItem) {
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


        Todo_Model todo_model = getItem(position);
        holder.todo.setText(todo_model.getTodo());


        if (cate.equals("All")) {
            holder.category.setText("Category : " + todo_model.getCode());
        } else {
            holder.category.setVisibility(View.GONE);
        }

        if (!todo_model.isIsfinish()) {
            holder.imageView.setImageResource(R.drawable.ic_twotone_close_24);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_twotone_done_24);
        }
    }

    public static class todo_viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView todo;
        private final TextView category;
        private final ImageView imageView;
        private final ImageView delete;
        Onitemclick onitemclick;

        public todo_viewholder(@NonNull @NotNull View itemView, Onitemclick onitemclick) {
            super(itemView);

            todo = itemView.findViewById(R.id.todo_textview);
            imageView = itemView.findViewById(R.id.done_or_not_iamgeview);
            delete = itemView.findViewById(R.id.delete_imageview);
            category = itemView.findViewById(R.id.category_textview_rv);

            this.onitemclick = onitemclick;
            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v == itemView) {
                onitemclick.clickon_itemview_recyclerview(getAdapterPosition());
            } else if (v == delete) {
                onitemclick.clickon_delete_recyclerview(getAdapterPosition());
            } else if (v == imageView) {
                onitemclick.DoneOrNot(getAdapterPosition());
            }

        }
    }

    public interface Onitemclick {

        void clickon_itemview_recyclerview(int postion);

        void clickon_delete_recyclerview(int postion);

        void DoneOrNot(int postion);
    }
}
