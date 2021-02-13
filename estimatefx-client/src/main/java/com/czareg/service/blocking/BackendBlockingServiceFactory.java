package com.czareg.service.blocking;

import com.czareg.service.shared.OkHttpClientFactory;
import com.czareg.service.shared.RetrofitClientFactory;
import okhttp3.OkHttpClient;
import org.apache.commons.configuration.PropertiesConfiguration;
import retrofit2.Retrofit;

public class BackendBlockingServiceFactory {
    private OkHttpClientFactory okHttpClientFactory = new OkHttpClientFactory();
    private RetrofitClientFactory retrofitClientFactory = new RetrofitClientFactory();

    public BackendBlockingService create(PropertiesConfiguration propertiesConfiguration) {
        OkHttpClient okHttpClient = okHttpClientFactory.createWithDefaultTimeouts(propertiesConfiguration);
        Retrofit retrofit = retrofitClientFactory.createWithoutRxJava2CallAdapterFactory(okHttpClient, propertiesConfiguration);
        BackendBlockingApi backendBlockingApi = retrofit.create(BackendBlockingApi.class);
        return new BackendBlockingServiceImpl(backendBlockingApi);
    }
}