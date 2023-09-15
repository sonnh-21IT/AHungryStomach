package com.example.ahungrystomach.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ahungrystomach.Utils.Utils;
import com.example.ahungrystomach.activities.CartActivity;
import com.example.ahungrystomach.databinding.ItemCartBinding;
import com.example.ahungrystomach.listener.ChangeNumListener;
import com.example.ahungrystomach.models.Cart;

import java.util.List;

import io.paperdb.Paper;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<Cart> cartList;
    private ChangeNumListener changeNumListener;

    public CartAdapter(Context context, List<Cart> cartList, ChangeNumListener changeNumListener) {
        this.context = context;
        this.cartList = cartList;
        this.changeNumListener = changeNumListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding cartBinding=ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(cartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart=cartList.get(position);
        holder.binding.tvname.setText(cart.getMealDetail().getMeal());
        Glide.with(context).load(cart.getMealDetail().getStrmealthumb()).into(holder.binding.img);
        holder.binding.tvprice.setText("$"+String.valueOf(cart.getMealDetail().getPrice()));
        holder.binding.tvtotal.setText("$"+String.valueOf(cart.getAmount()*cart.getMealDetail().getPrice()));
        holder.binding.tvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(holder.getAdapterPosition());
                notifyDataSetChanged();
                changeNumListener.change();
            }
        });
        holder.binding.tvsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subToCart(holder.getAdapterPosition());
                notifyDataSetChanged();
                changeNumListener.change();
            }
        });
        holder.binding.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.cartList.isEmpty()){
                    removeToCart(holder.getAdapterPosition());
                    notifyDataSetChanged();
                    changeNumListener.change();
                }
            }
        });
        holder.binding.tvamount.setText(cart.getAmount()+"");
        holder.binding.tvtotal.setText("$"+String.valueOf(cart.getAmount()*cart.getMealDetail().getPrice()));
    }

    private void removeToCart(int adapterPosition) {
        Utils.cartList.remove(adapterPosition);
        Paper.book().write("cart",Utils.cartList);
        if (Utils.cartList.isEmpty()){
            CartActivity.binding.viewCart.setVisibility(View.GONE);
            CartActivity.binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void subToCart(int adapterPosition) {
        if (Utils.cartList.get(adapterPosition).getAmount()>1){
            Utils.cartList.get(adapterPosition).setAmount(Utils.cartList.get(adapterPosition).getAmount()-1);
        }
        Paper.book().write("cart",Utils.cartList);
    }

    private void addToCart(int adapterPosition) {
        Utils.cartList.get(adapterPosition).setAmount(Utils.cartList.get(adapterPosition).getAmount()+1);
        Paper.book().write("cart",Utils.cartList);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemCartBinding binding;

        public MyViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
