package com.czareg.tasks;

import com.czareg.dto.SessionDTO;
import com.czareg.model.Session;
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
    private ChoiceBox<Session> choiceBox;
    private BackendService backendService;
    private List<Session> sessions;

    public FillSessionsChoiceBoxTask(ChoiceBox<Session> choiceBox, BackendService backendService) {
        this.choiceBox = choiceBox;
        this.backendService = backendService;
    }

    @Override
    protected Void call() throws BackendServiceException {
        try {
            List<SessionDTO> sessionDTOs = backendService.getSessions();
            LOG.info("Received sessions from backend: {}", sessionDTOs);
            sessions = sessionDTOs.stream()
                    .map(Session::new)
                    .collect(Collectors.toList());
            return null;
        } catch (BackendServiceException e) {
            LOG.error("Failed to fill existing sessions choice box", e);
            throw e;
        }
    }

    @Override
    protected void succeeded() {
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(sessions);
    }
}
