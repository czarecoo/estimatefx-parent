package com.czareg.session.controller;

import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.session.model.Session;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SessionListSink {
    private Sinks.Many<List<Session>> sink = Sinks.many()
            .replay()
            .latest();

    public void emit(List<Session> sessions) {
        sink.tryEmitNext(sessions);
    }

    public Flux<List<SessionIdentifierDTO>> asSessionIdentifierDTOListFlux() {
        return sink.asFlux()
                .map(mapListOfSessionToListOfSessionIdentifierDTOs());
    }

    private Function<List<Session>, List<SessionIdentifierDTO>> mapListOfSessionToListOfSessionIdentifierDTOs() {
        return list -> list.stream()
                .map(Session::toSessionIdentifierDTO)
                .collect(Collectors.toList());
    }
}