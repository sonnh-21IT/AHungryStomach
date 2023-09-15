package com.example.ahungrystomach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ahungrystomach.databinding.ItemCheckoutBinding;
import com.example.ahungrystomach.models.Cart;

import java.util.List;

public class CheckoutAdpater extends RecyclerView.Adapter<CheckoutAdpater.MyViewHolder> {
    private Context context;
    private List<Cart> cartList;

    public CheckoutAdpater(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCheckoutBinding checkoutBinding=ItemCheckoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(checkoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart=cartList.get(position);
        holder.binding.tvname.setText(cart.getMealDetail().getMeal());
        Glide.with(context).load(cart.getMealDetail().getStrmealthumb()).into(holder.binding.img);
        holder.binding.tvprice.setText("$"+String.valueOf(cart.getMealDetail().getPrice()));
        holder.binding.tvtotal.setText("$"+String.valueOf(cart.getAmount()*cart.getMealDetail().getPrice()));
        holder.binding.tvamount.setText(cart.getAmount()+"");
        holder.binding.tvtotal.setText("$"+String.valueOf(cart.getAmount()*cart.getMealDetail().getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemCheckoutBinding binding;

        public MyViewHolder(ItemCheckoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
