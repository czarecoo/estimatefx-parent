package com.czareg.context;

import com.czareg.scene.SceneManager;
import javafx.stage.Stage;

public class Context {
    private Stage stage;
    private SceneManager sceneManager;
    private String name;

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
}
