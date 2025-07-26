package com.czareg.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    private static final String FILE_NAME = "application.properties";

    private final Properties propertiesConfiguration = new Properties();

    public PropertiesManager() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(FILE_NAME)) {
            if (in == null) {
                throw new IllegalStateException("Properties file not found in classpath: " + FILE_NAME);
            }
            propertiesConfiguration.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + FILE_NAME, e);
        }
    }

    public int getThreadCount() {
        return Integer.parseInt(propertiesConfiguration.getProperty("thread.count", "5"));
    }

    public String getBaseUrl() {
        String baseUrl = propertiesConfiguration.getProperty("backend.url");
        requireDefined(baseUrl, "Backend url");
        return baseUrl;
    }

    public boolean shouldUseProxy() {
        return Boolean.parseBoolean(propertiesConfiguration.getProperty("proxy.enabled", "false"));
    }

    public String getProxyHost() {
        String proxyHost = propertiesConfiguration.getProperty("proxy.host");
        requireDefined(proxyHost, "Proxy host address");
        return proxyHost;
    }

    public int getProxyPort() {
        String proxyPort = propertiesConfiguration.getProperty("proxy.port");
        requireDefined(proxyPort, "Proxy port");
        return Integer.parseInt(proxyPort);
    }

    private <T> void requireDefined(T object, String objectsDescription) {
        if (object == null) {
            String message = String.format("%s is not defined in %s", objectsDescription, FILE_NAME);
            throw new IllegalStateException(message);
        }
    }
}