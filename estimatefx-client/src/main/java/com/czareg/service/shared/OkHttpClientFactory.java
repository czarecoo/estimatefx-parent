package com.czareg.service.shared;

import okhttp3.OkHttpClient;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.Proxy;

public class OkHttpClientFactory {
    public OkHttpClient createWithDefaultTimeouts(PropertiesConfiguration propertiesConfiguration) {
        boolean useProxy = propertiesConfiguration.getBoolean("proxy.enabled", false);
        if (useProxy) {
            Proxy proxy = new ProxyFactory().create(propertiesConfiguration);
            return new OkHttpClient.Builder()
                    .proxy(proxy)
                    .build();
        } else {
            return new OkHttpClient();
        }
    }
}