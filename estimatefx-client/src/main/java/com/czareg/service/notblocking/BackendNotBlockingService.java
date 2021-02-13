package com.czareg.service.notblocking;

import com.czareg.listeners.SessionIdentifiersListener;
import com.czareg.listeners.SessionListener;
import com.czareg.model.SessionIdentifier;
import javafx.scene.control.ChoiceBox;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.sse.RealEventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class BackendNotBlockingService {
    public RealEventSource pollSession(int sessionId) {
        Request request = new Request.Builder()
                .url("http://localhost/pollSession/" + sessionId)
                .build();
        EventSourceListener eventSourceListener = new SessionListener();
        RealEventSource realEventSource = new RealEventSource(request, eventSourceListener);
        realEventSource.connect(createClient());
        return realEventSource;
    }

    public RealEventSource pollSessionIdentifiers(ChoiceBox<SessionIdentifier> choiceBox) {
        Request request = new Request.Builder()
                .url("http://localhost/pollSessionIdentifiers")
                .build();
        EventSourceListener eventSourceListener = new SessionIdentifiersListener(choiceBox);
        RealEventSource realEventSource = new RealEventSource(request, eventSourceListener);
        realEventSource.connect(createClient());
        return realEventSource;
    }

    @NotNull
    private OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .readTimeout(Duration.ZERO)
                .build();
    }

}