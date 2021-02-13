package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.shared.BackendServiceException;
import javafx.concurrent.Task;
import okhttp3.internal.sse.RealEventSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.VOTE;

public class JoinSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(JoinSessionTask.class);
    private SessionDTO session;
    private Context context;
    private BackendBlockingService backendBlockingService;
    private RealEventSource realEventSource;

    public JoinSessionTask(Context context, BackendBlockingService backendBlockingService, RealEventSource realEventSource) {
        this.context = context;
        this.backendBlockingService = backendBlockingService;
        this.realEventSource = realEventSource;
    }

    @Override
    protected Void call() {
        try {
            String userName = context.getUserName();
            int sessionId = context.getSessionId();
            session = backendBlockingService.joinSession(sessionId, userName);
            LOG.info("Received join session from backend: {}", session);
        } catch (BackendServiceException e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    protected void succeeded() {
        realEventSource.cancel();
        context.getSceneManager().setScene(VOTE);
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotificationFromUiThread("Failed to join session.");
    }
}