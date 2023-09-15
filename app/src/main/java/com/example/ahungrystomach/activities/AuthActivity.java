package com.example.ahungrystomach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ahungrystomach.R;
import com.example.ahungrystomach.databinding.ActivityAuthBinding;
import com.example.ahungrystomach.models.User;
import com.example.ahungrystomach.models.UserModel;
import com.example.ahungrystomach.viewModel.CheckOutViewModel;
import com.example.ahungrystomach.viewModel.UserViewModel;

import io.paperdb.Paper;

public class AuthActivity extends AppCompatActivity {
    private ActivityAuthBinding binding;
    private String pass;
    private String phone;
    private UserViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_auth);
        Paper.init(this);
        initView();
        initControl();
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.nfErrorLogin.setVisibility(View.GONE);
        binding.nfErrorSignup.setVisibility(View.GONE);
        binding.signupForm.setVisibility(View.GONE);
        binding.loginform.setVisibility(View.VISIBLE);
    }

    private void initControl() {
        viewModel.init();
        viewModel.signupMutableLiveData().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel.isSuccess()){
                    User user= userModel.getResult().get(0);
                    Paper.book().write("auth",user);
                    finish();
                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                }else{
                    binding.nfErrorSignup.setText("The phone number already exists on the system!");
                    binding.nfErrorSignup.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.loginMutableLiveData().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel.isSuccess()){
                    if (userModel.getResult().isEmpty()){
                        binding.nfErrorLogin.setText("The account does not exist on the system!");
                        binding.nfErrorLogin.setVisibility(View.VISIBLE);
                    }else{
                        User user=userModel.getResult().get(0);
                        if (user.getPass().equals(pass)){
                            Paper.book().write("auth",user);
                            finish();
                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);
                        }else{
                            binding.nfErrorLogin.setText("The password is wrong!");
                            binding.nfErrorLogin.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    binding.nfErrorLogin.setText("The account does not exist on the system!");
                    binding.nfErrorLogin.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        binding.backTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.signupForm.setVisibility(View.GONE);
                binding.loginform.setVisibility(View.VISIBLE);
            }
        });
        binding.signupLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.signupForm.setVisibility(View.VISIBLE);
                binding.loginform.setVisibility(View.GONE);
            }
        });
        binding.signupSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.edtNameSignup.getText().toString();
                String phone = binding.edtPhoneSignup.getText().toString();
                String pass = binding.edtPassSignup.getText().toString();
                String passcf = binding.edtPassCfSignup.getText().toString();

                if (name.equals("")||phone.equals("")||pass.equals("")||passcf.equals("")){
                    binding.nfErrorSignup.setText("Data fields cannot be left blank!");
                    binding.nfErrorSignup.setVisibility(View.VISIBLE);
                }else{
                    if(pass.equals(passcf)){
                        viewModel.signUp(name,phone,pass);
                    }else {
                        binding.nfErrorSignup.setText("Confirmation password is not correct!");
                        binding.nfErrorSignup.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        binding.loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = binding.edtPhoneLogin.getText().toString();
                pass=binding.edtPassLogin.getText().toString();
                if(phone.equals("")||pass.equals("")){
                    binding.nfErrorLogin.setText("Data fields cannot be left blank!");
                    binding.nfErrorLogin.setVisibility(View.VISIBLE);
                }else{
                    viewModel.loGin(phone,pass);
                }
            }
        });
    }
}