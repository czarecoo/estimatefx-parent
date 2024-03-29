package com.czareg.session.service;

import com.czareg.session.model.Session;
import com.czareg.session.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmptySessionCleaningService {
    private static Logger LOGGER = LoggerFactory.getLogger(EmptySessionCleaningService.class);
    private SessionRepository sessionRepository;

    public EmptySessionCleaningService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Scheduled(fixedRateString = "${empty.session.cleaning.millis}")
    public void clean() {
        List<Session> emptySessions = getEmptySessions();
        if (!emptySessions.isEmpty()) {
            LOGGER.info("Removing empty sessions: {}", emptySessions);
            emptySessions.forEach(session -> sessionRepository.delete(session.getSessionId()));
        }
    }

    private List<Session> getSessions() {
        return sessionRepository.getSessions();
    }

    private List<Session> getEmptySessions() {
        return getSessions().stream()
                .filter(Session::isEmpty)
                .collect(Collectors.toList());
    }
}