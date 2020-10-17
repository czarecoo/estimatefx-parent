package com.czareg.service;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;

import java.util.List;

public interface BackendService {
    SessionDTO createSession(String userName) throws BackendServiceException;

    SessionDTO joinSession(int sessionId, String userName) throws BackendServiceException;

    SessionDTO getSession(int sessionId) throws BackendServiceException;

    List<SessionDTO> getSessions() throws BackendServiceException;

    List<SessionIdentifierDTO> getSessionIdentifiers() throws BackendServiceException;

    void startVotingOnSession(int sessionId, String userName) throws BackendServiceException;

    void stopVotingOnSession(int sessionId, String userName) throws BackendServiceException;

    void leaveSession(int sessionId, String userName) throws BackendServiceException;

    void voteOnSession(int sessionId, String userName, String voteValue) throws BackendServiceException;
}
