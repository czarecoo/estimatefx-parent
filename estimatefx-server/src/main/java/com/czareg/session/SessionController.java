package com.czareg.session;

import com.czareg.session.exceptions.BadRequestException;
import com.czareg.session.exceptions.NotExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
public class SessionController {
    private SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(value = "/createSession")
    public ResponseEntity<Session> createSession(@RequestParam String userName) {
        Session session = sessionService.create(userName);
        return ResponseEntity
                .status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(session);
    }

    @PutMapping(value = "/joinSession/{sessionId}")
    public ResponseEntity<Session> joinSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName) {
        try {
            Session session = sessionService.join(sessionId, userName);
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .body(session);
        } catch (NotExistsException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        } catch (BadRequestException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/getSession/{sessionId}")
    public ResponseEntity<Session> getSessionById(@PathVariable("sessionId") int sessionId) {
        try {
            Session session = sessionService.getSession(sessionId);
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .body(session);
        } catch (NotExistsException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/getSessions")
    public ResponseEntity<List<Session>> getSessions() {
        List<Session> sessions = sessionService.getSessions();
        return ResponseEntity
                .ok()
                .contentType(APPLICATION_JSON)
                .body(sessions);
    }

    @PutMapping(value = "/voteOnSession/{sessionId}")
    public void voteOnSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName, @RequestParam int value) {
        try {
            sessionService.vote(sessionId, userName, value);
        } catch (NotExistsException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        } catch (BadRequestException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(value = "/leaveSession/{sessionId}")
    public void leaveSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName) {
        try {
            sessionService.leave(sessionId, userName);
        } catch (NotExistsException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping(value = "/startVotingOnSession/{sessionId}")
    public void startVotingOnSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName) {
        try {
            sessionService.startVoting(sessionId, userName);
        } catch (NotExistsException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        } catch (BadRequestException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(value = "/stopVotingOnSession/{sessionId}")
    public void stopVotingOnSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName) {
        try {
            sessionService.stopVoting(sessionId, userName);
        } catch (NotExistsException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        } catch (BadRequestException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }
}