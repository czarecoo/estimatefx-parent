package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PassCreatorTask extends CustomTask {
    private static final Logger LOG = LogManager.getLogger(PassCreatorTask.class);
    private Context context;
    private BackendBlockingService backendBlockingService;
    private String newCreator;

    public PassCreatorTask(Context context, BackendBlockingService backendBlockingService, String newCreator) {
        super(LOG);
        this.context = context;
        this.backendBlockingService = backendBlockingService;
        this.newCreator = newCreator;
    }

    @Override
    void process() throws BackendServiceException {
        String userName = context.getUserName();
        int sessionId = context.getSessionId();
        backendBlockingService.passCreator(sessionId, userName, newCreator);
        LOG.info("Passed creator to " + newCreator);
    }
}