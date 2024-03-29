package com.czareg.service.blocking;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.service.blocking.utils.BackendServiceException;

import java.util.List;

public interface BackendBlockingService {
    SessionDTO createSession(String userName,
                             boolean allowStealingCreator,
                             boolean passCreatorWhenLeaving) throws BackendServiceException;

    SessionDTO joinSession(int sessionId, String userName) throws BackendServiceException;

    SessionDTO getSession(int sessionId) throws BackendServiceException;

    List<SessionDTO> getSessions() throws BackendServiceException;

    List<SessionIdentifierDTO> getSessionIdentifiers() throws BackendServiceException;

    void startVotingOnSession(int sessionId, String userName) throws BackendServiceException;

    void stopVotingOnSession(int sessionId, String userName) throws BackendServiceException;

    void leaveSession(int sessionId, String userName) throws BackendServiceException;

    void voteOnSession(int sessionId, String userName, String voteValue) throws BackendServiceException;

    void passCreator(int sessionId, String oldCreator, String newCreator) throws BackendServiceException;

    void stealCreator(int sessionId, String userName) throws BackendServiceException;

    void kickUser(int sessionId, String userName, String userToKick) throws BackendServiceException;
}