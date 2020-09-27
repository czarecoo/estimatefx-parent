package com.czareg.scene.fxml;

import com.czareg.context.Context;
import com.czareg.stage.ContextAware;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

public class FxmlSceneLoader {
    public Scene load(String fxmlFile, Context context) {
        URL url = createUrlForFxmlFileInResources(fxmlFile);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loadFxmlFile(loader, fxmlFile);
        injectContextIntoFxmlController(loader, context, fxmlFile);
        return new Scene(root);
    }

    private URL createUrlForFxmlFileInResources(String fxmlFile) {
        URL url = getClass().getResource(fxmlFile);
        if (url == null) {
            String message = String.format("Fxml file %s was not found in resources", fxmlFile);
            throw new FxmlSceneLoadingException(message);
        }
        return url;
    }

    private Parent loadFxmlFile(FXMLLoader loader, String fxmlFile) {
        try {
            return loader.load();
        } catch (IOException e) {
            String message = String.format("Fxml file %s failed to load", fxmlFile);
            throw new FxmlSceneLoadingException(message, e);
        }
    }

    private void injectContextIntoFxmlController(FXMLLoader loader, Context context, String fxmlFile) {
        ContextAware controller = loader.getController();
        if (controller == null) {
            String message = String.format("Failed to inject context into controller of %s", fxmlFile);
            throw new FxmlSceneLoadingException(message);
        }
        controller.setContext(context);
    }
}