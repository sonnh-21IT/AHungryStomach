package com.example.ahungrystomach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ahungrystomach.R;
import com.example.ahungrystomach.Utils.Utils;
import com.example.ahungrystomach.adapters.CategoryAdapter;
import com.example.ahungrystomach.adapters.OrderAdapter;
import com.example.ahungrystomach.databinding.ActivityOrderBinding;
import com.example.ahungrystomach.models.OrderModel;
import com.example.ahungrystomach.models.User;
import com.example.ahungrystomach.viewModel.OrderViewModel;
import com.example.ahungrystomach.viewModel.SearchViewModel;

import io.paperdb.Paper;

public class OrderActivity extends AppCompatActivity {
    ActivityOrderBinding binding;
    private OrderViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order);
        Paper.init(this);
        initView();
        initControl();
    }

    private void initControl() {
        if (Utils.user!=null){
            viewModel.orderModelMutableLiveData(Utils.user.getId()).observe(this, new Observer<OrderModel>() {
                @Override
                public void onChanged(OrderModel orderModel) {
                    if(orderModel.isSuccess()){
                        OrderAdapter adapter=new OrderAdapter(orderModel.getResult());
                        binding.rcOrder.setAdapter(adapter);
                        if(binding.rcOrder.getChildCount()>0){
                            binding.rcOrder.setVisibility(View.GONE);
                            binding.tvEmpty.setVisibility(View.VISIBLE);
                            binding.isEmpty.setVisibility(View.VISIBLE);
                        }else{
                            binding.rcOrder.setVisibility(View.VISIBLE);
                            binding.tvEmpty.setVisibility(View.GONE);
                            binding.isEmpty.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        Utils.user=Paper.book().read("auth");
        binding.rcOrder.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rcOrder.setLayoutManager(layoutManager);
        binding.rcOrder.setVisibility(View.VISIBLE);
        binding.tvEmpty.setVisibility(View.GONE);
        binding.isEmpty.setVisibility(View.GONE);
        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        viewModel.init();
    }
}