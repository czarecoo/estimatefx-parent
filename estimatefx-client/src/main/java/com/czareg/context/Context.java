package com.czareg.context;

import com.czareg.scene.SceneManager;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.BackendBlockingServiceFactory;
import com.czareg.service.notblocking.polling.PollingService;
import com.czareg.tasks.TaskFactory;
import javafx.stage.Stage;
import org.apache.commons.configuration.ConfigurationException;

public class Context {
    private String userName;
    private Integer sessionId;
    private Stage stage;
    private SceneManager sceneManager;
    private final TaskFactory taskFactory;
    private VoteContext voteContext;
    private final PollingServicesManager pollingServicesManager;
    private final PropertiesManager propertiesManager;

    public Context() throws ConfigurationException {
        propertiesManager = new PropertiesManager();
        BackendBlockingServiceFactory backendBlockingServiceFactory = new BackendBlockingServiceFactory(propertiesManager);
        BackendBlockingService backendBlockingService = backendBlockingServiceFactory.create();
        taskFactory = new TaskFactory(backendBlockingService, this);
        pollingServicesManager = new PollingServicesManager();
    }

    public void add(PollingService pollingService) {
        pollingServicesManager.add(pollingService);
    }

    public void remove(PollingService pollingService) {
        pollingServicesManager.remove(pollingService);
    }

    public void close() {
        pollingServicesManager.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public TaskFactory getTaskFactory() {
        return taskFactory;
    }

    public void setVoteContext(VoteContext voteContext) {
        this.voteContext = voteContext;
    }

    public VoteContext getVoteContext() {
        return voteContext;
    }

    public PropertiesManager getPropertiesManager() {
        return propertiesManager;
    }
}