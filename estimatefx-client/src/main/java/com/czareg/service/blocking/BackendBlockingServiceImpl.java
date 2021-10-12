package com.czareg.service.blocking;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.service.blocking.utils.BackendServiceException;
import com.czareg.service.blocking.utils.BackendServiceExceptionBuilder;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class BackendBlockingServiceImpl implements BackendBlockingService {
    private final BackendBlockingApi backendBlockingApi;

    public BackendBlockingServiceImpl(BackendBlockingApi backendBlockingApi) {
        this.backendBlockingApi = backendBlockingApi;
    }

    @Override
    public SessionDTO createSession(String userName,
                                    boolean allowStealingCreator,
                                    boolean passCreatorWhenLeaving) throws BackendServiceException {
        try {
            Call<SessionDTO> session = backendBlockingApi.createSession(userName,
                    allowStealingCreator, passCreatorWhenLeaving);
            Response<SessionDTO> sessionDTOResponse = session.execute();
            if (sessionDTOResponse.isSuccessful()) {
                return sessionDTOResponse.body();
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("create session")
                    .parameter("userName", userName)
                    .parameter("allowStealingCreator", allowStealingCreator)
                    .parameter("passCreatorWhenLeaving", passCreatorWhenLeaving)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("create session")
                    .parameter("userName", userName)
                    .parameter("allowStealingCreator", allowStealingCreator)
                    .parameter("passCreatorWhenLeaving", passCreatorWhenLeaving)
                    .cause(e)
                    .build();
        }
    }

    @Override
    public SessionDTO joinSession(int sessionId, String userName) throws BackendServiceException {
        try {
            Response<SessionDTO> sessionDTOResponse = backendBlockingApi.joinSession(sessionId, userName).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return sessionDTOResponse.body();
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("join session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("join session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .cause(e)
                    .build();
        }
    }

    @Override
    public SessionDTO getSession(int sessionId) throws BackendServiceException {
        try {
            Response<SessionDTO> sessionDTOResponse = backendBlockingApi.getSessionById(sessionId).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return sessionDTOResponse.body();
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("get session")
                    .parameter("sessionId", sessionId)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("get session")
                    .parameter("sessionId", sessionId)
                    .cause(e)
                    .build();
        }
    }

    @Override
    public List<SessionDTO> getSessions() throws BackendServiceException {
        try {
            Response<List<SessionDTO>> sessionDTOResponse = backendBlockingApi.getSessions().execute();
            if (sessionDTOResponse.isSuccessful()) {
                return sessionDTOResponse.body();
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("get sessions")
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("get sessions")
                    .cause(e)
                    .build();
        }
    }

    @Override
    public List<SessionIdentifierDTO> getSessionIdentifiers() throws BackendServiceException {
        try {
            Response<List<SessionIdentifierDTO>> sessionDTOResponse = backendBlockingApi.getSessionIdentifiers().execute();
            if (sessionDTOResponse.isSuccessful()) {
                return sessionDTOResponse.body();
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("get session identifiers")
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("get session identifiers")
                    .cause(e)
                    .build();
        }
    }

    @Override
    public void startVotingOnSession(int sessionId, String userName) throws BackendServiceException {
        try {
            Response<Void> sessionDTOResponse = backendBlockingApi.startVotingOnSession(sessionId, userName).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return;
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("start voting on session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("start voting on session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .cause(e)
                    .build();
        }
    }

    @Override
    public void stopVotingOnSession(int sessionId, String userName) throws BackendServiceException {
        try {
            Response<Void> sessionDTOResponse = backendBlockingApi.stopVotingOnSession(sessionId, userName).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return;
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("stop voting on session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("stop voting on session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .cause(e)
                    .build();
        }
    }

    @Override
    public void leaveSession(int sessionId, String userName) throws BackendServiceException {
        try {
            Response<Void> sessionDTOResponse = backendBlockingApi.leaveSession(sessionId, userName).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return;
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("leave session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("leave session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .cause(e)
                    .build();
        }
    }

    @Override
    public void voteOnSession(int sessionId, String userName, String voteValue) throws BackendServiceException {
        try {
            Response<Void> sessionDTOResponse = backendBlockingApi.voteOnSession(sessionId, userName, voteValue).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return;
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("vote on session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .parameter("voteValue", voteValue)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("vote on session")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .parameter("voteValue", voteValue)
                    .cause(e)
                    .build();
        }
    }

    @Override
    public void passCreator(int sessionId, String oldCreator, String newCreator) throws BackendServiceException {
        try {
            Response<Void> sessionDTOResponse = backendBlockingApi.passCreator(sessionId, oldCreator, newCreator).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return;
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("pass creator")
                    .parameter("sessionId", sessionId)
                    .parameter("oldCreator", oldCreator)
                    .parameter("newCreator", newCreator)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("pass creator")
                    .parameter("sessionId", sessionId)
                    .parameter("oldCreator", oldCreator)
                    .parameter("newCreator", newCreator)
                    .cause(e)
                    .build();
        }
    }

    @Override
    public void stealCreator(int sessionId, String userName) throws BackendServiceException {
        try {
            Response<Void> sessionDTOResponse = backendBlockingApi.stealCreator(sessionId, userName).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return;
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("steal creator")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("steal creator")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .cause(e)
                    .build();
        }
    }

    @Override
    public void kickUser(int sessionId, String userName, String userToKick) throws BackendServiceException {
        try {
            Response<Void> sessionDTOResponse = backendBlockingApi.kickUser(sessionId, userName, userToKick).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return;
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("kick user")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .parameter("userToKick", userToKick)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("kick user")
                    .parameter("sessionId", sessionId)
                    .parameter("userName", userName)
                    .parameter("userToKick", userToKick)
                    .cause(e)
                    .build();
        }
    }
}