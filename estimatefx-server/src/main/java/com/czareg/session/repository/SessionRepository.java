package com.czareg.session.repository;

import com.czareg.session.exceptions.NotExistsException;
import com.czareg.session.model.Session;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class SessionRepository {
    private List<Session> sessions = new LinkedList<>();

    public void add(Session session) {
        sessions.add(session);
    }

    public Session getSession(int sessionId) throws NotExistsException {
        return sessions.stream()
                .filter(session -> session.getSessionId() == sessionId)
                .findFirst()
                .orElseThrow(() -> new NotExistsException("Session does not exist"));
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void delete(int sessionId) throws NotExistsException {
        Session session = getSession(sessionId);
        sessions.remove(session);
    }
}