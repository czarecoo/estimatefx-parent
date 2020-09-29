package com.czareg.scene;

import com.czareg.context.Context;
import com.czareg.scene.fxml.FxmlScene;
import com.czareg.scene.fxml.FxmlSceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import static com.czareg.scene.fxml.FxmlScene.*;

public class SceneManager {
    private Map<FxmlScene, String> fxmlSceneFiles;
    private FxmlSceneLoader fxmlSceneLoader;
    private Context context;

    public SceneManager(Context context) {
        this.context = context;
        context.setSceneManager(this);
        this.fxmlSceneFiles = new HashMap<>();
        fxmlSceneFiles.put(JOIN, "join.fxml");
        fxmlSceneFiles.put(CREATE, "create.fxml");
        fxmlSceneFiles.put(VOTE, "vote.fxml");
        this.fxmlSceneLoader = new FxmlSceneLoader();
    }

    public void setScene(FxmlScene fxmlScene) {
        Scene scene = loadScene(fxmlScene);
        Stage stage = context.getStage();
        stage.setScene(scene);
    }

    private Scene loadScene(FxmlScene fxmlScene) {
        String fxmlFile = fxmlSceneFiles.get(fxmlScene);
        return fxmlSceneLoader.load(fxmlFile, context);
    }
}
