package com.czareg.tasks.task;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.VOTE;

public class CreateSessionTask extends CustomTask {
    private static final Logger LOG = LogManager.getLogger(CreateSessionTask.class);
    private SessionDTO session;
    private Context context;
    private BackendBlockingService backendBlockingService;

    public CreateSessionTask(Context context, BackendBlockingService backendBlockingService) {
        super(LOG);
        this.context = context;
        this.backendBlockingService = backendBlockingService;
    }

    @Override
    void process() throws BackendServiceException {
        String userName = context.getUserName();
        boolean allowStealingCreator = context.getCreateContext().shouldAllowStealingCreator();
        boolean passCreatorWhenLeaving = context.getCreateContext().shouldPassCreatorWhenLeaving();
        session = backendBlockingService.createSession(userName, allowStealingCreator, passCreatorWhenLeaving);
        LOG.info("Received created session from backend: {}", session);
    }

    @Override
    protected void succeeded() {
        int sessionId = session.getSessionId();
        context.setSessionId(sessionId);
        context.getSceneManager().setScene(VOTE);
    }
}