package com.czareg.tasks.task;

import com.czareg.context.Context;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StopVotingOnSessionTask extends CustomTask {
    private static final Logger LOG = LogManager.getLogger(StopVotingOnSessionTask.class);
    private Context context;
    private BackendBlockingService backendBlockingService;

    public StopVotingOnSessionTask(Context context, BackendBlockingService backendBlockingService) {
        super(LOG);
        this.context = context;
        this.backendBlockingService = backendBlockingService;
    }

    @Override
    void process() throws BackendServiceException {
        String userName = context.getUserName();
        int sessionId = context.getSessionId();
        backendBlockingService.stopVotingOnSession(sessionId, userName);
        LOG.info("Stopped voting on session");
    }
}