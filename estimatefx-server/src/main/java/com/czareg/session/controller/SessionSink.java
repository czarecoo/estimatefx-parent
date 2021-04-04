package com.czareg.session.controller;

import com.czareg.dto.SessionDTO;
import com.czareg.session.model.Session;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class SessionSink {
    private Sinks.Many<SessionDTO> sink = Sinks.many()
            .replay()
            .latest();

    public void emit(Session session) {
        sink.tryEmitNext(session.toSessionDTO());
    }

    public Flux<SessionDTO> asSessionDTOFlux() {
        return sink.asFlux();
    }

    public Flux<SessionDTO> asSessionDTOFluxBySessionId(int sessionId) {
        return sink.asFlux()
                .filter(sessionDTO -> sessionDTO.getSessionId() == sessionId);
    }
}