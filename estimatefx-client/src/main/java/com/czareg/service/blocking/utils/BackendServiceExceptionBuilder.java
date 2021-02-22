package com.czareg.service.blocking.utils;

import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Response;

import java.io.IOException;

public class BackendServiceExceptionBuilder {
    private static final Logger LOG = LogManager.getLogger(BackendServiceExceptionBuilder.class);

    private StringBuilder stringBuilder = new StringBuilder();
    private Throwable cause;

    public BackendServiceExceptionBuilder failedTo(String problemDescription) {
        String message = String.format("Failed to %s", problemDescription);
        stringBuilder.append(message);
        return this;
    }

    public BackendServiceExceptionBuilder serverMessage(Response<?> response) {
        ResponseBody responseBody = response.errorBody();
        if (responseBody == null) {
            LOG.error("Response's error body is null");
            return this;
        }
        try {
            String serverMessage = responseBody.string();
            String message = String.format(", serverMessage: %s", serverMessage);
            stringBuilder.append(message);
        } catch (IOException e) {
            LOG.error("Failed to read server error message", e);
        }

        return this;
    }

    public BackendServiceExceptionBuilder parameter(String parameterName, Object parameterValue) {
        String message = String.format(", %s: %s", parameterName, parameterValue);
        stringBuilder.append(message);
        return this;
    }

    public BackendServiceExceptionBuilder cause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public BackendServiceException build() {
        stringBuilder.append(".");
        String message = stringBuilder.toString();
        if (cause == null) {
            return new BackendServiceException(message);
        } else {
            return new BackendServiceException(message, cause);
        }
    }
}