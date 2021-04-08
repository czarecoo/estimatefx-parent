package com.czareg.dto;

public enum UserType {
    CREATOR, JOINER;

    public boolean isCreator() {
        return this == CREATOR;
    }

    public boolean isJoiner() {
        return this == JOINER;
    }
}
