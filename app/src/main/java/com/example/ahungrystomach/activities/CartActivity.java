package com.example.ahungrystomach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ahungrystomach.R;
import com.example.ahungrystomach.Utils.Utils;
import com.example.ahungrystomach.adapters.CartAdapter;
import com.example.ahungrystomach.databinding.ActivityCartBinding;
import com.example.ahungrystomach.listener.ChangeNumListener;
import com.example.ahungrystomach.models.Cart;
import com.example.ahungrystomach.models.MessModel;
import com.example.ahungrystomach.models.User;
import com.example.ahungrystomach.viewModel.CartViewModel;
import com.google.gson.Gson;

import java.util.List;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {
    double total;
    public static ActivityCartBinding binding;
    CartViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart);
        Paper.init(this);
        initView();
        initData();
        totalPrice();
        initControl();
    }

    private void initControl() {
        viewModel.init();
        binding.tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=Utils.user;
                if (user==null){
                    Intent intent = new Intent(getApplicationContext(),AuthActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(),CheckOutActivity.class);
                    startActivity(intent);
                }
            }
        });
        binding.tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        if (Utils.cartList!=null){
            CartAdapter adapter=new CartAdapter(this, Utils.cartList, new ChangeNumListener() {
                @Override
                public void change() {
                    totalPrice();
                }
            });
            binding.rcCart.setAdapter(adapter);
        }else {
            binding.viewCart.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void totalPrice() {
        if (Utils.cartList!=null){
            total=0.0;
            if (Utils.cartList != null||Utils.cartList.size()<=0){
                for (int i =0;i<Utils.cartList.size();i++){
                    total+=Utils.cartList.get(i).getAmount()*Utils.cartList.get(i).getMealDetail().getPrice();
                }
            }
            binding.tvAmount.setText(Utils.cartList.size()+"");
            binding.total.setText("$"+total);
        }
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding.rcCart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        binding.rcCart.setLayoutManager(layoutManager);
        List<Cart> carts= Paper.book().read("cart");
        Utils.cartList=carts;
        if(Utils.cartList==null||Utils.cartList.size()<=0){
            binding.viewCart.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }else{
            binding.viewCart.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
        }
    }
}