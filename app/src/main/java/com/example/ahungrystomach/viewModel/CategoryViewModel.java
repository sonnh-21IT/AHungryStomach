package com.example.ahungrystomach.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ahungrystomach.models.MealModel;
import com.example.ahungrystomach.repository.MealRepository;

public class CategoryViewModel extends ViewModel {
    private MealRepository mealRepository ;
    public CategoryViewModel(){
        mealRepository = new MealRepository();
    }
    public MutableLiveData<MealModel> mealModelMutableLiveData(int idcate){
        return  mealRepository.getMeals(idcate);
    }
}
