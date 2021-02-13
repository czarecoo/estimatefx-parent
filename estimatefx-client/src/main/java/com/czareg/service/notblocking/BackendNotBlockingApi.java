package com.czareg.service.notblocking;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface BackendNotBlockingApi {
    @GET("/pollSession/{sessionId}")
    Observable<SessionDTO> pollSessionById(@Path("sessionId") int sessionId);

    @GET("/pollSessions")
    Observable<SessionDTO> pollSessions();

    @GET("/pollSessionIdentifiers")
    Observable<List<SessionIdentifierDTO>> pollSessionIdentifiers();
}