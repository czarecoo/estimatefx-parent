package com.czareg.service;

public class BackendServiceCallFailedException extends Exception {
    public BackendServiceCallFailedException(String message) {
        super(message);
    }

    public BackendServiceCallFailedException(Throwable cause) {
        super(cause);
    }
}
