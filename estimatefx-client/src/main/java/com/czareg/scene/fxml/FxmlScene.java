package com.czareg.scene.fxml;

public enum FxmlScene {
    JOIN(300, 250),
    CREATE(300, 280),
    VOTE(400, 500);

    private int width;
    private int height;

    FxmlScene(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}