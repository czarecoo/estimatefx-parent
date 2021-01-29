package com.czareg.session.service;

import com.czareg.session.exceptions.NotExistsException;
import com.czareg.session.model.user.SessionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActivityService {
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);
    private static final int MAX_HOURS_OF_INACTIVITY = 8;
    private HashMap<SessionUser, Instant> lastActivityMap;
    private SessionService sessionService;

    public ActivityService(@Lazy SessionService sessionService) {
        lastActivityMap = new HashMap<>();
        this.sessionService = sessionService;
    }

    public void add(int sessionId, String userName) {
        SessionUser sessionUser = new SessionUser(sessionId, userName);
        lastActivityMap.put(sessionUser, Instant.now());
    }

    public void remove(int sessionId, String userName) {
        SessionUser sessionUser = new SessionUser(sessionId, userName);
        lastActivityMap.remove(sessionUser);
    }

    @Scheduled(cron = "0 0 * ? * *") //every hour
    public void cleanInactive() {
        Instant cleaningTime = Instant.now();
        LOGGER.info("Cleaning started");
        LOGGER.info(lastActivityMap.toString());
        for (Map.Entry<SessionUser, Instant> entry : lastActivityMap.entrySet()) {
            SessionUser sessionUser = entry.getKey();
            Instant lastActivity = entry.getValue();
            if (Duration.between(lastActivity, cleaningTime).toHours() > MAX_HOURS_OF_INACTIVITY) {
                cleanUser(sessionUser);
            }
        }
    }

    public void cleanUser(SessionUser sessionUser) {
        try {
            LOGGER.info("Removing user {} from session {}, last active over {} hours.", sessionUser.getName(), sessionUser.getSessionId(), MAX_HOURS_OF_INACTIVITY);
            sessionService.leave(sessionUser.getSessionId(), sessionUser.getName());
        } catch (NotExistsException e) {
            LOGGER.error("{} does not exist", sessionUser, e);
        }
    }
}