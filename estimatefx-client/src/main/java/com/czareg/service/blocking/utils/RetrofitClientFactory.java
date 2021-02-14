package com.czareg.service.blocking.utils;

import com.czareg.context.PropertiesManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientFactory {
    private PropertiesManager propertiesManager;

    public RetrofitClientFactory(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    public Retrofit create(OkHttpClient httpClient) {
        String baseUrl = propertiesManager.getBaseUrl();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}