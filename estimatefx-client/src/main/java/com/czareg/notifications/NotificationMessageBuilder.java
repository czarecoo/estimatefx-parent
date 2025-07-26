package com.czareg.notifications;

import com.czareg.utils.StringUtils;

public class NotificationMessageBuilder {
    private String developerMessage;
    private String exceptionMessage;
    private String remediation;

    public NotificationMessageBuilder developerMessage(String developerMessage) {
        this.developerMessage = developerMessage;
        return this;
    }

    public NotificationMessageBuilder exceptionMessage(Throwable throwable) {
        if (throwable != null) {
            String message = throwable.getMessage();
            if (!StringUtils.isEmpty(message)) {
                this.exceptionMessage = message;
            }
        }
        return this;
    }

    public NotificationMessageBuilder remediation(String remediation) {
        if (!StringUtils.isEmpty(remediation)) {
            this.remediation = remediation;
        }
        return this;
    }

    public String build() {
        String message = "";
        message = appendIfNotEmpty(message, developerMessage);
        message = appendIfNotEmpty(message, exceptionMessage);
        message = appendIfNotEmpty(message, remediation);
        message = appendIfNotEmpty(message, "See logs for more information.");
        return message;
    }

    private String appendIfNotEmpty(String message, String appendex) {
        if (!StringUtils.isEmpty(appendex)) {
            message = addTwoEmptyLinesIfNeeded(message);
            message += appendex;
        }
        return message;
    }

    private String addTwoEmptyLinesIfNeeded(String message) {
        if (!StringUtils.isEmpty(message)) {
            message += "\n\n";
        }
        return message;
    }
}