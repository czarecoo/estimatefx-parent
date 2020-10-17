package com.czareg.tasks;

import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.model.SessionIdentifier;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceException;
import javafx.concurrent.Task;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
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
            return null;
        } catch (BackendServiceException e) {
            LOG.error("Failed to fill existing sessions choice box", e);
            throw e;
        }
    }

    @Override
    protected void succeeded() {
        if (listsAreNotEqual()) {
            addMissingSessionIdentifiers();
            removeExtraSessionIdentifiers();
        }
    }

    private boolean listsAreNotEqual() {
        return !sessionIdentifiers.equals(choiceBox.getItems());
    }

    private void removeExtraSessionIdentifiers() {
        Iterator<SessionIdentifier> iterator = choiceBox.getItems().iterator();
        while (iterator.hasNext()) {
            SessionIdentifier sessionIdentifier = iterator.next();
            if (!sessionIdentifiers.contains(sessionIdentifier)) {
                iterator.remove();
                LOG.info("Removed session id: {} from join choice box", sessionIdentifier.getSessionId());
            }
        }
    }

    private void addMissingSessionIdentifiers() {
        for (SessionIdentifier sessionIdentifier : sessionIdentifiers) {
            if (!choiceBox.getItems().contains(sessionIdentifier)) {
                choiceBox.getItems().add(sessionIdentifier);
                LOG.info("Added session id: {} to join choice box", sessionIdentifier.getSessionId());
            }
        }
    }
}
