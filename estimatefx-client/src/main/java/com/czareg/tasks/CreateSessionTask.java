package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceCallFailedException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.VOTE;

public class CreateSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(CreateSessionTask.class);
    private SessionDTO session;
    private Context context;
    private BackendService backendService;

    public CreateSessionTask(Context context, BackendService backendService) {
        this.context = context;
        this.backendService = backendService;
    }

    @Override
    protected Void call() throws BackendServiceCallFailedException {
        try {
            String userName = context.getName();
            session = backendService.createSession(userName);
            LOG.info("Received created session from backend: {}", session);
            return null;
        } catch (BackendServiceCallFailedException e) {
            LOG.error("Failed to create new session.", e);
            throw e;
        }
    }

    @Override
    protected void succeeded() {
        int sessionId = session.getSessionId();
        context.setSessionId(sessionId);
        context.getSceneManager().setScene(VOTE);
    }
}
