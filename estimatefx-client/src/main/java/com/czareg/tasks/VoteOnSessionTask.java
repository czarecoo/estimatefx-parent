package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.shared.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoteOnSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(VoteOnSessionTask.class);
    private Context context;
    private BackendBlockingService backendBlockingService;
    private String voteValue;

    public VoteOnSessionTask(Context context, BackendBlockingService backendBlockingService, String voteValue) {
        this.context = context;
        this.backendBlockingService = backendBlockingService;
        this.voteValue = voteValue;
    }

    @Override
    protected Void call() {
        try {
            String userName = context.getUserName();
            int sessionId = context.getSessionId();
            backendBlockingService.voteOnSession(sessionId, userName, voteValue);
            LOG.info("Voted on session");
        } catch (BackendServiceException e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotificationFromUiThread("Failed to vote on current session.");
    }
}