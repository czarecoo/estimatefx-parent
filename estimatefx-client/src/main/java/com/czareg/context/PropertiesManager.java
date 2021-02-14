package com.czareg.context;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesManager {
    private final PropertiesConfiguration propertiesConfiguration;

    public PropertiesManager() throws ConfigurationException {
        propertiesConfiguration = new PropertiesConfiguration("application.properties");
        propertiesConfiguration.setThrowExceptionOnMissing(true);
    }

    public String getBaseUrl() {
        String baseUrl = propertiesConfiguration.getString("backend.url");
        requireDefined(baseUrl, "Backend url");
        return baseUrl;
    }

    public boolean shouldUseProxy() {
        return propertiesConfiguration.getBoolean("proxy.enabled", false);
    }

    public String getProxyHost() {
        String proxyHost = propertiesConfiguration.getString("proxy.host");
        requireDefined(proxyHost, "Proxy host address");
        return proxyHost;
    }

    public int getProxyPort() {
        Integer proxyPort = propertiesConfiguration.getInteger("proxy.port", null);
        requireDefined(proxyPort, "Proxy port");
        return proxyPort;
    }

    private <T> void requireDefined(T object, String objectsDescription) {
        if (object == null) {
            String message = String.format("%s is not defined in %s", objectsDescription, propertiesConfiguration.getFileName());
            throw new IllegalStateException(message);
        }
    }
}