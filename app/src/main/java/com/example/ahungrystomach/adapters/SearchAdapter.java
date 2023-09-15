package com.example.ahungrystomach.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ahungrystomach.Utils.Utils;
import com.example.ahungrystomach.activities.DetailActivity;
import com.example.ahungrystomach.databinding.ItemSearchBinding;
import com.example.ahungrystomach.listener.DetailListener;
import com.example.ahungrystomach.models.Cart;
import com.example.ahungrystomach.models.MealDetail;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private List<MealDetail> mealDetailList;
    private DetailListener detailListener;
    private Context context;

    public SearchAdapter(List<MealDetail> mealDetailList,DetailListener listener,Context context) {
        this.mealDetailList = mealDetailList;
        this.detailListener=listener;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MealDetail mealDetail=mealDetailList.get(position);
        holder.setBinding(mealDetailList.get(position));
        holder.binding.price.setText("$"+mealDetailList.get(position).getPrice());
        Glide.with(holder.itemView).load(mealDetailList.get(position).getStrmealthumb()).into(holder.binding.img);
        holder.binding.desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                detailListener.detail(mealDetail);
            }
        });
        holder.binding.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                detailListener.detail(mealDetail);
            }
        });
        holder.binding.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                detailListener.detail(mealDetail);
            }
        });
        holder.binding.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(1,mealDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemSearchBinding binding;
        public MyViewHolder(ItemSearchBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        private void setBinding(MealDetail mealDetail){
            binding.setMeal(mealDetail);
            binding.executePendingBindings();
        }
    }
    private void addToCart(int amount,MealDetail mealDetail) {
        boolean checkExit=false;
        int n =0;
        if (Utils.cartList!=null){
            if(Utils.cartList.size()>0){
                for (int i =0;i<Utils.cartList.size();i++){
                    if(Utils.cartList.get(i).getMealDetail().getId()==mealDetail.getId()){
                        checkExit=true;
                        n = i;
                        break;
                    }
                }
            }
        }else {
            Utils.cartList=new ArrayList<>();
        }
        if (checkExit){
            Utils.cartList.get(n).setAmount(Utils.cartList.get(n).getAmount()+1);
        }else {
            Cart cart=new Cart();
            cart.setMealDetail(mealDetail);
            cart.setAmount(amount);
            Utils.cartList.add(cart);
        }
        Toast.makeText(context,"added 1 item to the cart",Toast.LENGTH_LONG).show();
        Paper.book().write("cart",Utils.cartList);
    }
}
