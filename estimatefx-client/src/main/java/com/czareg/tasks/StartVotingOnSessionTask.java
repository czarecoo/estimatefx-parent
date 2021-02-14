package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartVotingOnSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(StartVotingOnSessionTask.class);
    private Context context;
    private BackendBlockingService backendBlockingService;

    public StartVotingOnSessionTask(Context context, BackendBlockingService backendBlockingService) {
        this.context = context;
        this.backendBlockingService = backendBlockingService;
    }

    @Override
    protected Void call() {
        try {
            String userName = context.getUserName();
            int sessionId = context.getSessionId();
            backendBlockingService.startVotingOnSession(sessionId, userName);
            LOG.info("Started voting on session");
        } catch (BackendServiceException e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotificationFromUiThread("Failed to start voting on current session.");
    }
}