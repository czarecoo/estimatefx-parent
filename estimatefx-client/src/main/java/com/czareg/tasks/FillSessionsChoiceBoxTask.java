package com.czareg.tasks;

import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.model.SessionIdentifier;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.shared.BackendServiceException;
import javafx.concurrent.Task;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class FillSessionsChoiceBoxTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(FillSessionsChoiceBoxTask.class);
    private ChoiceBox<SessionIdentifier> choiceBox;
    private BackendBlockingService backendBlockingService;
    private List<SessionIdentifier> sessionIdentifiers;

    public FillSessionsChoiceBoxTask(ChoiceBox<SessionIdentifier> choiceBox, BackendBlockingService backendBlockingService) {
        this.choiceBox = choiceBox;
        this.backendBlockingService = backendBlockingService;
    }

    @Override
    protected Void call() {
        try {
            List<SessionIdentifierDTO> sessionDTOs = backendBlockingService.getSessionIdentifiers();
            sessionIdentifiers = sessionDTOs.stream()
                    .map(SessionIdentifier::new)
                    .collect(Collectors.toList());
            LOG.info("Received session identifiers from backend: {}", sessionIdentifiers.size());
        } catch (BackendServiceException e) {
            LOG.error("Failed to fill existing sessions choice box", e);
        }
        return null;
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
