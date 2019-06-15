package com.aj.pickup.ImageRepository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.aj.pickup.Model.PhotoList.PhotoListModel;
import com.aj.pickup.Model.SearchPhotoList.Result;
import com.aj.pickup.Model.SearchPhotoList.SearchPhotoList;
import com.aj.pickup.Retrofit.Query;
import com.aj.pickup.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository repository;
    private Repository(){
    }

    public static Repository getInstance(){
            repository=new Repository();
            return repository;
    }

    private static final Query query= RetrofitClient.getClient().create(Query.class);

    public LiveData<List<PhotoListModel>> getNewPhoto(String key){

        final MutableLiveData<List<PhotoListModel>> dataList=new MutableLiveData<>();
        query.getNewPhoto(key).enqueue(new Callback<List<PhotoListModel>>() {
            @Override
            public void onResponse(Call<List<PhotoListModel>> call, Response<List<PhotoListModel>> response) {
                if (response.isSuccessful()){
                    dataList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PhotoListModel>> call, Throwable t) {
                dataList.setValue(null);
            }
        });
        return dataList;
    }

    public LiveData<List<Result>> getSearchPhoto(String key,String search){

        final MutableLiveData<List<Result>> dataList=new MutableLiveData<>();

        query.getResult(key,search).enqueue(new Callback<SearchPhotoList>() {
            @Override
            public void onResponse(Call<SearchPhotoList> call, Response<SearchPhotoList> response) {
                if (response.isSuccessful()){
                    if (response.body().getResults() ==null){
                        return;
                    }else {
                        dataList.setValue(response.body().getResults());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchPhotoList> call, Throwable t) {
                dataList.setValue(null);
            }
        });

        return dataList;
    }

}
