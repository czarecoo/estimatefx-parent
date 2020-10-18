package com.czareg.context;

import com.czareg.scene.SceneManager;
import com.czareg.service.*;
import com.czareg.tasks.TaskFactory;
import javafx.stage.Stage;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import retrofit2.Retrofit;

public class Context {
    private String userName;
    private Integer sessionId;
    private Stage stage;
    private SceneManager sceneManager;
    private TaskFactory taskFactory;

    public Context() throws ConfigurationException {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration("application.properties");
        propertiesConfiguration.setThrowExceptionOnMissing(true);

        Retrofit retrofit = new RetrofitClientFactory().geRetrofitClient(propertiesConfiguration);
        BackendApi backendApi = new BackendApiFactory().createBackendApi(retrofit);
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
