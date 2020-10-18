package com.czareg.session;

import com.czareg.dto.StateDTO;
import com.czareg.session.exceptions.BadRequestException;

import java.util.Map;

public enum State {
    VOTING {
        @Override
        public void vote(Session session, String userName, String voteValue) {
            Map<String, String> votes = session.getVotes();
            votes.remove(userName);
            votes.put(userName, voteValue);
        }

        @Override
        public String getDescription() {
            return "The session is now in VOTING state";
        }
    }, WAITING {
        @Override
        public void vote(Session session, String userName, String voteValue) throws BadRequestException {
            throw new BadRequestException("Voting is disabled in WAITING state");
        }

        @Override
        public String getDescription() {
            return "The session is now in WAITING state";
        }
    }, CLOSED {
        @Override
        public void vote(Session session, String userName, String voteValue) throws BadRequestException {
            throw new BadRequestException("Voting is disabled in CLOSED state");
        }

        @Override
        public String getDescription() {
            return "The session is now in CLOSED state";
        }
    };

    public abstract void vote(Session session, String userName, String voteValue) throws BadRequestException;

    public abstract String getDescription();

    @Override
    public String toString() {
        return getDescription();
    }

    public StateDTO toDTO() {
        return StateDTO.valueOf(this.name());
    }
}