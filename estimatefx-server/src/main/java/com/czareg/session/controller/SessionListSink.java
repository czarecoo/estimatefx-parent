package com.czareg.session.controller;

import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.session.model.Session;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class SessionListSink {
    private Sinks.Many<List<SessionIdentifierDTO>> sink = Sinks.many()
            .replay()
            .latest();

    public void emit(List<Session> sessions) {
        List<SessionIdentifierDTO> sessionIdentifierDTOS = mapListOfSessionToListOfSessionIdentifierDTOs(sessions);
        sink.tryEmitNext(sessionIdentifierDTOS);
    }

    public Flux<List<SessionIdentifierDTO>> asSessionIdentifierDTOListFlux() {
        return sink.asFlux();
    }

    private List<SessionIdentifierDTO> mapListOfSessionToListOfSessionIdentifierDTOs(List<Session> sessions) {
        return sessions.stream()
                .map(Session::toSessionIdentifierDTO)
                .collect(toList());
    }
}