package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.scheduled.FillSessionsChoiceBoxScheduledService;
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
    private FillSessionsChoiceBoxScheduledService fillSessionsChoiceBoxScheduledService;

    public JoinSessionTask(Context context, BackendService backendService, FillSessionsChoiceBoxScheduledService fillSessionsChoiceBoxScheduledService) {
        this.context = context;
        this.backendService = backendService;
        this.fillSessionsChoiceBoxScheduledService = fillSessionsChoiceBoxScheduledService;
    }

    @Override
    protected Void call() throws BackendServiceException {
        try {
            String userName = context.getUserName();
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
        fillSessionsChoiceBoxScheduledService.cancel();
        context.getSceneManager().setScene(VOTE);
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotification("Failed to join session.");
    }
}