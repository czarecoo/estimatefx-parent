package com.czareg.context;

import com.czareg.scene.SceneManager;
import com.czareg.service.BackendApi;
import com.czareg.service.BackendApiFactory;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceImpl;
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

    public Context() throws ConfigurationException {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration("application.properties");
        propertiesConfiguration.setThrowExceptionOnMissing(true);
        String baseUrl = propertiesConfiguration.getString("backend.url");
        BackendApiFactory backendApiFactory = new BackendApiFactory();
        BackendApi backendApi = backendApiFactory.createBackendApi(baseUrl);
        BackendService backendService = new BackendServiceImpl(backendApi);
        taskFactory = new TaskFactory(backendService, this);
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
}
