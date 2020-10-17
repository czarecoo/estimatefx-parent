package com.czareg.service;

import com.czareg.dto.SessionDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BackendApi {
    @POST("/createSession")
    Call<SessionDTO> createSession(@Query(value = "userName") String userName);

    @PUT("/joinSession/{sessionId}")
    Call<SessionDTO> joinSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName);

    @GET("/getSession/{sessionId}")
    Call<SessionDTO> getSessionById(@Path("sessionId") int sessionId);

    @GET("/getSessions")
    Call<List<SessionDTO>> getSessions();

    @PUT("/voteOnSession/{sessionId}")
    Call<Void> voteOnSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName, @Query(value = "voteValue") String voteValue);

    @DELETE("/leaveSession/{sessionId}")
    Call<Void> leaveSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName);

    @PUT("/startVotingOnSession/{sessionId}")
    Call<Void> startVotingOnSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName);

    @PUT("/stopVotingOnSession/{sessionId}")
    Call<Void> stopVotingOnSession(@Path("sessionId") int sessionId, @Query(value = "userName") String userName);
}