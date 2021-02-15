package com.czareg.service.notblocking.listener;

import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.czareg.service.shared.NetworkUtils.exceptionCausedByCancelingEventSource;
import static com.czareg.service.shared.NetworkUtils.exceptionCausedBySocketClosing;

public abstract class Listener extends EventSourceListener {
    private Logger logger;

    public Listener(Logger logger) {
        this.logger = logger;
    }

    protected void onEvent(String jsonObject) {
    }

    protected void onClosed() {
    }

    protected void onOpen() {
    }

    protected void onFailure(Throwable t) {
    }

    @Override
    public final void onClosed(@NotNull EventSource eventSource) {
        super.onClosed(eventSource);
        logger.info("Closed");
        onClosed();
    }

    @Override
    public final void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        super.onEvent(eventSource, id, type, data);
        logger.info("Event");
        onEvent(data);
    }

    @Override
    public final void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        super.onFailure(eventSource, t, response);

        if (exceptionCausedByCancelingEventSource(t) || exceptionCausedBySocketClosing(t)) {
            logger.info("Cancelled");
        } else {
            onFailure(t);
        }
    }

    @Override
    public final void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        super.onOpen(eventSource, response);
        logger.info("Open");
        onOpen();
    }
}