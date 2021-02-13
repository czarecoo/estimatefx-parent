package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.shared.BackendServiceException;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LeaveSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(LeaveSessionTask.class);
    private Context context;
    private BackendBlockingService backendBlockingService;

    public LeaveSessionTask(Context context, BackendBlockingService backendBlockingService) {
        this.context = context;
        this.backendBlockingService = backendBlockingService;
    }

    @Override
    protected Void call() {
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
            SessionDTO sessionDTO = backendBlockingService.getSession(sessionId);
            boolean hasUser = hasUser(userName, sessionDTO);
            if (!hasUser) {
                LOG.info("Stopping without leaving session because userName is not in session's user list");
                return null;
            }
            backendBlockingService.leaveSession(sessionId, userName);
            LOG.info("Left session");
        } catch (BackendServiceException e) {
            LOG.error(e);
        }
        return null;
    }

    private boolean hasUser(String userName, SessionDTO sessionDTO) {
        return sessionDTO.getUsers().stream()
                .anyMatch((userDTO -> userDTO.getUserName().equals(userName)));
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotification("Failed to leave current session.");
    }
}