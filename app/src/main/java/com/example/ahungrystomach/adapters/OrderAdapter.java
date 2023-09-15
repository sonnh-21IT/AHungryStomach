package com.example.ahungrystomach.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahungrystomach.databinding.ItemOrderBinding;
import com.example.ahungrystomach.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList=orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding orderBinding=ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(orderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order=orderList.get(position);
        if (order.getStatus()==1){
            holder.binding.status.setVisibility(View.GONE);
            holder.binding.statusIng.setVisibility(View.VISIBLE);
        }else {
            holder.binding.status.setVisibility(View.VISIBLE);
            holder.binding.statusIng.setVisibility(View.GONE);
        }
        holder.binding.tvIdorder.setText("ID: "+order.getId());
        holder.binding.tvAmount.setText("amount: "+String.valueOf(order.getAmount()));
        holder.binding.tvtotal.setText("$"+String.valueOf(order.getTotal()));
        holder.binding.tvDate.setText("date: "+order.getDate());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemOrderBinding binding;

        public MyViewHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
