package com.czareg.model;

import java.util.Objects;

public class VoteValue implements Comparable<VoteValue> {
    private String vote;

    public VoteValue(String vote) {
        this.vote = vote;
    }

    public String getVote() {
        return vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteValue voteValue1 = (VoteValue) o;
        return Objects.equals(vote, voteValue1.vote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vote);
    }

    @Override
    public String toString() {
        return vote;
    }

    @Override
    public int compareTo(VoteValue otherVote) {
        if (isInt(vote) && otherVote.isInt(otherVote.vote)) {
            int firstVote = Integer.parseInt(vote);
            int secondVote = Integer.parseInt(otherVote.vote);
            return Integer.compare(firstVote, secondVote);
        } else if (isInt(vote) && isNotInt(otherVote.vote)) {
            return 1;
        } else if (isNotInt(vote) && isInt(otherVote.vote)) {
            return -1;
        } else {
            return 0;
        }
    }

    private boolean isNotInt(String s) {
        return !isInt(s);
    }

    private boolean isInt(String s) {
        return s.matches("\\d+");
    }
}
