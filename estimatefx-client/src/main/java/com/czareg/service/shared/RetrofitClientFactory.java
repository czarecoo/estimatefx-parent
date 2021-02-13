package com.czareg.service.shared;

import okhttp3.OkHttpClient;
import org.apache.commons.configuration.PropertiesConfiguration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Objects;

public class RetrofitClientFactory {
    public Retrofit createWithRxJava2CallAdapterFactory(OkHttpClient httpClient, PropertiesConfiguration propertiesConfiguration) {
        String baseUrl = propertiesConfiguration.getString("backend.url");
        Objects.requireNonNull(baseUrl, "Base url is not defined in " + propertiesConfiguration.getFileName());

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    public Retrofit createWithoutRxJava2CallAdapterFactory(OkHttpClient httpClient, PropertiesConfiguration propertiesConfiguration) {
        String baseUrl = propertiesConfiguration.getString("backend.url");
        Objects.requireNonNull(baseUrl, "Base url is not defined in " + propertiesConfiguration.getFileName());

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}