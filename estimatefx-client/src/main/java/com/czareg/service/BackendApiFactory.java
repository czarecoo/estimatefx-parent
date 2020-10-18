package com.czareg.service;

import retrofit2.Retrofit;

public class BackendApiFactory {
    public BackendApi createBackendApi(Retrofit retrofit) {
        return retrofit.create(BackendApi.class);
    }
}