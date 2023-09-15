package com.example.ahungrystomach.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ahungrystomach.models.SearchModel;
import com.example.ahungrystomach.repository.SearchRepository;

public class SearchViewModel extends ViewModel {
    private SearchRepository searchRepository;
    private MutableLiveData<SearchModel> liveData;
    public void init(){
        searchRepository= new SearchRepository();
        liveData= searchRepository.searchModelMutableLiveData();
    }
    public void search(String search){
        searchRepository.search(search);
    }
    public MutableLiveData<SearchModel> searchModelMutableLiveData(){
        return liveData;
    }
}
