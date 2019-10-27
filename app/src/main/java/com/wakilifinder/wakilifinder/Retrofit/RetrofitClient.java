package com.wakilifinder.wakilifinder.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit instance;

    public static Retrofit getInstance() {
        if(instance == null){

            instance = new Retrofit.Builder()
                    .baseUrl("https://frozen-thicket-19759.herokuapp.com/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return instance;
    }
}
