package com.example.ahungrystomach.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ahungrystomach.models.MealDetailModel;
import com.example.ahungrystomach.models.SearchModel;
import com.example.ahungrystomach.retrofit.FoodAppApi;
import com.example.ahungrystomach.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {
    private FoodAppApi api;
    private MutableLiveData<SearchModel> data;

    public SearchRepository() {
        api= RetrofitInstance.getRetrofit().create(FoodAppApi.class);
        data= new MutableLiveData<>();
    }

    public void search(String search){
        if (!search.equals("")){
            api.getSearch(search).enqueue(new Callback<SearchModel>() {
                @Override
                public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                    data.postValue(response.body());
                }

                @Override
                public void onFailure(Call<SearchModel> call, Throwable t) {
                    data.postValue(null);
                    Log.d("Log",t.getMessage());
                }
            });
        }
    }

    public MutableLiveData<SearchModel> searchModelMutableLiveData() {
        return data;
    }
}
