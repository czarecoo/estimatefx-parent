package com.czareg.tasks;

import com.czareg.notifications.NotificationMessageBuilder;
import com.czareg.service.blocking.utils.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.Logger;

import static com.czareg.notifications.EstimateFxNotification.showErrorNotificationFromUiThread;

abstract class CustomTask extends Task<Void> {
    private Logger logger;

    CustomTask(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected Void call() throws BackendServiceException {
        process();
        return null;
    }

    abstract void process() throws BackendServiceException;

    @Override
    protected void failed() {
        logger.error("Task failed.", getException());
        NotificationMessageBuilder notificationMessageBuilder = new NotificationMessageBuilder();
        notificationMessageBuilder.exceptionMessage(getException());
        showErrorNotificationFromUiThread(notificationMessageBuilder.build());
    }
}