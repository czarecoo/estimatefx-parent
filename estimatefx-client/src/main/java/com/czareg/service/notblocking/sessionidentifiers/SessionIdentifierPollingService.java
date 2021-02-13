package com.czareg.service.notblocking.sessionidentifiers;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.sse.RealEventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class SessionIdentifierPollingService implements AutoCloseable {
    private final OkHttpClient client;
    private final RealEventSource realEventSource;

    public SessionIdentifierPollingService(EventSourceListener eventSourceListener) {
        Request request = new Request.Builder()
                .url("http://localhost/pollSessionIdentifiers")
                .build();
        realEventSource = new RealEventSource(request, eventSourceListener);
        client = createClient();
        realEventSource.connect(client);
    }

    @NotNull
    private OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .readTimeout(Duration.ZERO)
                .build();
    }

    @Override
    public void close() {
        realEventSource.cancel();
        client.dispatcher().executorService().shutdown();
    }
}