package com.czareg.session;

import com.czareg.session.exceptions.BadRequestException;

public enum State {
    VOTING {
        @Override
        public void vote(Session session, String userName, int value) {
            session.getVotes().put(userName, value);
        }

        @Override
        public String getDescription() {
            return "The session is now in VOTING state. Please click the button, that most accurately, describes discussed topic's complexity.";
        }
    }, WAITING {
        @Override
        public void vote(Session session, String userName, int value) throws BadRequestException {
            throw new BadRequestException("Voting is disabled in current state. Please wait for creator to start another vote.");
        }

        @Override
        public String getDescription() {
            return "The session is now in WAITING state. Please wait for creator to start another vote.";
        }
    }, CLOSED {
        @Override
        public void vote(Session session, String userName, int value) throws BadRequestException {
            throw new BadRequestException("Voting is disabled in current state.");
        }

        @Override
        public String getDescription() {
            return "The session is CLOSED because the creator left. Consider creating new session.";
        }
    };

    public abstract void vote(Session session, String userName, int value) throws BadRequestException;

    public abstract String getDescription();

    @Override
    public String toString() {
        return getDescription();
    }
}