package com.example.ahungrystomach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ahungrystomach.R;
import com.example.ahungrystomach.adapters.MealAdapter;
import com.example.ahungrystomach.databinding.ActivityCategoryBinding;
import com.example.ahungrystomach.listener.EventClickListener;
import com.example.ahungrystomach.models.Meals;
import com.example.ahungrystomach.viewModel.CategoryViewModel;

public class CategoryActivity extends AppCompatActivity implements EventClickListener {
    ActivityCategoryBinding binding ;
    CategoryViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_category);
        initView();
        initData();
        initControl();
    }

    private void initControl() {
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
        int id = getIntent().getIntExtra("idcate",1);
        String name = getIntent().getStringExtra("namecate");
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModel.mealModelMutableLiveData(id).observe(this,mealModel -> {
            if(mealModel.isSuccess()){
                MealAdapter adapter = new MealAdapter(mealModel.getResult(),this);
                binding.rccategory.setAdapter(adapter);
                binding.tvName.setText(name + ": "+mealModel.getResult().size());
            }
        });
    }

    private void initView() {
        binding.rccategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,2);
        binding.rccategory.setLayoutManager(layoutManager);
    }

    @Override
    public void onPopularClick(Meals meals) {
        Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
        intent.putExtra("idmeal",meals.getIdMeal());
        startActivity(intent);
    }
}