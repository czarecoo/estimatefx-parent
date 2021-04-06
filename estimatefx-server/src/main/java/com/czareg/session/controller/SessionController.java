package com.czareg.session.controller;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.session.exceptions.BadRequestException;
import com.czareg.session.exceptions.NotExistsException;
import com.czareg.session.model.Session;
import com.czareg.session.service.SessionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequestMapping("/service/v1")
public class SessionController {
    private SessionService sessionService;
    private SessionSink sessionSink;

    public SessionController(SessionService sessionService, SessionSink sessionSink) {
        this.sessionService = sessionService;
        this.sessionSink = sessionSink;
    }

    @PostMapping(value = "/createSession")
    public SessionDTO createSession(@RequestParam String userName,
                                    @RequestParam(defaultValue = "true") boolean allowPassingCreator,
                                    @RequestParam(defaultValue = "true") boolean allowStealingCreator,
                                    @RequestParam(defaultValue = "true") boolean passCreatorWhenLeaving) throws BadRequestException {
        Session session = sessionService.create(userName, allowPassingCreator, allowStealingCreator, passCreatorWhenLeaving);
        sessionSink.emit(session);
        return session.toSessionDTO();
    }

    @PutMapping(value = "/joinSession/{sessionId}")
    public SessionDTO joinSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName)
            throws BadRequestException, NotExistsException {
        Session session = sessionService.join(sessionId, userName);
        sessionSink.emit(session);
        return session.toSessionDTO();
    }

    @GetMapping(value = "/getSession/{sessionId}")
    public SessionDTO getSessionById(@PathVariable("sessionId") int sessionId) throws NotExistsException {
        Session session = sessionService.getSession(sessionId);
        return session.toSessionDTO();
    }

    @GetMapping(value = "/pollSession/{sessionId}", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<SessionDTO> pollSessionById(@PathVariable("sessionId") int sessionId) {
        return sessionSink.asSessionDTOFluxBySessionId(sessionId);
    }

    @GetMapping(value = "/getSessions")
    public List<SessionDTO> getSessions() {
        List<Session> sessions = sessionService.getSessions();
        return sessions.stream()
                .map(Session::toSessionDTO)
                .collect(toList());
    }

    @GetMapping(value = "/pollSessions", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<SessionDTO> pollSessions() {
        return sessionSink.asSessionDTOFlux();
    }

    @GetMapping(value = "/getSessionIdentifiers")
    public List<SessionIdentifierDTO> getSessionIdentifiers() {
        List<Session> sessions = sessionService.getSessions();
        return sessions.stream()
                .map(Session::toSessionIdentifierDTO)
                .collect(toList());
    }

    @PutMapping(value = "/voteOnSession/{sessionId}")
    public void voteOnSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName,
                              @RequestParam String voteValue)
            throws BadRequestException, NotExistsException {
        Session session = sessionService.vote(sessionId, userName, voteValue);
        sessionSink.emit(session);
    }

    @DeleteMapping(value = "/leaveSession/{sessionId}")
    public void leaveSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName)
            throws NotExistsException {
        Session session = sessionService.leave(sessionId, userName);
        sessionSink.emit(session);
    }

    @PutMapping(value = "/startVotingOnSession/{sessionId}")
    public void startVotingOnSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName)
            throws BadRequestException, NotExistsException {
        Session session = sessionService.startVoting(sessionId, userName);
        sessionSink.emit(session);
    }

    @PutMapping(value = "/stopVotingOnSession/{sessionId}")
    public void stopVotingOnSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName)
            throws BadRequestException, NotExistsException {
        Session session = sessionService.stopVoting(sessionId, userName);
        sessionSink.emit(session);
    }

    @PutMapping(value = "/passCreator/{sessionId}")
    public void passCreator(@PathVariable("sessionId") int sessionId, @RequestParam String oldCreator,
                            @RequestParam String newCreator)
            throws NotExistsException, BadRequestException {
        Session session = sessionService.passCreator(sessionId, oldCreator, newCreator);
        sessionSink.emit(session);
    }

    @PutMapping(value = "/stealCreator/{sessionId}")
    public void stealCreator(@PathVariable("sessionId") int sessionId, @RequestParam String userName)
            throws NotExistsException, BadRequestException {
        Session session = sessionService.stealCreator(sessionId, userName);
        sessionSink.emit(session);
    }

    @PutMapping(value = "/kickUser/{sessionId}")
    public void kickUser(@PathVariable("sessionId") int sessionId, @RequestParam String userName,
                         @RequestParam String userToKick)
            throws NotExistsException, BadRequestException {
        Session session = sessionService.kickUser(sessionId, userName, userToKick);
        sessionSink.emit(session);
    }
}