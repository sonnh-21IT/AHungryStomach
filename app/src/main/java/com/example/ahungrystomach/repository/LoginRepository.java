package com.example.ahungrystomach.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ahungrystomach.models.UserModel;
import com.example.ahungrystomach.retrofit.FoodAppApi;
import com.example.ahungrystomach.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private FoodAppApi api;
    private MutableLiveData<UserModel> data;
    public LoginRepository(){
        api= RetrofitInstance.getRetrofit().create(FoodAppApi.class);
        data=new MutableLiveData<>();
    }
    public void login(String phone,String pass){
        if (!phone.equals("") && !pass.equals("")) {
            api.toLogin(phone,pass).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    data.postValue(response.body());
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    data.postValue(null);
                    Log.d("Log",t.getMessage());
                }
            });
        }
    }
    public MutableLiveData<UserModel> loginModelMutableLiveData(){
        return data;
    }
}
