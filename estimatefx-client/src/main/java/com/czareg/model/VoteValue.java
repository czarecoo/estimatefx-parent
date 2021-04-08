package com.czareg.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteValue implements Comparable<VoteValue> {
    private String vote;

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

    @Override
    public String toString() {
        return vote;
    }
}