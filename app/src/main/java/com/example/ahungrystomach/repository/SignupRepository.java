package com.example.ahungrystomach.repository;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ahungrystomach.models.UserModel;
import com.example.ahungrystomach.retrofit.FoodAppApi;
import com.example.ahungrystomach.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupRepository {
    private FoodAppApi api;
    private MutableLiveData<UserModel> data;

    public SignupRepository() {
        api= RetrofitInstance.getRetrofit().create(FoodAppApi.class);
        data=new MediatorLiveData<>();
    }
    public void signup(String name,String phone,String pass){
        if (!name.equals("")&&!phone.equals("")&&!phone.equals("")){
            api.toSignup(name,phone,pass).enqueue(new Callback<UserModel>() {
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

    public MutableLiveData<UserModel> sigupModelMutableLiveData() {
        return data;
    }
}
