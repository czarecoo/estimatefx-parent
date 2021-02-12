package com.czareg.session.controller;

import com.czareg.dto.SessionDTO;
import com.czareg.session.model.Session;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class SessionSink {
    private Sinks.Many<Session> sink = Sinks.many()
            .replay()
            .latest();

    public void emit(Session session) {
        sink.tryEmitNext(session);
    }

    public Flux<SessionDTO> asSessionDTOFlux() {
        return sink.asFlux()
                .map(Session::toSessionDTO);
    }

    public Flux<SessionDTO> asSessionDTOFluxBySessionId(int sessionId) {
        return sink.asFlux()
                .filter(sessionDTO -> sessionDTO.getSessionId() == sessionId)
                .map(Session::toSessionDTO);
    }
}