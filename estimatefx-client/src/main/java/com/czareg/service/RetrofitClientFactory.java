package com.czareg.service;

import okhttp3.OkHttpClient;
import org.apache.commons.configuration.PropertiesConfiguration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;

public class RetrofitClientFactory {
    public Retrofit geRetrofitClient(PropertiesConfiguration propertiesConfiguration) {
        String baseUrl = propertiesConfiguration.getString("backend.url");
        Objects.requireNonNull(baseUrl, "Base url is not defined in " + propertiesConfiguration.getFileName());

        OkHttpClient httpClient = createOkHttpClient(propertiesConfiguration);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    private OkHttpClient createOkHttpClient(PropertiesConfiguration propertiesConfiguration) {
        boolean useProxy = propertiesConfiguration.getBoolean("proxy.enabled", false);
        if (useProxy) {
            String proxyHost = propertiesConfiguration.getString("proxy.host");
            Objects.requireNonNull(proxyHost, "Proxy host address is not defined in " + propertiesConfiguration.getFileName());
            Integer proxyPort = propertiesConfiguration.getInteger("proxy.port", null);
            Objects.requireNonNull(proxyPort, "Proxy port is not defined in " + propertiesConfiguration.getFileName());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            return new OkHttpClient.Builder().proxy(proxy).build();
        } else {
            return new OkHttpClient();
        }
    }
}