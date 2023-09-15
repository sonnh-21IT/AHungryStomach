package com.example.ahungrystomach.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ahungrystomach.models.OrderModel;
import com.example.ahungrystomach.repository.OrderRepository;

public class OrderViewModel extends ViewModel {
    private OrderRepository orderRepository;
    public void init(){
        orderRepository=new OrderRepository();
    }
    public void getOrder(int iduser){
        orderRepository.getOrder(iduser);
    }
    public MutableLiveData<OrderModel> orderModelMutableLiveData(int iduser){
        return orderRepository.getOrder(iduser);
    }
}
