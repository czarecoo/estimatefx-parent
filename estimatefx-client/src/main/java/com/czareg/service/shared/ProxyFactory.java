package com.czareg.service.shared;

import com.czareg.context.PropertiesManager;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyFactory {
    private PropertiesManager propertiesManager;

    public ProxyFactory(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    public Proxy create() {
        String proxyHost = propertiesManager.getProxyHost();
        Integer proxyPort = propertiesManager.getProxyPort();
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    }
}