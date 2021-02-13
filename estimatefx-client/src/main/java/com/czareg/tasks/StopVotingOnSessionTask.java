package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.shared.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StopVotingOnSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(StopVotingOnSessionTask.class);
    private Context context;
    private BackendBlockingService backendBlockingService;

    public StopVotingOnSessionTask(Context context, BackendBlockingService backendBlockingService) {
        this.context = context;
        this.backendBlockingService = backendBlockingService;
    }

    @Override
    protected Void call() {
        try {
            String userName = context.getUserName();
            int sessionId = context.getSessionId();
            backendBlockingService.stopVotingOnSession(sessionId, userName);
            LOG.info("Stopped voting on session");
        } catch (BackendServiceException e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotificationFromUiThread("Failed to stop voting on current session.");
    }

    @Override
    protected void succeeded() {
        Task<Void> getSessionTask = context.getTaskFactory().createGetSessionTask();
        new Thread(getSessionTask).start();
    }
}