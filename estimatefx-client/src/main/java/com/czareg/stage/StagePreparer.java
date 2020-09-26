package com.czareg.stage;

import javafx.stage.Stage;

public class StagePreparer {
    private Stage stage;

    public StagePreparer(Stage stage) {
        this.stage = stage;
    }

    public void prepare() {
        stage.setTitle("EstimateFx");
        stage.setResizable(true);
    }
}
