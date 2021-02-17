package com.czareg.session.model;

import com.czareg.dto.StateDTO;
import com.czareg.session.exceptions.BadRequestException;
import com.czareg.session.model.user.User;
import com.czareg.session.model.vote.UserVotes;

public enum State {
    VOTING {
        @Override
        public void vote(Session session, User user, String voteValue) {
            UserVotes userVotes = session.getUserVotes();
            userVotes.addVote(user, voteValue);
        }

        @Override
        public String getDescription() {
            return "The session is now in VOTING state";
        }
    }, WAITING {
        @Override
        public void vote(Session session, User user, String voteValue) throws BadRequestException {
            throw new BadRequestException("Voting is disabled in WAITING state");
        }

        @Override
        public String getDescription() {
            return "The session is now in WAITING state";
        }
    };

    public abstract void vote(Session session, User user, String voteValue) throws BadRequestException;

    public abstract String getDescription();

    @Override
    public String toString() {
        return getDescription();
    }

    public StateDTO toDTO() {
        return StateDTO.valueOf(this.name());
    }
}