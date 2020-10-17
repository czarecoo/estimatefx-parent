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
    private String name;
    private int sessionId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public TaskFactory getTaskFactory() {
        return taskFactory;
    }
}
