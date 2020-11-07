package com.czareg.session.model.vote;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class Vote {
    private VoteState voteState;
    private String voteValue;

    public Vote(VoteState voteState) {
        this(voteState, "?");
    }

    public Vote(VoteState voteState, String voteValue) {
        this.voteState = voteState;
        this.voteValue = voteValue;
    }

    public String getVoteState() {
        return voteState.toString();
    }

    public String getVoteValue() {
        return voteValue;
    }
}