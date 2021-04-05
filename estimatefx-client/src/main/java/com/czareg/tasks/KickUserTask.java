package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KickUserTask extends CustomTask {
    private static final Logger LOG = LogManager.getLogger(KickUserTask.class);
    private Context context;
    private BackendBlockingService backendBlockingService;
    private String userToKick;

    public KickUserTask(Context context, BackendBlockingService backendBlockingService, String userToKick) {
        super(LOG);
        this.context = context;
        this.backendBlockingService = backendBlockingService;
        this.userToKick = userToKick;
    }

    @Override
    void process() throws BackendServiceException {
        String userName = context.getUserName();
        int sessionId = context.getSessionId();
        backendBlockingService.kickUser(sessionId, userName, userToKick);
        LOG.info("Kicked user " + userToKick);
    }
}