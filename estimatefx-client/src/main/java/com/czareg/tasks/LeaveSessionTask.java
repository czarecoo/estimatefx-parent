package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.utils.BackendServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LeaveSessionTask extends CustomTask {
    private static final Logger LOG = LogManager.getLogger(LeaveSessionTask.class);
    private Context context;
    private BackendBlockingService backendBlockingService;

    public LeaveSessionTask(Context context, BackendBlockingService backendBlockingService) {
        super(LOG);
        this.context = context;
        this.backendBlockingService = backendBlockingService;
    }

    @Override
    void process() throws BackendServiceException {
        String userName = context.getUserName();
        if (userName == null) {
            LOG.info("Stopping without leaving session because userName is null");
            return;
        }
        Integer sessionId = context.getSessionId();
        if (sessionId == null) {
            LOG.info("Stopping without leaving session because sessionId is null");
            return;
        }
        SessionDTO sessionDTO = backendBlockingService.getSession(sessionId);
        boolean hasUser = hasUser(userName, sessionDTO);
        if (!hasUser) {
            LOG.info("Stopping without leaving session because userName is not in session's user list");
            return;
        }
        backendBlockingService.leaveSession(sessionId, userName);
        LOG.info("Left session");
    }

    private boolean hasUser(String userName, SessionDTO sessionDTO) {
        return sessionDTO.getUsers().stream()
                .anyMatch((userDTO -> userDTO.getUserName().equals(userName)));
    }
}