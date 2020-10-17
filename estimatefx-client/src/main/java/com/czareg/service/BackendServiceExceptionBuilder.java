package com.czareg.service;

import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Response;

import java.io.IOException;

class BackendServiceExceptionBuilder {
    private static final Logger LOG = LogManager.getLogger(BackendServiceExceptionBuilder.class);

    private StringBuilder stringBuilder = new StringBuilder();
    private Throwable cause;

    BackendServiceExceptionBuilder failedTo(String problemDescription) {
        String message = String.format("Failed to %s", problemDescription);
        stringBuilder.append(message);
        return this;
    }

    BackendServiceExceptionBuilder serverMessage(Response<?> response) {
        ResponseBody responseBody = response.errorBody();
        if (responseBody == null) {
            LOG.error("Response's error body is null");
            LOG.error(response);
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

    BackendServiceExceptionBuilder sessionId(int sessionId) {
        String message = String.format(", sessionId: %d", sessionId);
        stringBuilder.append(message);
        return this;
    }

    BackendServiceExceptionBuilder userName(String userName) {
        String message = String.format(", userName: %s", userName);
        stringBuilder.append(message);
        return this;
    }

    BackendServiceExceptionBuilder voteValue(String voteValue) {
        String message = String.format(", voteValue: %s", voteValue);
        stringBuilder.append(message);
        return this;
    }

    BackendServiceExceptionBuilder cause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    BackendServiceException build() {
        stringBuilder.append(".");
        String message = stringBuilder.toString();
        if (cause == null) {
            return new BackendServiceException(message);
        } else {
            return new BackendServiceException(message, cause);
        }
    }
}