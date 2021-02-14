package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.VOTE;

public class CreateSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(CreateSessionTask.class);
    private SessionDTO session;
    private Context context;
    private BackendBlockingService backendBlockingService;

    public CreateSessionTask(Context context, BackendBlockingService backendBlockingService) {
        this.context = context;
        this.backendBlockingService = backendBlockingService;
    }

    @Override
    protected Void call() {
        try {
            String userName = context.getUserName();
            session = backendBlockingService.createSession(userName);
            LOG.info("Received created session from backend: {}", session);
        } catch (BackendServiceException e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    protected void succeeded() {
        int sessionId = session.getSessionId();
        context.setSessionId(sessionId);
        context.getSceneManager().setScene(VOTE);
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotificationFromUiThread("Failed to create new session.");
    }
}
