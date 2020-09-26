package com.czareg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Client extends Application {
    private static final Logger LOG = LogManager.getLogger(Client.class);

    public static void main(String[] args) {
        launch(Client.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/join.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 150);
        stage.setTitle("EstimateFx");
        stage.setResizable(false);
        stage.setScene(scene);
        LOG.info("Started UI");
        stage.show();
    }
}