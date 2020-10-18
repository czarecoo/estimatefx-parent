package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceException;
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
    protected Void call() throws BackendServiceException {
        try {
            String userName = context.getUserName();
            session = backendService.createSession(userName);
            LOG.info("Received created session from backend: {}", session);
            return null;
        } catch (BackendServiceException e) {
            LOG.error(e);
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