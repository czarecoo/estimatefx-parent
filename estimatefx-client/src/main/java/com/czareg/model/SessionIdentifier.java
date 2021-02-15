package com.czareg.model;

import com.czareg.dto.SessionIdentifierDTO;

import java.util.Objects;

public class SessionIdentifier {
    private int sessionId;
    private String description;

    public SessionIdentifier(int sessionId, String description) {
        this.sessionId = sessionId;
        this.description = description;
    }

    public SessionIdentifier(SessionIdentifierDTO sessionIdentifierDTO) {
        this.sessionId = sessionIdentifierDTO.getSessionId();
        this.description = sessionIdentifierDTO.getDescription();
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionIdentifier that = (SessionIdentifier) o;
        return sessionId == that.sessionId &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, description);
    }

    @Override
    public String toString() {
        return description;
    }
}