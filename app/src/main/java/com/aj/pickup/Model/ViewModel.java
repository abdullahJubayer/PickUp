package com.aj.pickup.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.aj.pickup.ImageRepository.Repository;
import com.aj.pickup.Model.PhotoList.PhotoListModel;
import com.aj.pickup.Model.SearchPhotoList.Result;

import java.util.List;


public class ViewModel extends AndroidViewModel {

    private static final String Key="54735667c7a576d43ecf777ac42fbc27a060a4a3a2b1d70d4be0784c0432f877";
    private LiveData<List<PhotoListModel>> newPhotoData;
    private LiveData<List<Result>> searchPhotoData;
    public ViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PhotoListModel>> getNewPhotoData(){
        if (newPhotoData == null){
            newPhotoData=Repository.getInstance().getNewPhoto(Key);
        }
        return newPhotoData;
    }
    public LiveData<List<Result>> getSearchPhotoData(String search){

        if (searchPhotoData == null){
            searchPhotoData=Repository.getInstance().getSearchPhoto(Key,search);
        }
        return searchPhotoData;
    }
}
