package com.czareg.dto;

import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class SessionDTO {
    private int sessionId;
    private StateDTO state;
    private String creationTime;
    private List<VoteDTO> voteDTOs;
    private String description;
    private boolean allowStealingCreator;
    private boolean passCreatorWhenLeaving;

    public List<UserDTO> getUserDTOs() {
        return voteDTOs.stream()
                .map(VoteDTO::getUserDTO)
                .collect(toList());
    }

    public List<String> getVoteValues() {
        return voteDTOs.stream()
                .map(VoteDTO::getVoteValue)
                .collect(toList());
    }
}