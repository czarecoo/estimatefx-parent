package com.czareg.session.service;

import com.czareg.session.controller.SessionSink;
import com.czareg.session.exceptions.NotExistsException;
import com.czareg.session.model.Session;
import com.czareg.session.model.user.SessionUser;
import com.czareg.session.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class InactiveUserCleaningService {
    private static Logger LOGGER = LoggerFactory.getLogger(InactiveUserCleaningService.class);
    private static final int MAX_HOURS_OF_INACTIVITY = 8;
    private HashMap<SessionUser, Instant> lastActivityMap;
    private SessionService sessionService;
    private SessionRepository sessionRepository;
    private SessionSink sessionSink;

    public InactiveUserCleaningService(@Lazy SessionService sessionService, SessionRepository sessionRepository, SessionSink sessionSink) {
        lastActivityMap = new HashMap<>();
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
        this.sessionSink = sessionSink;
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
    public void clean() {
        LOGGER.info(lastActivityMap.toString());
        List<SessionUser> sessionUsersToClean = findInactiveUsers();
        deleteInactiveUsers(sessionUsersToClean);
    }

    private List<SessionUser> findInactiveUsers() {
        List<SessionUser> sessionUsersToClean = new ArrayList<>();
        for (Map.Entry<SessionUser, Instant> entry : lastActivityMap.entrySet()) {
            SessionUser sessionUser = entry.getKey();
            Instant lastActivity = entry.getValue();
            if (Duration.between(lastActivity, Instant.now()).toHours() >= MAX_HOURS_OF_INACTIVITY) {
                sessionUsersToClean.add(sessionUser);
            }
        }
        return sessionUsersToClean;
    }

    private void deleteInactiveUsers(List<SessionUser> sessionUsersToClean) {
        for (SessionUser sessionUser : sessionUsersToClean) {
            cleanUser(sessionUser);
            int sessionId = sessionUser.getSessionId();
            Optional<Session> sessionOptional = sessionRepository.getSessionOptional(sessionId);
            sessionOptional.ifPresent(session -> sessionSink.emit(session));
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