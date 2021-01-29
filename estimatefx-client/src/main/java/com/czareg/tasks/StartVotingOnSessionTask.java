package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartVotingOnSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(StartVotingOnSessionTask.class);
    private Context context;
    private BackendService backendService;

    public StartVotingOnSessionTask(Context context, BackendService backendService) {
        this.context = context;
        this.backendService = backendService;
    }

    @Override
    protected Void call() {
        try {
            String userName = context.getUserName();
            int sessionId = context.getSessionId();
            backendService.startVotingOnSession(sessionId, userName);
            LOG.info("Started voting on session");
        } catch (BackendServiceException e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotification("Failed to start voting on current session.");
    }

    @Override
    protected void succeeded() {
        Task<Void> getSessionTask = context.getTaskFactory().createGetSessionTask();
        new Thread(getSessionTask).start();
    }
}