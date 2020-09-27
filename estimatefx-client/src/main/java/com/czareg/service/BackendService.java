package com.czareg.service;

import com.czareg.dto.SessionDTO;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class BackendService {
    private String baseUrl;

    public BackendService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public SessionDTO createSession(String userName) throws BackendServiceCallFailed {
        try (ClosableClient client = new ClosableClient()) {
            Response response = client
                    .target(baseUrl)
                    .path("createSession")
                    .queryParam("userName", userName)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.text(""));

            if (response.getStatus() == 200) {
                return response.readEntity(SessionDTO.class);
            } else {
                String errorMessage = response.readEntity(String.class);
                throw new BackendServiceCallFailed(errorMessage);
            }
        }
    }

    public SessionDTO joinSession(int sessionId, String userName) throws BackendServiceCallFailed {
        try (ClosableClient client = new ClosableClient()) {
            Response response = client
                    .target(baseUrl)
                    .path("joinSession")
                    .path(String.valueOf(sessionId))
                    .queryParam("userName", userName)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .put(Entity.text(""));

            if (response.getStatus() == 200) {
                return response.readEntity(SessionDTO.class);
            } else {
                String errorMessage = response.readEntity(String.class);
                throw new BackendServiceCallFailed(errorMessage);
            }
        }
    }

    public void startVotingOnSession(int sessionId, String userName) throws BackendServiceCallFailed {
        try (ClosableClient client = new ClosableClient()) {
            Response response = client
                    .target(baseUrl)
                    .path("startVotingOnSession")
                    .path(String.valueOf(sessionId))
                    .queryParam("userName", userName)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .put(Entity.text(""));

            if (response.getStatus() != 200) {
                String errorMessage = response.readEntity(String.class);
                throw new BackendServiceCallFailed(errorMessage);
            }
        }
    }

    public void stopVotingOnSession(int sessionId, String userName) throws BackendServiceCallFailed {
        try (ClosableClient client = new ClosableClient()) {
            Response response = client
                    .target(baseUrl)
                    .path("stopVotingOnSession")
                    .path(String.valueOf(sessionId))
                    .queryParam("userName", userName)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .put(Entity.text(""));

            if (response.getStatus() != 200) {
                String errorMessage = response.readEntity(String.class);
                throw new BackendServiceCallFailed(errorMessage);
            }
        }
    }

    public List<SessionDTO> getSessions() throws BackendServiceCallFailed {
        try (ClosableClient client = new ClosableClient()) {
            Response response = client
                    .target(baseUrl)
                    .path("getSessions")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(new GenericType<List<SessionDTO>>() {
                });
            } else {
                String errorMessage = response.readEntity(String.class);
                throw new BackendServiceCallFailed(errorMessage);
            }
        }
    }

    public SessionDTO getSession(int sessionId) throws BackendServiceCallFailed {
        try (ClosableClient client = new ClosableClient()) {
            Response response = client
                    .target(baseUrl)
                    .path("getSession")
                    .path(String.valueOf(sessionId))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(SessionDTO.class);
            } else {
                String errorMessage = response.readEntity(String.class);
                throw new BackendServiceCallFailed(errorMessage);
            }
        }
    }

    public void leaveSession(int sessionId, String userName) throws BackendServiceCallFailed {
        try (ClosableClient client = new ClosableClient()) {
            Response response = client
                    .target(baseUrl)
                    .path("leaveSession")
                    .path(String.valueOf(sessionId))
                    .queryParam("userName", userName)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .delete();

            if (response.getStatus() != 200) {
                String errorMessage = response.readEntity(String.class);
                throw new BackendServiceCallFailed(errorMessage);
            }
        }
    }

    public void voteOnSession(int sessionId, String userName, String voteValue) throws BackendServiceCallFailed {
        try (ClosableClient client = new ClosableClient()) {
            Response response = client
                    .target(baseUrl)
                    .path("voteOnSession")
                    .path(String.valueOf(sessionId))
                    .queryParam("userName", userName)
                    .queryParam("voteValue", voteValue)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .put(Entity.text(""));

            if (response.getStatus() != 200) {
                String errorMessage = response.readEntity(String.class);
                throw new BackendServiceCallFailed(errorMessage);
            }
        }
    }
}
