package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LeaveSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(LeaveSessionTask.class);
    private Context context;
    private BackendService backendService;

    public LeaveSessionTask(Context context, BackendService backendService) {
        this.context = context;
        this.backendService = backendService;
    }

    @Override
    protected Void call() throws BackendServiceException {
        try {
            String userName = context.getUserName();
            if (userName == null) {
                LOG.info("Stopping without leaving session because userName is null");
                return null;
            }
            Integer sessionId = context.getSessionId();
            if (sessionId == null) {
                LOG.info("Stopping without leaving session because sessionId is null");
                return null;
            }
            backendService.leaveSession(sessionId, userName);
            LOG.info("Left session");
            return null;
        } catch (BackendServiceException e) {
            LOG.error(e);
            throw e;
        }
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotification("Failed to leave current session.");
    }
}