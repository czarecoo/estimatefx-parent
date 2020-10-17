package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.VOTE;

public class JoinSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(JoinSessionTask.class);
    private SessionDTO session;
    private Context context;
    private BackendService backendService;

    public JoinSessionTask(Context context, BackendService backendService) {
        this.context = context;
        this.backendService = backendService;
    }

    @Override
    protected Void call() throws BackendServiceException {
        try {
            String userName = context.getName();
            int sessionId = context.getSessionId();
            session = backendService.joinSession(sessionId, userName);
            LOG.info("Received join session from backend: {}", session);
            return null;
        } catch (BackendServiceException e) {
            LOG.error(e);
            throw e;
        }
    }

    @Override
    protected void succeeded() {
        context.getSceneManager().setScene(VOTE);
    }
}