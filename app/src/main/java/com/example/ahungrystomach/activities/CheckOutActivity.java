package com.example.ahungrystomach.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahungrystomach.R;
import com.example.ahungrystomach.Utils.Utils;
import com.example.ahungrystomach.adapters.CheckoutAdpater;
import com.example.ahungrystomach.databinding.ActivityCheckOutBinding;
import com.example.ahungrystomach.dialog.LoadingDialogBar;
import com.example.ahungrystomach.models.Cart;
import com.example.ahungrystomach.models.MessModel;
import com.example.ahungrystomach.viewModel.CheckOutViewModel;
import com.google.gson.Gson;

import java.util.List;

import io.paperdb.Paper;

public class CheckOutActivity extends AppCompatActivity {
    ActivityCheckOutBinding binding;
    double total;
    private CheckOutViewModel viewModel;

    LoadingDialogBar loadingDialogBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_out);
        initView();
        initData();
        initControl();
    }

    private void initControl() {
        viewModel.init();
        viewModel.messModelMutableLiveData().observe(this, new Observer<MessModel>() {
            @Override
            public void onChanged(MessModel messModel) {
                if (messModel.isSuccess()) {
                    LoadingDialogBar.progressBar.setVisibility(View.GONE);
                    LoadingDialogBar.imageView.setVisibility(View.VISIBLE);
                    LoadingDialogBar.textTitle.setText("Order Success!");
                    LoadingDialogBar.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(home);
                            finish();
                            Utils.cartList.clear();
                            Paper.book().delete("cart");
                        }
                    });
                }
            }
        });
        binding.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkOrder()) {
                    if (Utils.cartList != null) {
                        String cart = new Gson().toJson(Utils.cartList);
                        String name = binding.edtName.getText().toString();
                        String phone = binding.edtPhone.getText().toString();
                        String district = binding.edtDistric.getText().toString();
                        String city = binding.edtCity.getText().toString();
                        String location = binding.edtLocation.getText().toString();
                        String address = location + " " + district + " " + city;
                        Log.d("Log", cart);
                        loadingDialogBar.showDialog("Loading...");
                        Utils.user=Paper.book().read("auth");
                        viewModel.checkOut(Utils.user.getId(), Utils.cartList.size(), total, cart, name, phone, address);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "An error occurred, please check again!", Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String error = "";
                if (TextUtils.isEmpty(binding.edtPhone.getText())) {
                    error = "The phone number field cannot be left blank!";
                } else {
                    if (!Patterns.PHONE.matcher(binding.edtPhone.getText()).matches()) {
                        error = "Invalid phone number!";
                    } else {
                        error = "";
                    }
                }
                binding.nfErrorNamePhone.setText(error);
                if (TextUtils.isEmpty(error)) {
                    binding.nfErrorNamePhone.setVisibility(View.GONE);
                } else {
                    binding.nfErrorNamePhone.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(binding.edtName.getText())) {
                    String errorOld = "";
                    String error = "";
                    if (!TextUtils.isEmpty(binding.nfErrorNamePhone.getText())) {
                        errorOld = binding.nfErrorNamePhone.getText().toString();
                    }
                    if (TextUtils.isEmpty(binding.edtName.getText())) {
                        error = errorOld + "The name field cannot be left blank! ";
                    } else {
                        error = "";
                    }
                    binding.nfErrorNamePhone.setText(error);
                    if (TextUtils.isEmpty(error)) {
                        binding.nfErrorNamePhone.setVisibility(View.GONE);
                    } else {
                        binding.nfErrorNamePhone.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edtDistric.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String error = "";
                if (TextUtils.isEmpty(binding.edtDistric.getText())) {
                    error = "The district field cannot be left blank!";
                } else {
                    error = "";
                }
                binding.nfErrorDistric.setText(error);
                if (TextUtils.isEmpty(error)) {
                    binding.nfErrorDistric.setVisibility(View.GONE);
                } else {
                    binding.nfErrorDistric.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edtCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String error = "";
                if (TextUtils.isEmpty(binding.edtCity.getText())) {
                    error = "The city field cannot be left blank!";
                } else {
                    error = "";
                }
                binding.nfErrorCity.setText(error);
                if (TextUtils.isEmpty(error)) {
                    binding.nfErrorCity.setVisibility(View.GONE);
                } else {
                    binding.nfErrorCity.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edtLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String error = "";
                if (TextUtils.isEmpty(binding.edtLocation.getText())) {
                    error = "The location field cannot be left blank!";
                } else {
                    error = "";
                }
                binding.nfErrorLocation.setText(error);
                if (TextUtils.isEmpty(error)) {
                    binding.nfErrorLocation.setVisibility(View.GONE);
                } else {
                    binding.nfErrorLocation.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edtCommune.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String error = "";
                if (TextUtils.isEmpty(binding.edtCommune.getText())) {
                    error = "The commune field cannot be left blank!";
                } else {
                    error = "";
                }
                binding.nfErrorCommune.setText(error);
                if (TextUtils.isEmpty(error)) {
                    binding.nfErrorCommune.setVisibility(View.GONE);
                } else {
                    binding.nfErrorCommune.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean checkOrder() {
        if (binding.nfErrorCity.isShown() || binding.nfErrorCommune.isShown() || binding.nfErrorDistric.isShown() || binding.nfErrorLocation.isShown() || binding.nfErrorNamePhone.isShown()) {
            return false;
        } else {
            if (binding.payMethod.isChecked()) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Please choose a payment method", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private void initData() {
        List<Cart> carts = Paper.book().read("cart");
        Utils.cartList = carts;
        if (Utils.cartList != null) {
            for (int i = 0; i < Utils.cartList.size(); i++) {
                total += Utils.cartList.get(i).getAmount() * Utils.cartList.get(i).getMealDetail().getPrice();
            }
            CheckoutAdpater adapter = new CheckoutAdpater(this, Utils.cartList);
            binding.rcItem.setAdapter(adapter);
            binding.tvPay.setText("Order: $" + total);
        }
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(CheckOutViewModel.class);
        binding.rcItem.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcItem.setLayoutManager(layoutManager);
        binding.nfErrorNamePhone.setText("The phone number field cannot be left blank!");
        binding.nfErrorNamePhone.setVisibility(View.VISIBLE);
        loadingDialogBar = new LoadingDialogBar(this);
    }
}