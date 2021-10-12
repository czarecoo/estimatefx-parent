package com.czareg.session.model;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.dto.VoteDTO;
import com.czareg.session.exceptions.BadRequestException;
import com.czareg.session.model.user.User;
import com.czareg.session.model.vote.UserVotes;
import com.czareg.session.model.vote.Vote;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.czareg.session.model.State.VOTING;
import static com.czareg.session.model.State.WAITING;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Data
@Component
@Scope(SCOPE_PROTOTYPE)
public class Session {
    private static final AtomicInteger ID = new AtomicInteger();
    private int sessionId;
    private State state;
    private Instant creationTime;
    private UserVotes userVotes;
    private String description;
    private boolean allowStealingCreator;
    private boolean passCreatorWhenLeaving;

    public Session(UserVotes userVotes) {
        creationTime = Instant.now();
        state = WAITING;
        sessionId = ID.getAndIncrement();
        this.userVotes = userVotes;
    }

    public void addCreator(User user) {
        description = user.getName() + "'s session";
        addUser(user);
    }

    public void addJoiner(User user) {
        addUser(user);
    }

    private void addUser(User user) {
        userVotes.addUser(user);
    }

    public void removeUser(User user) {
        userVotes.removeUser(user);
    }

    public void setWaitingState() {
        this.state = WAITING;
    }

    public void setVotingState() {
        this.state = VOTING;
    }

    public void vote(User user, String voteValue) throws BadRequestException {
        getState().vote(this, user, voteValue);
    }

    public SessionDTO toSessionDTO() {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionId);
        sessionDTO.setState(state.toDTO());
        sessionDTO.setCreationTime(creationTime.toString());
        sessionDTO.setVoteDTOs(getVotesDependingOnState());
        sessionDTO.setDescription(description);
        sessionDTO.setAllowStealingCreator(allowStealingCreator);
        sessionDTO.setPassCreatorWhenLeaving(passCreatorWhenLeaving);
        return sessionDTO;
    }

    private List<VoteDTO> getVotesDependingOnState() {
        if (state == VOTING) {
            return userVotes.toVoteDTO(Vote::getVoteState);
        }
        return userVotes.toVoteDTO(Vote::getVoteValue);
    }

    public SessionIdentifierDTO toSessionIdentifierDTO() {
        SessionIdentifierDTO sessionIdentifierDTO = new SessionIdentifierDTO();
        sessionIdentifierDTO.setSessionId(sessionId);
        sessionIdentifierDTO.setDescription(description);
        return sessionIdentifierDTO;
    }

    public boolean isEmpty() {
        return userVotes.isEmpty();
    }

    public Set<User> getUsers() {
        return userVotes.getUsers();
    }

    public boolean isAllowStealingCreator() {
        return allowStealingCreator;
    }

    public void setAllowStealingCreator(boolean allowStealingCreator) {
        this.allowStealingCreator = allowStealingCreator;
    }

    public boolean isPassCreatorWhenLeaving() {
        return passCreatorWhenLeaving;
    }

    public void setPassCreatorWhenLeaving(boolean passCreatorWhenLeaving) {
        this.passCreatorWhenLeaving = passCreatorWhenLeaving;
    }
}