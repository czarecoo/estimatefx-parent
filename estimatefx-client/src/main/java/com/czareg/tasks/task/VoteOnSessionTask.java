package com.czareg.tasks.task;

import com.czareg.context.Context;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoteOnSessionTask extends CustomTask {
    private static final Logger LOG = LogManager.getLogger(VoteOnSessionTask.class);
    private Context context;
    private BackendBlockingService backendBlockingService;
    private String voteValue;

    public VoteOnSessionTask(Context context, BackendBlockingService backendBlockingService, String voteValue) {
        super(LOG);
        this.context = context;
        this.backendBlockingService = backendBlockingService;
        this.voteValue = voteValue;
    }

    @Override
    void process() throws BackendServiceException {
        String userName = context.getUserName();
        int sessionId = context.getSessionId();
        backendBlockingService.voteOnSession(sessionId, userName, voteValue);
        LOG.info("Voted on session");
    }
}