package com.czareg.context;

import com.czareg.scene.SceneManager;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.BackendBlockingServiceFactory;
import com.czareg.service.notblocking.polling.PollingService;
import com.czareg.tasks.TaskFactory;
import com.czareg.tasks.TaskScheduler;
import com.czareg.tasks.ThreadPoolManager;
import javafx.stage.Stage;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Context {
    private static final Logger LOG = LogManager.getLogger(Context.class);
    private String userName;
    private Integer sessionId;
    private Stage stage;
    private SceneManager sceneManager;
    private VoteContext voteContext;
    private CreateContext createContext;
    private TaskFactory taskFactory;

    private PollingServicesManager pollingServicesManager;
    private PropertiesManager propertiesManager;
    private ThreadPoolManager threadPoolManager;
    private TaskScheduler taskScheduler;

    public Context() throws ConfigurationException {
        propertiesManager = new PropertiesManager();
        BackendBlockingServiceFactory backendBlockingServiceFactory = new BackendBlockingServiceFactory(propertiesManager);
        BackendBlockingService backendBlockingService = backendBlockingServiceFactory.create();
        taskFactory = new TaskFactory(backendBlockingService, this);
        threadPoolManager = new ThreadPoolManager(propertiesManager);
        taskScheduler = new TaskScheduler(taskFactory, threadPoolManager);
        pollingServicesManager = new PollingServicesManager();
    }

    public void add(PollingService pollingService) {
        pollingServicesManager.add(pollingService);
    }

    public void remove(PollingService pollingService) {
        pollingServicesManager.remove(pollingService);
    }

    public void close() {
        LOG.info("Closing context");
        threadPoolManager.close();
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

    public void setVoteContext(VoteContext voteContext) {
        this.voteContext = voteContext;
    }

    public VoteContext getVoteContext() {
        return voteContext;
    }

    public PropertiesManager getPropertiesManager() {
        return propertiesManager;
    }

    public CreateContext getCreateContext() {
        return createContext;
    }

    public void setCreateContext(CreateContext createContext) {
        this.createContext = createContext;
    }

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public PollingServicesManager getPollingServicesManager() {
        return pollingServicesManager;
    }
}