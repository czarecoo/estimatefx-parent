package com.czareg.service.notblocking.polling;

import com.czareg.notifications.NotificationMessageBuilder;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.czareg.notifications.EstimateFxNotification.showErrorNotificationFromCustomThread;
import static com.czareg.service.shared.NetworkUtils.exceptionCausedByCancelingEventSource;
import static com.czareg.service.shared.NetworkUtils.exceptionCausedBySocketClosing;

public class RetryInterceptor implements Interceptor {
    private static final Logger LOG = LogManager.getLogger(RetryInterceptor.class);
    public static final long SLEEP_BEFORE_RETRY_IN_MILLIS = 13 * 1000L;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        int tryCount = 1;
        do {
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                if (exceptionCausedByCancelingEventSource(e) || exceptionCausedBySocketClosing(e)) {
                    LOG.info("Cancelled, stopping retries");
                    throw e;
                }
                String message = String.format("Processing request failed. Try %d. Retrying.", tryCount++);
                LOG.warn(message, e);
                NotificationMessageBuilder notificationMessageBuilder = new NotificationMessageBuilder();
                notificationMessageBuilder.developerMessage(message);
                notificationMessageBuilder.exceptionMessage(e);
                showErrorNotificationFromCustomThread(notificationMessageBuilder.build());
                sleep();
            }
        } while (response == null || !response.isSuccessful());
        return response;
    }

    private void sleep() {
        try {
            Thread.sleep(SLEEP_BEFORE_RETRY_IN_MILLIS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}