package com.czareg.session.model.vote;

import static com.czareg.session.model.vote.VoteState.NOT_VOTED;
import static com.czareg.session.model.vote.VoteState.VOTED;

public class Vote {
    private VoteState voteState;
    private String voteValue;

    public Vote() {
        this.voteState = NOT_VOTED;
        this.voteValue = "?";
    }

    public Vote(String voteValue) {
        this.voteState = VOTED;
        this.voteValue = voteValue;
    }

    public String getVoteState() {
        return voteState.toString();
    }

    public String getVoteValue() {
        return voteValue;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteState=" + voteState +
                ", voteValue='" + voteValue + '\'' +
                '}';
    }
}