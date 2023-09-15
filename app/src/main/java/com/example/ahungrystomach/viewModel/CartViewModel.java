package com.example.ahungrystomach.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ahungrystomach.models.MessModel;
import com.example.ahungrystomach.repository.CartRepository;

public class CartViewModel extends ViewModel {
    private CartRepository cartRepository;
    private MutableLiveData<MessModel> mutableLiveData;
    public void init(){
        cartRepository = new CartRepository();
        mutableLiveData = cartRepository.messModelMutableLiveData();
    }
    public MutableLiveData<MessModel> messModelMutableLiveData(){
        return mutableLiveData;
    }
}
