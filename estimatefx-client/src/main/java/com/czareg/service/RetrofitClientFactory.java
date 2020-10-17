package com.czareg.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientFactory {
    public Retrofit getRetrofitClient(String baseUrl) {
        OkHttpClient httpClient = new OkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}