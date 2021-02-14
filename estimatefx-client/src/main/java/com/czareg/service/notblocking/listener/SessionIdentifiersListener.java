package com.czareg.service.notblocking.listener;

import com.czareg.model.SessionIdentifier;
import com.czareg.notifications.EstimateFxNotification;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SessionIdentifiersListener extends Listener {
    private static final Logger LOG = LogManager.getLogger(SessionIdentifiersListener.class);

    private ChoiceBox<SessionIdentifier> choiceBox;
    private Gson gson;
    private Type sessionIdentifierListType;

    public SessionIdentifiersListener(ChoiceBox<SessionIdentifier> choiceBox) {
        super(LOG);
        this.choiceBox = choiceBox;
        gson = new Gson();
        sessionIdentifierListType = new TypeToken<ArrayList<SessionIdentifier>>() {
        }.getType();
    }

    @Override
    protected void onEvent(String jsonObject) {
        List<SessionIdentifier> sessionIdentifiers = gson.fromJson(jsonObject, sessionIdentifierListType);

        Platform.runLater(() -> updateChoiceBox(sessionIdentifiers));
    }

    @Override
    protected void onFailure(Throwable t) {
        LOG.error("Failed to fill existing sessions choice box", t);
        EstimateFxNotification.showErrorNotificationFromCustomThread("Failed to get existing session list from backend.");
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