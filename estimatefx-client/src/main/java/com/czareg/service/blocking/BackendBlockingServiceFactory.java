package com.czareg.service.blocking;

import com.czareg.context.PropertiesManager;
import com.czareg.service.blocking.utils.RetrofitClientFactory;
import com.czareg.service.shared.OkHttpClientFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class BackendBlockingServiceFactory {
    private final OkHttpClientFactory okHttpClientFactory;
    private final RetrofitClientFactory retrofitClientFactory;

    public BackendBlockingServiceFactory(PropertiesManager propertiesManager) {
        okHttpClientFactory = new OkHttpClientFactory(propertiesManager);
        retrofitClientFactory = new RetrofitClientFactory(propertiesManager);
    }

    public BackendBlockingService create() {
        OkHttpClient okHttpClient = okHttpClientFactory.createClientForBlockingService();
        Retrofit retrofit = retrofitClientFactory.create(okHttpClient);
        BackendBlockingApi backendBlockingApi = retrofit.create(BackendBlockingApi.class);
        return new BackendBlockingServiceImpl(backendBlockingApi);
    }
}