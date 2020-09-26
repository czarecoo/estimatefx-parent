package com.czareg;

import java.util.Objects;

public class Vote {
    private String name;
    private String vote;

    public Vote(String name, String vote) {
        this.name = name;
        this.vote = vote;
    }

    public String getName() {
        return name;
    }

    public String getVote() {
        return vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote1 = (Vote) o;
        return Objects.equals(name, vote1.name) &&
                Objects.equals(vote, vote1.vote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, vote);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "name='" + name + '\'' +
                ", vote='" + vote + '\'' +
                '}';
    }
}
