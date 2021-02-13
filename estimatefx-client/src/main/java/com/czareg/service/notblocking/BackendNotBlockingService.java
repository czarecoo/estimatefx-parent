package com.czareg.service.notblocking;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;
import io.reactivex.Observable;

import java.util.List;

public interface BackendNotBlockingService {
    Observable<SessionDTO> pollSession(int sessionId);

    Observable<SessionDTO> pollSessions();

    Observable<List<SessionIdentifierDTO>> pollSessionIdentifiers();
}