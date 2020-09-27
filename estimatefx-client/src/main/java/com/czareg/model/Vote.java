package com.czareg.model;

import java.util.Objects;

public class Vote {
    private String name;
    private VoteValue voteValue;

    public Vote(String name, String vote) {
        this.name = name;
        this.voteValue = new VoteValue(vote);
    }

    public String getName() {
        return name;
    }

    public VoteValue getVoteValue() {
        return voteValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote1 = (Vote) o;
        return Objects.equals(name, vote1.name) &&
                Objects.equals(voteValue, vote1.voteValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, voteValue);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "name='" + name + '\'' +
                ", vote='" + voteValue + '\'' +
                '}';
    }
}
