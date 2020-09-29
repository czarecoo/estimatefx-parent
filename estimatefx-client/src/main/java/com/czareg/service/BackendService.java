package com.czareg.service;

import com.czareg.dto.SessionDTO;

import javax.ws.rs.ProcessingException;
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

    public SessionDTO createSession(String userName) throws BackendServiceCallFailedException {
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
                throw new BackendServiceCallFailedException(errorMessage);
            }
        } catch (ProcessingException e) {
            throw new BackendServiceCallFailedException(e);
        }
    }

    public SessionDTO joinSession(int sessionId, String userName) throws BackendServiceCallFailedException {
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
                throw new BackendServiceCallFailedException(errorMessage);
            }
        } catch (ProcessingException e) {
            throw new BackendServiceCallFailedException(e);
        }
    }

    public void startVotingOnSession(int sessionId, String userName) throws BackendServiceCallFailedException {
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
                throw new BackendServiceCallFailedException(errorMessage);
            }
        } catch (ProcessingException e) {
            throw new BackendServiceCallFailedException(e);
        }
    }

    public void stopVotingOnSession(int sessionId, String userName) throws BackendServiceCallFailedException {
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
                throw new BackendServiceCallFailedException(errorMessage);
            }
        } catch (ProcessingException e) {
            throw new BackendServiceCallFailedException(e);
        }
    }

    public List<SessionDTO> getSessions() throws BackendServiceCallFailedException {
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
                throw new BackendServiceCallFailedException(errorMessage);
            }
        } catch (ProcessingException e) {
            throw new BackendServiceCallFailedException(e);
        }
    }

    public SessionDTO getSession(int sessionId) throws BackendServiceCallFailedException {
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
                throw new BackendServiceCallFailedException(errorMessage);
            }
        } catch (ProcessingException e) {
            throw new BackendServiceCallFailedException(e);
        }
    }

    public void leaveSession(int sessionId, String userName) throws BackendServiceCallFailedException {
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
                throw new BackendServiceCallFailedException(errorMessage);
            }
        } catch (ProcessingException e) {
            throw new BackendServiceCallFailedException(e);
        }
    }

    public void voteOnSession(int sessionId, String userName, String voteValue) throws BackendServiceCallFailedException {
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
                throw new BackendServiceCallFailedException(errorMessage);
            }
        } catch (ProcessingException e) {
            throw new BackendServiceCallFailedException(e);
        }
    }
}
