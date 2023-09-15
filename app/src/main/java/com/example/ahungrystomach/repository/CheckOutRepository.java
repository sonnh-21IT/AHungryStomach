package com.example.ahungrystomach.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ahungrystomach.models.Cart;
import com.example.ahungrystomach.models.MessModel;
import com.example.ahungrystomach.retrofit.FoodAppApi;
import com.example.ahungrystomach.retrofit.RetrofitInstance;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutRepository {
    private FoodAppApi api;
    private MutableLiveData<MessModel> data;

    public CheckOutRepository() {
        api= RetrofitInstance.getRetrofit().create(FoodAppApi.class);
        data = new MutableLiveData<>();
    }
    public void checkOut(int iduser, int amount, double total, String detail, String name, String phone, String address){
        if (!detail.equals("")){
            api.postCart(iduser,amount,total,detail,name,phone,address).enqueue(new Callback<MessModel>() {
                @Override
                public void onResponse(Call<MessModel> call, Response<MessModel> response) {
                    data.postValue(response.body());
                }

                @Override
                public void onFailure(Call<MessModel> call, Throwable t) {
                    data.postValue(null);
                    Log.d("Log",t.getMessage());
                }
            });
        }
    }
    public MutableLiveData<MessModel> messModelMutableLiveData(){
        return data;
    }
}
