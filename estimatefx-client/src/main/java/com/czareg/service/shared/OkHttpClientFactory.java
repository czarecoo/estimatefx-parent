package com.czareg.service.shared;

import com.czareg.context.PropertiesManager;
import com.czareg.service.notblocking.polling.RetryInterceptor;
import okhttp3.OkHttpClient;

import java.net.Proxy;
import java.time.Duration;

public class OkHttpClientFactory {
    private PropertiesManager propertiesManager;
    private ProxyFactory proxyFactory;

    public OkHttpClientFactory(PropertiesManager propertiesManager) {
        proxyFactory = new ProxyFactory(propertiesManager);
        this.propertiesManager = propertiesManager;
    }

    public OkHttpClient createClientForBlockingService() {
        if (propertiesManager.shouldUseProxy()) {
            Proxy proxy = proxyFactory.create();
            return new OkHttpClient.Builder()
                    .proxy(proxy)
                    .build();
        } else {
            return new OkHttpClient();
        }
    }

    public OkHttpClient createClientForPollingService() {
        if (propertiesManager.shouldUseProxy()) {
            Proxy proxy = proxyFactory.create();
            return new OkHttpClient.Builder()
                    .proxy(proxy)
                    .readTimeout(Duration.ZERO)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new RetryInterceptor())
                    .build();
        } else {
            return new OkHttpClient.Builder()
                    .readTimeout(Duration.ZERO)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new RetryInterceptor())
                    .build();
        }
    }
}