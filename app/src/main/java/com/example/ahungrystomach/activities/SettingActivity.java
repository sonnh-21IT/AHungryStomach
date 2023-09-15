package com.example.ahungrystomach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ahungrystomach.R;
import com.example.ahungrystomach.Utils.Utils;
import com.example.ahungrystomach.databinding.ActivitySettingBinding;
import com.example.ahungrystomach.dialog.LogoutDialog;
import com.example.ahungrystomach.listener.SettingListener;
import com.example.ahungrystomach.models.User;
import com.example.ahungrystomach.models.UserModel;
import com.example.ahungrystomach.viewModel.DetailViewModel;
import com.example.ahungrystomach.viewModel.UserViewModel;

import io.paperdb.Paper;

public class SettingActivity extends AppCompatActivity implements SettingListener {
    private ActivitySettingBinding binding;
    private LogoutDialog logoutDialog;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_setting);
        Paper.init(this);
        initView();
        initControl();
    }

    private void initView() {
        user=Paper.book().read("auth");
        binding.tvPhone.setText("PHONE: "+String.valueOf(user.getPhone()));
        binding.tvName.setText("NAME: "+user.getName());
        logoutDialog=new LogoutDialog(this, this);
    }

    private void initControl() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        binding.toLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog.showDialog();
            }
        });
        binding.toCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        binding.toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSayYesDialog() {
        logoutDialog.hindDialog();
        finish();
        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        Paper.book().delete("auth");
    }
}