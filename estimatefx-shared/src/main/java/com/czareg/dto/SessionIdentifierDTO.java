package com.czareg.dto;

import java.util.Objects;

public class SessionIdentifierDTO {
    private int sessionId;
    private String description;

    public SessionIdentifierDTO() {
    }

    public SessionIdentifierDTO(int sessionId, String description) {
        this.sessionId = sessionId;
        this.description = description;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionIdentifierDTO that = (SessionIdentifierDTO) o;
        return sessionId == that.sessionId &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, description);
    }

    @Override
    public String toString() {
        return "SessionIdentifierDTO{" +
                "sessionId=" + sessionId +
                ", description='" + description + '\'' +
                '}';
    }
}