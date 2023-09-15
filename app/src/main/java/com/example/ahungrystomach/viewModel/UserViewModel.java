package com.example.ahungrystomach.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ahungrystomach.models.UserModel;
import com.example.ahungrystomach.repository.LoginRepository;
import com.example.ahungrystomach.repository.SignupRepository;

public class UserViewModel extends ViewModel {
    private SignupRepository signupRepository;
    private LoginRepository loginRepository;
    public void init(){
        signupRepository=new SignupRepository();
        loginRepository=new LoginRepository();
    }
    public void signUp(String name,String phone,String pass){
        signupRepository.signup(name,phone,pass);
    }
    public void loGin(String phone,String pass){
        loginRepository.login(phone,pass);
    }
    public MutableLiveData<UserModel> signupMutableLiveData(){
        return signupRepository.sigupModelMutableLiveData();
    }
    public MutableLiveData<UserModel> loginMutableLiveData(){
        return loginRepository.loginModelMutableLiveData();
    }
}
