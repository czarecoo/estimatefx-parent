package com.czareg.service.notblocking.sessionidentifiers;

import com.czareg.model.SessionIdentifier;
import com.czareg.notifications.EstimateFxNotification;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class SessionIdentifiersListener extends EventSourceListener {
    private static final Logger LOG = LogManager.getLogger(SessionIdentifiersListener.class);

    private ChoiceBox<SessionIdentifier> choiceBox;
    private Gson gson;
    private Type sessionIdentifierListType;

    public SessionIdentifiersListener(ChoiceBox<SessionIdentifier> choiceBox) {
        this.choiceBox = choiceBox;
        gson = new Gson();
        sessionIdentifierListType = new TypeToken<ArrayList<SessionIdentifier>>() {
        }.getType();
    }

    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        super.onClosed(eventSource);
        LOG.info("Closed");
    }

    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        super.onOpen(eventSource, response);
        LOG.info("Open");
    }

    @Override
    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        super.onEvent(eventSource, id, type, data);
        LOG.info("Event");
        List<SessionIdentifier> sessionIdentifiers = gson.fromJson(data, sessionIdentifierListType);

        Platform.runLater(() -> updateChoiceBox(sessionIdentifiers));
    }

    @Override
    public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        super.onFailure(eventSource, t, response);
        if (exceptionCausedByCancelingEventSource(t)) {
            LOG.info("Cancelled");
        } else {
            LOG.error("Failed to fill existing sessions choice box", t);
            EstimateFxNotification.showErrorNotificationFromCustomThread("Failed to get existing session list from backend.");
        }
    }

    private boolean exceptionCausedByCancelingEventSource(@Nullable Throwable t) {
        return t instanceof SocketException && "Socket closed".equals(t.getMessage());
    }

    private void updateChoiceBox(List<SessionIdentifier> sessionIdentifiers) {
        if (listsNotEqual(sessionIdentifiers)) {
            LOG.info("Choice box list changed, updating");
            choiceBox.getItems().clear();
            choiceBox.getItems().addAll(sessionIdentifiers);
        }
    }

    private boolean listsNotEqual(List<SessionIdentifier> sessionIdentifiers) {
        return !sessionIdentifiers.equals(choiceBox.getItems());
    }
}