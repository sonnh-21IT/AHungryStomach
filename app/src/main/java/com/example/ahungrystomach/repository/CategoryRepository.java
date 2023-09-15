package com.example.ahungrystomach.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ahungrystomach.models.CategoryModel;
import com.example.ahungrystomach.retrofit.FoodAppApi;
import com.example.ahungrystomach.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private FoodAppApi foodAppApi;

    public CategoryRepository(){
        foodAppApi= RetrofitInstance.getRetrofit().create(FoodAppApi.class);
    }
    public MutableLiveData<CategoryModel> getCategory(){
        MutableLiveData<CategoryModel> data = new MutableLiveData<>();
        foodAppApi.getCategory().enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Log.d("log",t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
