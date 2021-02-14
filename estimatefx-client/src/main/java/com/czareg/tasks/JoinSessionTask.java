package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import com.czareg.service.notblocking.polling.PollingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.VOTE;

public class JoinSessionTask extends CustomTask {
    private static final Logger LOG = LogManager.getLogger(JoinSessionTask.class);
    private SessionDTO session;
    private Context context;
    private BackendBlockingService backendBlockingService;
    private PollingService pollingService;

    public JoinSessionTask(Context context, BackendBlockingService backendBlockingService,
                           PollingService pollingService) {
        super(LOG);
        this.context = context;
        this.backendBlockingService = backendBlockingService;
        this.pollingService = pollingService;
    }

    @Override
    void process() throws BackendServiceException {
        String userName = context.getUserName();
        int sessionId = context.getSessionId();
        session = backendBlockingService.joinSession(sessionId, userName);
        LOG.info("Received join session from backend: {}", session);
    }

    @Override
    protected void succeeded() {
        pollingService.close();
        context.getSceneManager().setScene(VOTE);
    }
}