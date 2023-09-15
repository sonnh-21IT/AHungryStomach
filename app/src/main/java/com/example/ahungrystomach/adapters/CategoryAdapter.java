package com.example.ahungrystomach.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ahungrystomach.databinding.ItemCategoryBinding;
import com.example.ahungrystomach.listener.CategoryListener;
import com.example.ahungrystomach.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    List<Category> list;
    private CategoryListener listener;

    public CategoryAdapter(List<Category> list, CategoryListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding itemCategoryBinding=ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(itemCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setBinding(list.get(position));
        Glide.with(holder.itemView).load(list.get(position).getCategoryThumb()).into(holder.binding.imageCategory);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private ItemCategoryBinding binding;
        public MyViewHolder(ItemCategoryBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        public void setBinding(Category category){
            binding.setCategoryName(category);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }

}
