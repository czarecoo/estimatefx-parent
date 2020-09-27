package com.czareg.user;

public enum Type {
    CREATOR, JOINER;

    public boolean isCreator() {
        return this.equals(CREATOR);
    }
}