package com.czareg.service.blocking;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;
import com.czareg.service.shared.BackendServiceException;
import com.czareg.service.shared.BackendServiceExceptionBuilder;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class BackendBlockingServiceImpl implements BackendBlockingService {
    private final BackendBlockingApi backendBlockingApi;

    public BackendBlockingServiceImpl(BackendBlockingApi backendBlockingApi) {
        this.backendBlockingApi = backendBlockingApi;
    }

    @Override
    public SessionDTO createSession(String userName) throws BackendServiceException {
        try {
            Response<SessionDTO> sessionDTOResponse = backendBlockingApi.createSession(userName).execute();
            if (sessionDTOResponse.isSuccessful()) {
                return sessionDTOResponse.body();
            }
            throw new BackendServiceExceptionBuilder()
                    .failedTo("create session")
                    .userName(userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("create session")
                    .userName(userName)
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
                    .sessionId(sessionId)
                    .userName(userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("join session")
                    .sessionId(sessionId)
                    .userName(userName)
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
                    .sessionId(sessionId)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("get session")
                    .sessionId(sessionId)
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
                    .sessionId(sessionId)
                    .userName(userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("start voting on session")
                    .sessionId(sessionId)
                    .userName(userName)
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
                    .sessionId(sessionId)
                    .userName(userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("stop voting on session")
                    .sessionId(sessionId)
                    .userName(userName)
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
                    .sessionId(sessionId)
                    .userName(userName)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("leave session")
                    .sessionId(sessionId)
                    .userName(userName)
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
                    .sessionId(sessionId)
                    .userName(userName)
                    .voteValue(voteValue)
                    .serverMessage(sessionDTOResponse)
                    .build();

        } catch (IOException e) {
            throw new BackendServiceExceptionBuilder()
                    .failedTo("vote on session")
                    .sessionId(sessionId)
                    .userName(userName)
                    .voteValue(voteValue)
                    .cause(e)
                    .build();
        }
    }
}