package com.example.ahungrystomach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahungrystomach.R;
import com.example.ahungrystomach.Utils.Utils;
import com.example.ahungrystomach.databinding.ActivityDetailBinding;
import com.example.ahungrystomach.models.Cart;
import com.example.ahungrystomach.models.MealDetail;
import com.example.ahungrystomach.viewModel.DetailViewModel;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import okhttp3.internal.Util;

public class DetailActivity extends AppCompatActivity {
    MealDetail mealDetail;
    int temp;
    DetailViewModel viewmodel;
    ActivityDetailBinding binding;
    int amount =1;
    double total=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_detail);
        Paper.init(this);
        int id = getIntent().getIntExtra("idmeal",0);
        binding.tvCheck.setVisibility(View.GONE);
        getData(id);
        eventClick();
//        showToCart(id);
    }

//    private void showToCart(int id) {
//        if(Paper.book().read("cart")!=null){
//            Utils.cartList=Paper.book().read("cart");
//        }
//        if(Utils.cartList.size()>0){
//            for (int i =0;i<Utils.cartList.size();i++){
//                if(Utils.cartList.get(i).getMealDetail().getId()==id){
//                    binding.tvAmount.setText(String.valueOf(Utils.cartList.get(i).getAmount()));
//                }
//            }
//        }else{
//            binding.tvAmount.setText(String.valueOf(amount));
//        }
//    }

    private void eventClick() {
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Integer.parseInt(binding.tvAmount.getText().toString())+1;
                total = amount * mealDetail.getPrice();
                binding.tvAddcartPrice.setText("$"+String.valueOf(total));
                binding.tvAmount.setText(String.valueOf(amount));
            }
        });
        binding.tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(binding.tvAmount.getText().toString())>1){
                    amount = Integer.parseInt(binding.tvAmount.getText().toString())-1;
                    total = amount * mealDetail.getPrice();
                    binding.tvAddcartPrice.setText("$"+String.valueOf(total));
                    binding.tvAmount.setText(String.valueOf(amount));
                }
            }
        });
        binding.tvAddcartPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(amount);
            }
        });
    }

    private void addToCart(int amount) {
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
            Utils.cartList.get(n).setAmount(Utils.cartList.get(n).getAmount()+Integer.parseInt(binding.tvAmount.getText().toString()));
        }else {
            Cart cart=new Cart();
            cart.setMealDetail(mealDetail);
            cart.setAmount(amount);
            Utils.cartList.add(cart);
        }
        binding.tvCheck.setVisibility(View.VISIBLE);
        binding.tvCheck.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.tvCheck.setVisibility(View.GONE);
            }
        }, 2000);
        Paper.book().write("cart",Utils.cartList);
    }

    private void getData(int id) {
        viewmodel = new ViewModelProvider(this).get(DetailViewModel.class);
        viewmodel.mealDetailModelMutableLiveData(id).observe(this,mealDetailModel -> {
            if(mealDetailModel.isSuccess()){
                mealDetail = mealDetailModel.getResult().get(0);
                temp=amount;
                if(Paper.book().read("cart")!=null){
                    Utils.cartList=Paper.book().read("cart");
                    for (int i =0;i<Utils.cartList.size();i++){
                        if(Utils.cartList.get(i).getMealDetail().getId()==mealDetail.getId()){
                            temp=Utils.cartList.get(i).getAmount();
                        }
                    }
                }
                binding.collapingToolbar.setTitle(mealDetail.getMeal());
                binding.tvAddcartPrice.setText("$"+mealDetail.getPrice());
                binding.tvAmount.setText("1");
                binding.tvInstrucionsStr.setText(mealDetail.getInstructions());
                binding.tvArea.setText("Area: "+mealDetail.getArea());
                binding.tvCategories.setText("Category: "+mealDetail.getCategory());
                Glide.with(this).load(mealDetail.getStrmealthumb()).into(binding.img);
            }
        });
    }
}