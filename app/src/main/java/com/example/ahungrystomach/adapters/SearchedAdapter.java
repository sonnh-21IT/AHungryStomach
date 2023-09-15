package com.example.ahungrystomach.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahungrystomach.databinding.ItemSearchedBinding;
import com.example.ahungrystomach.listener.SearchedListener;

import java.util.List;

public class SearchedAdapter extends RecyclerView.Adapter<SearchedAdapter.MyViewHolder>  {
    private List<String> listSearched;
    private SearchedListener searchedListener;

    public SearchedAdapter(List<String> listSearched,SearchedListener searchedListener) {
        this.listSearched = listSearched;
        this.searchedListener=searchedListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchedBinding binding=ItemSearchedBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String searchedStr=listSearched.get(position);
        holder.binding.tvSearched.setText(listSearched.get(position));
        holder.binding.tvSearched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                searchedListener.onclick(searchedStr);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSearched.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemSearchedBinding binding;
        public MyViewHolder(ItemSearchedBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
