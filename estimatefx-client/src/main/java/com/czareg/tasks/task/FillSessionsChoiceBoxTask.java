package com.czareg.tasks.task;

import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.model.SessionIdentifier;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class FillSessionsChoiceBoxTask extends CustomTask {
    private static final Logger LOG = LogManager.getLogger(FillSessionsChoiceBoxTask.class);
    private ChoiceBox<SessionIdentifier> choiceBox;
    private BackendBlockingService backendBlockingService;
    private List<SessionIdentifier> sessionIdentifiers;

    public FillSessionsChoiceBoxTask(ChoiceBox<SessionIdentifier> choiceBox, BackendBlockingService backendBlockingService) {
        super(LOG);
        this.choiceBox = choiceBox;
        this.backendBlockingService = backendBlockingService;
    }

    @Override
    void process() throws BackendServiceException {
        List<SessionIdentifierDTO> sessionDTOs = backendBlockingService.getSessionIdentifiers();
        sessionIdentifiers = sessionDTOs.stream()
                .map(SessionIdentifier::new)
                .collect(Collectors.toList());
        LOG.info("Received session identifiers from backend: {}", sessionIdentifiers.size());
    }

    @Override
    protected void succeeded() {
        if (listsNotEqual()) {
            LOG.info("Choice box list changed, updating");
            choiceBox.getItems().clear();
            choiceBox.getItems().addAll(sessionIdentifiers);
        }
    }

    private boolean listsNotEqual() {
        return !sessionIdentifiers.equals(choiceBox.getItems());
    }
}