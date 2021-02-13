package com.czareg.service.notblocking;

import com.czareg.service.shared.OkHttpClientFactory;
import com.czareg.service.shared.RetrofitClientFactory;
import okhttp3.OkHttpClient;
import org.apache.commons.configuration.PropertiesConfiguration;
import retrofit2.Retrofit;

public class BackendNotBlockingServiceFactory {
    private OkHttpClientFactory okHttpClientFactory = new OkHttpClientFactory();
    private RetrofitClientFactory retrofitClientFactory = new RetrofitClientFactory();

    public BackendNotBlockingService create(PropertiesConfiguration propertiesConfiguration) {
        OkHttpClient okHttpClient = okHttpClientFactory.createWithoutTimeouts(propertiesConfiguration);
        Retrofit retrofit = retrofitClientFactory.createWithRxJava2CallAdapterFactory(okHttpClient, propertiesConfiguration);
        BackendNotBlockingApi backendNotBlockingApi = retrofit.create(BackendNotBlockingApi.class);
        return new BackendNotBlockingServiceImpl(backendNotBlockingApi);
    }
}