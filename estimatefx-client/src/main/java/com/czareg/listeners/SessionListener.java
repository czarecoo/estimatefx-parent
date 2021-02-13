package com.czareg.listeners;

import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SessionListener extends EventSourceListener {
    private static final Logger LOG = LogManager.getLogger(SessionListener.class);

    //gson = new Gson();
    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        super.onClosed(eventSource);
        LOG.info("Closed SessionIdentifiersListener");
    }

    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        super.onOpen(eventSource, response);
        LOG.info("Opened SessionIdentifiersListener");
    }

    @Override
    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        super.onEvent(eventSource, id, type, data);
    }

    @Override
    public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        super.onFailure(eventSource, t, response);
    }
}