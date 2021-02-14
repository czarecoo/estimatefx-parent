package com.czareg.service.blocking.utils;

public class BackendServiceException extends Exception {
    public BackendServiceException(String message) {
        super(message);
    }

    public BackendServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}