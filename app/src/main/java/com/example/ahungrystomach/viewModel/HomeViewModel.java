package com.example.ahungrystomach.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ahungrystomach.models.Category;
import com.example.ahungrystomach.models.CategoryModel;
import com.example.ahungrystomach.models.MealModel;
import com.example.ahungrystomach.repository.CategoryRepository;
import com.example.ahungrystomach.repository.MealRepository;

public class HomeViewModel extends ViewModel {
    private CategoryRepository categoryRepository;
    private MealRepository mealRepository;

    public HomeViewModel(){
        categoryRepository = new CategoryRepository();
        mealRepository = new MealRepository();
    }
    public MutableLiveData<CategoryModel> categoryModelMutableLiveData(){
        return categoryRepository.getCategory();
    }
    public MutableLiveData<MealModel> mealModelMutableLiveData(int idcate){
        return mealRepository.getMeals(idcate);
    }
}
