package com.czareg.service.notblocking;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;
import io.reactivex.Observable;

import java.util.List;

public class BackendNotBlockingServiceImpl implements BackendNotBlockingService {
    private final BackendNotBlockingApi backendNotBlockingApi;

    public BackendNotBlockingServiceImpl(BackendNotBlockingApi backendNotBlockingApi) {
        this.backendNotBlockingApi = backendNotBlockingApi;
    }

    @Override
    public Observable<SessionDTO> pollSession(int sessionId) {
        return backendNotBlockingApi.pollSessionById(sessionId);
    }

    @Override
    public Observable<SessionDTO> pollSessions() {
        return backendNotBlockingApi.pollSessions();
    }

    @Override
    public Observable<List<SessionIdentifierDTO>> pollSessionIdentifiers() {
        return backendNotBlockingApi.pollSessionIdentifiers();
    }
}