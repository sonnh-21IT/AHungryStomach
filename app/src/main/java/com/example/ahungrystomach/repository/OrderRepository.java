package com.example.ahungrystomach.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ahungrystomach.models.OrderModel;
import com.example.ahungrystomach.models.UserModel;
import com.example.ahungrystomach.retrofit.FoodAppApi;
import com.example.ahungrystomach.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    private FoodAppApi api;
    private MutableLiveData<OrderModel> data;
    public OrderRepository(){
        api= RetrofitInstance.getRetrofit().create(FoodAppApi.class);
        data=new MutableLiveData<>();
    }
    public MutableLiveData<OrderModel> getOrder(int iduser){
        if (iduser>=0){
            api.orderUser(iduser).enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    data.postValue(response.body());
                }

                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    data.postValue(null);
                    Log.d("Log",t.getMessage());
                }
            });
        }
        return data;
    }
}
