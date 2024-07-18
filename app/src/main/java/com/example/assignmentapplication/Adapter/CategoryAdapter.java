package com.example.assignmentapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.*;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> mlist;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Category cate);

        void onRemoveClick(Category cate);
    }


    public CategoryAdapter(List<Category> mlist, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category Cate = mlist.get(position);
        if (Cate == null) {
            return;
        }
        String cateID = Integer.toString(Cate.categoryId);
        String cateName = Cate.categoryName;

        holder.cateName.setText(cateName);

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(Cate));
        holder.btn.setOnClickListener(v -> onItemClickListener.onRemoveClick(Cate));

    }

    @Override
    public int getItemCount() {
        if (mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView cateName;
        private Button btn;

        public CategoryViewHolder(@NonNull View itemView) {

            super(itemView);

            cateName = itemView.findViewById(R.id.text_2);

            btn = itemView.findViewById(R.id.Remove);
        }

    }


}
