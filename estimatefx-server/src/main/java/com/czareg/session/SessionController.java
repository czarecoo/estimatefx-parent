package com.czareg.session;

import com.czareg.dto.SessionDTO;
import com.czareg.session.exceptions.BadRequestException;
import com.czareg.session.exceptions.NotExistsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SessionController {
    private SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(value = "/createSession")
    public SessionDTO createSession(@RequestParam String userName) {
        Session session = sessionService.create(userName);
        return session.toDTO();
    }

    @PutMapping(value = "/joinSession/{sessionId}")
    public SessionDTO joinSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName)
            throws BadRequestException, NotExistsException {
        Session session = sessionService.join(sessionId, userName);
        return session.toDTO();
    }

    @GetMapping(value = "/getSession/{sessionId}")
    public SessionDTO getSessionById(@PathVariable("sessionId") int sessionId) throws NotExistsException {
        Session session = sessionService.getSession(sessionId);
        return session.toDTO();
    }

    @GetMapping(value = "/getSessions")
    public List<SessionDTO> getSessions() {
        List<Session> sessions = sessionService.getSessions();
        return sessions.stream()
                .map(Session::toDTO)
                .collect(Collectors.toList());
    }

    @PutMapping(value = "/voteOnSession/{sessionId}")
    public void voteOnSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName,
                              @RequestParam String voteValue)
            throws BadRequestException, NotExistsException {
        sessionService.vote(sessionId, userName, voteValue);
    }

    @DeleteMapping(value = "/leaveSession/{sessionId}")
    public void leaveSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName)
            throws NotExistsException {
        sessionService.leave(sessionId, userName);
    }

    @PutMapping(value = "/startVotingOnSession/{sessionId}")
    public void startVotingOnSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName)
            throws BadRequestException, NotExistsException {
        sessionService.startVoting(sessionId, userName);
    }

    @PutMapping(value = "/stopVotingOnSession/{sessionId}")
    public void stopVotingOnSession(@PathVariable("sessionId") int sessionId, @RequestParam String userName)
            throws BadRequestException, NotExistsException {
        sessionService.stopVoting(sessionId, userName);
    }
}