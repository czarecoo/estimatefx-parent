package com.czareg.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ClosableClient implements AutoCloseable {
    private Client client;

    public ClosableClient() {
        client = ClientBuilder.newClient();
    }

    public WebTarget target(String baseUrl) {
        return client.target(baseUrl);
    }

    @Override
    public void close() {
        client.close();
    }
}
