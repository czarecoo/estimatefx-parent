package com.czareg.service.shared;

import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;

public class ProxyFactory {
    public Proxy create(PropertiesConfiguration propertiesConfiguration) {
        String proxyHost = propertiesConfiguration.getString("proxy.host");
        Objects.requireNonNull(proxyHost, "Proxy host address is not defined in " + propertiesConfiguration.getFileName());
        Integer proxyPort = propertiesConfiguration.getInteger("proxy.port", null);
        Objects.requireNonNull(proxyPort, "Proxy port is not defined in " + propertiesConfiguration.getFileName());
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    }
}