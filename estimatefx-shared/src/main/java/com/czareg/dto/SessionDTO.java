package com.czareg.dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SessionDTO {
    private int sessionId;
    private StateDTO state;
    private String creationTime;
    private List<UserDTO> users;
    private Map<String, String> votes;
    private String description;
    private boolean allowPassingCreator;
    private boolean allowStealingCreator;
    private boolean passCreatorWhenLeaving;

    public SessionDTO() {
    }

    public SessionDTO(int sessionId, StateDTO state, String creationTime, List<UserDTO> users, Map<String, String> votes,
                      String description, boolean allowPassingCreator, boolean allowStealingCreator, boolean passCreatorWhenLeaving) {
        this.sessionId = sessionId;
        this.state = state;
        this.creationTime = creationTime;
        this.users = users;
        this.votes = votes;
        this.description = description;
        this.allowPassingCreator = allowPassingCreator;
        this.allowStealingCreator = allowStealingCreator;
        this.passCreatorWhenLeaving = passCreatorWhenLeaving;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public StateDTO getState() {
        return state;
    }

    public void setState(StateDTO state) {
        this.state = state;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public Map<String, String> getVotes() {
        return votes;
    }

    public void setVotes(Map<String, String> votes) {
        this.votes = votes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAllowPassingCreator() {
        return allowPassingCreator;
    }

    public void setAllowPassingCreator(boolean allowPassingCreator) {
        this.allowPassingCreator = allowPassingCreator;
    }

    public boolean isAllowStealingCreator() {
        return allowStealingCreator;
    }

    public void setAllowStealingCreator(boolean allowStealingCreator) {
        this.allowStealingCreator = allowStealingCreator;
    }

    public boolean isPassCreatorWhenLeaving() {
        return passCreatorWhenLeaving;
    }

    public void setPassCreatorWhenLeaving(boolean passCreatorWhenLeaving) {
        this.passCreatorWhenLeaving = passCreatorWhenLeaving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDTO that = (SessionDTO) o;
        return sessionId == that.sessionId &&
                allowPassingCreator == that.allowPassingCreator &&
                allowStealingCreator == that.allowStealingCreator &&
                passCreatorWhenLeaving == that.passCreatorWhenLeaving &&
                state == that.state &&
                Objects.equals(creationTime, that.creationTime) &&
                Objects.equals(users, that.users) &&
                Objects.equals(votes, that.votes) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, state, creationTime, users, votes, description, allowPassingCreator, allowStealingCreator, passCreatorWhenLeaving);
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
                "sessionId=" + sessionId +
                ", state=" + state +
                ", creationTime='" + creationTime + '\'' +
                ", users=" + users +
                ", votes=" + votes +
                ", description='" + description + '\'' +
                ", allowPassingCreator=" + allowPassingCreator +
                ", allowStealingCreator=" + allowStealingCreator +
                ", passCreatorWhenLeaving=" + passCreatorWhenLeaving +
                '}';
    }
}