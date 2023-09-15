package com.example.ahungrystomach.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ahungrystomach.models.MealDetailModel;
import com.example.ahungrystomach.repository.MealDetailRepository;

public class DetailViewModel extends ViewModel {
    private MealDetailRepository mealDetailRepository;
    public DetailViewModel(){
        mealDetailRepository = new MealDetailRepository();
    }
    public MutableLiveData<MealDetailModel> mealDetailModelMutableLiveData(int id){
        return mealDetailRepository.getMealDetail(id);
    }
}
