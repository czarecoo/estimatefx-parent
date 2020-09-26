package com.czareg.scene.fxml;

import com.czareg.context.Context;
import com.czareg.stage.ContextAware;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class FxmlSceneLoader {
    private static final Logger LOG = LogManager.getLogger(FxmlSceneLoader.class);

    public Scene load(String fxmlFile, Context context) {
        URL url = getClass().getResource(fxmlFile);
        if (url == null) {
            LOG.error("The URL for the resource {} was not found", fxmlFile);
            Platform.exit();
            return null;
        }

        FXMLLoader loader = new FXMLLoader(url);
        Scene scene;

        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            LOG.error("Failed to load file: {}", fxmlFile, e);
            Platform.exit();
            return null;
        }

        ContextAware controller = loader.getController();
        if (controller != null) {
            controller.setContext(context);
        } else {
            LOG.error("Failed inject context into controller for scene {}", fxmlFile);
        }

        return scene;
    }
}