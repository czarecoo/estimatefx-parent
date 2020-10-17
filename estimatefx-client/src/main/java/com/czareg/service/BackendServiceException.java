package com.czareg.service;

public class BackendServiceException extends Exception {
    public BackendServiceException(String message) {
        super(message);
    }

    public BackendServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}