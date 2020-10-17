package com.czareg.service;

public class BackendApiFactory {
    public BackendApi createBackendApi(String baseUrl) {
        return new RetrofitClientFactory()
                .getRetrofitClient(baseUrl)
                .create(BackendApi.class);
    }
}