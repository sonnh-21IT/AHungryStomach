package com.example.ahungrystomach.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahungrystomach.R;
import com.example.ahungrystomach.Utils.Utils;
import com.example.ahungrystomach.adapters.SearchAdapter;
import com.example.ahungrystomach.adapters.SearchedAdapter;
import com.example.ahungrystomach.databinding.ActivitySearchBinding;
import com.example.ahungrystomach.listener.DetailListener;
import com.example.ahungrystomach.listener.SearchedListener;
import com.example.ahungrystomach.models.Cart;
import com.example.ahungrystomach.models.MealDetail;
import com.example.ahungrystomach.viewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SearchActivity extends AppCompatActivity{
    SearchViewModel viewModel;
    ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.search.requestFocus();
        Paper.init(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.search, InputMethodManager.SHOW_IMPLICIT);
        initView();
        initControl();
    }

    private void initControl() {
        viewModel.searchModelMutableLiveData().observe(this, searchModel -> {
            if (searchModel.isSuccess()) {
                SearchAdapter adapter = new SearchAdapter(searchModel.getResult(), new DetailListener() {
                    @Override
                    public void detail(MealDetail mealDetail) {
                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        intent.putExtra("idmeal", mealDetail.getId());
                        startActivity(intent);
                    }
                }, getApplicationContext());
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
                binding.rcResult.setLayoutManager(layoutManager);
                binding.rcResult.setAdapter(adapter);
                binding.rcResult.setVisibility(View.VISIBLE);
                binding.searchOnFailed.setVisibility(View.GONE);
                if (searchModel.getStatus()==0){
                    binding.rcResult.setVisibility(View.GONE);
                    binding.searchOnFailed.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (
                        i == EditorInfo.IME_ACTION_SEARCH
                                || i == EditorInfo.IME_ACTION_DONE
                                || keyEvent.getAction() == keyEvent.ACTION_DOWN
                                || keyEvent.getAction() == keyEvent.KEYCODE_ENTER
                ) {
                    viewModel.search(binding.search.getText().toString());
                    boolean checkExit=false;
                    Utils.listsearch=Paper.book().read("search");
                    int n=0;
                    if (Utils.listsearch!=null){
                        if (Utils.listsearch.size()>0){
                            for (int j = 0;j<Utils.listsearch.size();j++){
                                if (Utils.listsearch.get(j).equalsIgnoreCase(binding.search.getText().toString())){
                                    n=j;
                                    checkExit=true;
                                }
                            }
                        }
                    }else {
                        Utils.listsearch=new ArrayList<>();
                    }
                    if (!checkExit){
                        Utils.listsearch.add(binding.search.getText().toString());
                        if (Utils.listsearch.size()>7){
                            Utils.listsearch.remove(0);
                        }
                    }else{
                        Utils.listsearch.remove(n);
                        Utils.listsearch.add(binding.search.getText().toString());
                    }
                    Paper.book().write("search",Utils.listsearch);
                }
                return false;
            }
        });
        binding.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> search= Paper.book().read("search");
                if (search!=null){
                    List<String> temp=new ArrayList<>();
                    for (int i = search.size()-1;i>=0;i--){
                        temp.add(search.get(i));
                    }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    binding.rcResult.setLayoutManager(layoutManager);
                    SearchedAdapter adapter=new SearchedAdapter(temp, new SearchedListener() {
                        @Override
                        public void onclick(String search) {
                            viewModel.search(search);
                            binding.search.setText(search);
                        }
                    });
                    binding.rcResult.setAdapter(adapter);
                }
                binding.rcResult.setVisibility(View.VISIBLE);
                binding.searchOnFailed.setVisibility(View.GONE);
                binding.search.setText("");
            }
        });
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel.init();
        binding.rcResult.setHasFixedSize(true);
        List<String> search= Paper.book().read("search");
        if (search!=null){
            List<String> temp=new ArrayList<>();
            for (int i = search.size()-1;i>=0;i--){
                temp.add(search.get(i));
            }
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            binding.rcResult.setLayoutManager(layoutManager);
            SearchedAdapter adapter=new SearchedAdapter(temp, new SearchedListener() {
                @Override
                public void onclick(String search) {
                    viewModel.search(search);
                    binding.search.setText(search);
                }
            });
            binding.rcResult.setAdapter(adapter);
        }
    }
}