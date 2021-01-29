package com.czareg.session.model.user;

import java.util.Objects;

public class SessionUser {
    private int sessionId;
    private String name;

    public SessionUser(int sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionUser that = (SessionUser) o;
        return sessionId == that.sessionId &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, name);
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "sessionId=" + sessionId +
                ", name='" + name + '\'' +
                '}';
    }
}