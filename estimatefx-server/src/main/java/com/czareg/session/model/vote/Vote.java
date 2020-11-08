package com.czareg.session.model.vote;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.czareg.session.model.vote.VoteState.NOT_VOTED;
import static com.czareg.session.model.vote.VoteState.VOTED;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
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
}