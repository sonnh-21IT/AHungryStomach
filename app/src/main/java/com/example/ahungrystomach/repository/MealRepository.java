package com.example.ahungrystomach.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ahungrystomach.models.MealModel;
import com.example.ahungrystomach.retrofit.FoodAppApi;
import com.example.ahungrystomach.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRepository {
    private FoodAppApi api;

    public MealRepository() {
        api = RetrofitInstance.getRetrofit().create(FoodAppApi.class);
    }
    public MutableLiveData<MealModel> getMeals(int idcate){
        MutableLiveData<MealModel> data=new MutableLiveData<>();
        api.getMeals(idcate).enqueue(new Callback<MealModel>(){

            @Override
            public void onResponse(Call<MealModel> call, Response<MealModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MealModel> call, Throwable t) {
                Log.d("log",t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
