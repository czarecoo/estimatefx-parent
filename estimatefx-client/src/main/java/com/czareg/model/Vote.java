package com.czareg.model;

import com.czareg.dto.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Vote {
    private String name;
    private UserType userType;
    private VoteValue voteValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(name, vote.name) &&
                Objects.equals(voteValue, vote.voteValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, voteValue);
    }
}