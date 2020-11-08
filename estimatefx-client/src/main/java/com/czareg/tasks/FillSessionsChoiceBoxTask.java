package com.czareg.tasks;

import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.model.SessionIdentifier;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceException;
import javafx.concurrent.Task;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class FillSessionsChoiceBoxTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(FillSessionsChoiceBoxTask.class);
    private ChoiceBox<SessionIdentifier> choiceBox;
    private BackendService backendService;
    private List<SessionIdentifier> sessionIdentifiers;

    public FillSessionsChoiceBoxTask(ChoiceBox<SessionIdentifier> choiceBox, BackendService backendService) {
        this.choiceBox = choiceBox;
        this.backendService = backendService;
    }

    @Override
    protected Void call() throws BackendServiceException {
        try {
            List<SessionIdentifierDTO> sessionDTOs = backendService.getSessionIdentifiers();
            sessionIdentifiers = sessionDTOs.stream()
                    .map(SessionIdentifier::new)
                    .collect(Collectors.toList());
            LOG.info("Received session identifiers from backend: {}", sessionIdentifiers.size());
            return null;
        } catch (BackendServiceException e) {
            LOG.error("Failed to fill existing sessions choice box", e);
            throw e;
        }
    }

    @Override
    protected void succeeded() {
        if (listsNotEqual()) {
            LOG.info("Choice box list changed, updating");
            choiceBox.getItems().clear();
            choiceBox.getItems().addAll(sessionIdentifiers);
        }
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotification("Failed to get existing session list from backend.");
    }

    private boolean listsNotEqual() {
        return !sessionIdentifiers.equals(choiceBox.getItems());
    }
}
