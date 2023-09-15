package com.example.ahungrystomach.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.ahungrystomach.R;
import com.example.ahungrystomach.Utils.Utils;
import com.example.ahungrystomach.adapters.CategoryAdapter;
import com.example.ahungrystomach.adapters.PopularAdapter;
import com.example.ahungrystomach.databinding.ActivityHomeBinding;
import com.example.ahungrystomach.dialog.LoadingMainDialog;
import com.example.ahungrystomach.listener.CategoryListener;
import com.example.ahungrystomach.listener.EventClickListener;
import com.example.ahungrystomach.models.Category;
import com.example.ahungrystomach.models.Meals;
import com.example.ahungrystomach.models.User;
import com.example.ahungrystomach.viewModel.HomeViewModel;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements CategoryListener, EventClickListener {
    HomeViewModel homeViewModel;
    ActivityHomeBinding binding;
    LoadingMainDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        dialog=new LoadingMainDialog(this);
        Paper.init(this);
        User user=Paper.book().read("auth");
        Utils.user=user;
        initView();
        initData();
        initControl();
    }

    private void initControl() {
        binding.edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        binding.rcPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    dialog.hindDialog();
                }
            }
        });
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.user!=null){
                    Intent intent=new Intent(getApplicationContext(),SettingActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getApplicationContext(),AuthActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        if (Utils.user!=null){
            binding.tvhi.setText("Hello "+Utils.user.getName()+"!");
        }else {
            binding.tvhi.setText("Thanks for coming back!");
        }
        binding.rcCategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.rcCategory.setLayoutManager(layoutManager);

        binding.rcPopular.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rcPopular.setLayoutManager(layoutManager1);

        binding.floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),CartActivity.class);
               startActivity(intent);
            }
        });
        dialog.showDialog();
    }

    private void initData() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.categoryModelMutableLiveData().observe(this,categoryModel -> {
            if(categoryModel.isSuccess()){
                CategoryAdapter adapter=new CategoryAdapter(categoryModel.getResult(),this);
                binding.rcCategory.setAdapter(adapter);
            }
        });
        homeViewModel.mealModelMutableLiveData(1).observe(this,mealModel -> {
            if (mealModel.isSuccess()){
                PopularAdapter adapter= new PopularAdapter(mealModel.getResult(),this);
                binding.rcPopular.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onCategoryClick(Category category) {
        Intent intent=new Intent(getApplicationContext(),CategoryActivity.class);
        intent.putExtra("idcate",category.getId());
        intent.putExtra("namecate",category.getCategory());
        startActivity(intent);
    }

    @Override
    public void onPopularClick(Meals meals) {
        Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
        intent.putExtra("idmeal",meals.getIdMeal());
        startActivity(intent);
    }

}