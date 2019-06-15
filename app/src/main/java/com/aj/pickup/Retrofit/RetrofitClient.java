package com.aj.pickup.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getClient(){
        return new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
