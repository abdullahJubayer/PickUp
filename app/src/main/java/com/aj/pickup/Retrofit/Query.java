package com.aj.pickup.Retrofit;

import android.arch.lifecycle.LiveData;

import com.aj.pickup.Model.PhotoList.PhotoListModel;
import com.aj.pickup.Model.SearchPhotoList.SearchPhotoList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Query {
    @GET("photos?per_page=100")
   Call<List<PhotoListModel>> getNewPhoto(@retrofit2.http.Query("client_id") String client_id);

    @GET("/search/photos")
   Call<SearchPhotoList> getResult(@retrofit2.http.Query("client_id") String client_id, @retrofit2.http.Query("query") String search);
}
