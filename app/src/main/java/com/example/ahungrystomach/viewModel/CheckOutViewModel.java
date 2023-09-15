package com.example.ahungrystomach.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ahungrystomach.models.Cart;
import com.example.ahungrystomach.models.MessModel;
import com.example.ahungrystomach.repository.CartRepository;
import com.example.ahungrystomach.repository.CheckOutRepository;

import java.util.List;

public class CheckOutViewModel extends ViewModel {
    private CheckOutRepository checkOutRepository;
    private MutableLiveData<MessModel> liveData;
    public void init(){
        checkOutRepository = new CheckOutRepository();
        liveData = checkOutRepository.messModelMutableLiveData();
    }
    public void checkOut(int iduser, int amount, double total, String detail, String name, String phone, String address){
        checkOutRepository.checkOut(iduser,amount,total,detail,name,phone,address);
    }
    public MutableLiveData<MessModel> messModelMutableLiveData(){
        return liveData;
    }
}
