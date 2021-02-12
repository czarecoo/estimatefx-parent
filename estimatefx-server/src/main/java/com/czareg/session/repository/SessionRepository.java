package com.czareg.session.repository;

import com.czareg.session.exceptions.NotExistsException;
import com.czareg.session.model.Session;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class SessionRepository {
    private List<Session> sessions = new LinkedList<>();

    public void add(Session session) {
        sessions.add(session);
    }

    public Session getSession(int sessionId) throws NotExistsException {
        return getSessionOptional(sessionId)
                .orElseThrow(() -> new NotExistsException("Session does not exist"));
    }

    public Optional<Session> getSessionOptional(int sessionId) {
        return sessions.stream()
                .filter(session -> session.getSessionId() == sessionId)
                .findFirst();
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void delete(int sessionId) {
        Session session = sessions.stream()
                .filter(s -> s.getSessionId() == sessionId)
                .findFirst()
                .orElse(null);
        sessions.remove(session);
    }
}