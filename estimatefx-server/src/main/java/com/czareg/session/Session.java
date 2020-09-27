package com.czareg.session;

import com.czareg.dto.SessionDTO;
import com.czareg.session.exceptions.BadRequestException;
import com.czareg.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.czareg.session.State.*;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class Session {
    private static final AtomicInteger ID = new AtomicInteger();

    private int sessionId;
    private State state;
    private Instant creationTime;
    private List<User> users;
    private Map<String, String> votes;
    private String description;

    public Session() {
        creationTime = Instant.now();
        state = WAITING;
        sessionId = ID.getAndIncrement();
        users = new LinkedList<>();
        votes = new HashMap<>();
    }

    public void addUser(User user) {
        if (users.isEmpty()) {
            description = user.getName() + "'s session";
        }
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public int getSessionId() {
        return sessionId;
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<String, String> getVotes() {
        return votes;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public String getDescription() {
        return description;
    }

    public State getState() {
        return state;
    }

    public void setClosedState() {
        this.state = CLOSED;
    }

    public void setWaitingState() {
        this.state = WAITING;
    }

    public void setVotingState() {
        this.state = VOTING;
    }

    public void vote(String name, String voteValue) throws BadRequestException {
        getState().vote(this, name, voteValue);
    }

    public SessionDTO toDTO() {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionId);
        sessionDTO.setState(state.toDTO());
        sessionDTO.setCreationTime(creationTime.toString());
        sessionDTO.setUsers(User.mapUsersToDTOs(users));
        sessionDTO.setVotes(votes);
        sessionDTO.setDescription(description);
        return sessionDTO;
    }
}