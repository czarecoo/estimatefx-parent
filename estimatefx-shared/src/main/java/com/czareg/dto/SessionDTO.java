package com.czareg.dto;

import java.util.List;
import java.util.Map;

public class SessionDTO {
    private int sessionId;
    private StateDTO state;
    private String creationTime;
    private List<UserDTO> users;
    private Map<String, String> votes;
    private String description;

    public SessionDTO() {
    }

    public SessionDTO(int sessionId, StateDTO state, String creationTime, List<UserDTO> users, Map<String, String> votes, String description) {
        this.sessionId = sessionId;
        this.state = state;
        this.creationTime = creationTime;
        this.users = users;
        this.votes = votes;
        this.description = description;
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

    @Override
    public String toString() {
        return "SessionDTO{" +
                "sessionId=" + sessionId +
                ", state=" + state +
                ", creationTime='" + creationTime + '\'' +
                ", users=" + users +
                ", votes=" + votes +
                ", description='" + description + '\'' +
                '}';
    }
}