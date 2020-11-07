package com.czareg.session.model.vote;

public enum VoteState {
    VOTED {
        @Override
        public String toString() {
            return "voted";
        }
    },
    NOT_VOTED {
        @Override
        public String toString() {
            return "not voted";
        }
    }
}