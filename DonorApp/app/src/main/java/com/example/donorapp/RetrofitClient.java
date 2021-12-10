package com.example.donorapp;



import com.example.donorapp.Activity.ConstantsStrings;
import com.example.donorapp.Interface.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private Api myApi;
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
    private RetrofitClient() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantsStrings.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        myApi = retrofit.create(Api.class);
    }

    public static synchronized com.example.donorapp.RetrofitClient getInstance() {
        if (instance == null) {
            instance = new com.example.donorapp.RetrofitClient();
        }
        return instance;
    }

    public Api getMyApi() {
        return myApi;
    }
}

