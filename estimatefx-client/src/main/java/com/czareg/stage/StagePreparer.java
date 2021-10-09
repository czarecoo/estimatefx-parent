package com.czareg.stage;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StagePreparer {
    private Stage stage;

    public StagePreparer(Stage stage) {
        this.stage = stage;
    }

    public void prepare() {
        stage.setTitle("EstimateFx");
        stage.setResizable(true);
        stage.setMinWidth(400);
        stage.setMinHeight(450);
        stage.getIcons().add(new Image("icon.png"));
    }
}