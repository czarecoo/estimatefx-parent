package com.czareg.model;

import com.czareg.dto.SessionDTO;

import java.util.Objects;

public class Session {
    private SessionDTO sessionDTO;

    public Session(SessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;
    }

    public SessionDTO getSessionDTO() {
        return sessionDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionDTO, session.sessionDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionDTO);
    }

    @Override
    public String toString() {
        return sessionDTO.getDescription();
    }
}