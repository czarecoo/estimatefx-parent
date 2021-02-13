package com.czareg.context;

import com.czareg.scene.SceneManager;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.BackendBlockingServiceFactory;
import com.czareg.tasks.TaskFactory;
import javafx.stage.Stage;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Context {
    private String userName;
    private Integer sessionId;
    private Stage stage;
    private SceneManager sceneManager;
    private TaskFactory taskFactory;
    private VoteContext voteContext;

    public Context() throws ConfigurationException {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration("application.properties");
        propertiesConfiguration.setThrowExceptionOnMissing(true);
        BackendBlockingService backendBlockingService = new BackendBlockingServiceFactory().create(propertiesConfiguration);
        //BackendNotBlockingService backendNotBlockingService = new BackendNotBlockingServiceFactory().create(propertiesConfiguration);
        taskFactory = new TaskFactory(backendBlockingService, this);
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
}
