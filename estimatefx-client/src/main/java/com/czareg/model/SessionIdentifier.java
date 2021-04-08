package com.czareg.model;

import com.czareg.dto.SessionIdentifierDTO;
import lombok.Data;

@Data
public class SessionIdentifier {
    private int sessionId;
    private String description;

    public SessionIdentifier(SessionIdentifierDTO sessionIdentifierDTO) {
        this.sessionId = sessionIdentifierDTO.getSessionId();
        this.description = sessionIdentifierDTO.getDescription();
    }

    @Override
    public String toString() {
        return description;
    }
}