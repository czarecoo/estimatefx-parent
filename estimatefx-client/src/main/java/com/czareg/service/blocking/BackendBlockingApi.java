package com.czareg.service.blocking;

import com.czareg.dto.SessionDTO;
import com.czareg.dto.SessionIdentifierDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BackendBlockingApi {
    @POST("/service/v1/createSession")
    Call<SessionDTO> createSession(@Query(value = "userName") String userName,
                                   @Query(value = "allowPassingCreator") boolean allowPassingCreator,
                                   @Query(value = "allowStealingCreator") boolean allowStealingCreator,
                                   @Query(value = "passCreatorWhenLeaving") boolean passCreatorWhenLeaving);

    @PUT("/service/v1/joinSession/{sessionId}")
    Call<SessionDTO> joinSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName);

    @GET("/service/v1/getSession/{sessionId}")
    Call<SessionDTO> getSessionById(@Path("sessionId") int sessionId);

    @GET("/service/v1/getSessions")
    Call<List<SessionDTO>> getSessions();

    @GET("/service/v1/getSessionIdentifiers")
    Call<List<SessionIdentifierDTO>> getSessionIdentifiers();

    @PUT("/service/v1/voteOnSession/{sessionId}")
    Call<Void> voteOnSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName, @Query(value = "voteValue") String voteValue);

    @DELETE("/service/v1/leaveSession/{sessionId}")
    Call<Void> leaveSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName);

    @PUT("/service/v1/startVotingOnSession/{sessionId}")
    Call<Void> startVotingOnSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName);

    @PUT("/service/v1/stopVotingOnSession/{sessionId}")
    Call<Void> stopVotingOnSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName);
}