package com.example.ahungrystomach.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ahungrystomach.models.MessModel;
import com.example.ahungrystomach.retrofit.FoodAppApi;
import com.example.ahungrystomach.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {
    private FoodAppApi api;
    private MutableLiveData<MessModel> data;

    public CartRepository() {
        api=RetrofitInstance.getRetrofit().create(FoodAppApi.class);
        data = new MutableLiveData<>();
    }

//    public void checkOut(int iduser,int amount,double total,String detail){
//        if (detail==""){
//            api.postCart(iduser,amount,total,detail).enqueue(new Callback<MessModel>() {
//                @Override
//                public void onResponse(Call<MessModel> call, Response<MessModel> response) {
//                    data.postValue(response.body());
//                }
//
//                @Override
//                public void onFailure(Call<MessModel> call, Throwable t) {
//                    data.postValue(null);
//                    Log.d("Log",t.getMessage());
//                }
//            });
//        }
//    }
    public MutableLiveData<MessModel> messModelMutableLiveData(){
        return data;
    }
}
