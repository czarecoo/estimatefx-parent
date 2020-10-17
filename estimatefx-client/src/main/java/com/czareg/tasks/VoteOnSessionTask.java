package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoteOnSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(VoteOnSessionTask.class);
    private Context context;
    private BackendService backendService;
    private String voteValue;

    public VoteOnSessionTask(Context context, BackendService backendService, String voteValue) {
        this.context = context;
        this.backendService = backendService;
        this.voteValue = voteValue;
    }

    @Override
    protected Void call() throws BackendServiceException {
        try {
            String userName = context.getUserName();
            int sessionId = context.getSessionId();
            backendService.voteOnSession(sessionId, userName, voteValue);
            LOG.info("Voted on session");
            return null;
        } catch (BackendServiceException e) {
            LOG.error(e);
            throw e;
        }
    }
}