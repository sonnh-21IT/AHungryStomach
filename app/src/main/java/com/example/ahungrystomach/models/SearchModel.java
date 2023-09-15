package com.example.ahungrystomach.models;

import java.util.List;

public class SearchModel {
    private boolean success;
    private String message;
    private List<MealDetail> result;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public List<MealDetail> getResult() {
        return result;
    }

    public void setResult(List<MealDetail> result) {
        this.result = result;
    }
}
