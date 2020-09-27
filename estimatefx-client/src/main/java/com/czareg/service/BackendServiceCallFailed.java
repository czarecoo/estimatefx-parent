package com.czareg.service;

public class BackendServiceCallFailed extends Exception {
    public BackendServiceCallFailed(String message) {
        super(message);
    }

    public BackendServiceCallFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
