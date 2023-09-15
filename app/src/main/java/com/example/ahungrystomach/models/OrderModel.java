package com.example.ahungrystomach.models;

import androidx.lifecycle.ViewModel;

import java.util.List;

public class OrderModel extends ViewModel {
    private boolean success;
    private String message;
    private List<Order> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Order> getResult() {
        return result;
    }

    public void setResult(List<Order> result) {
        this.result = result;
    }
}
