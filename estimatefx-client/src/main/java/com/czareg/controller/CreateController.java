package com.czareg.controller;

import com.czareg.context.Context;
import com.czareg.stage.ContextAware;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.JOIN;

public class CreateController implements ContextAware {
    private static final Logger LOG = LogManager.getLogger(CreateController.class);
    private Context context;

    @FXML
    private TextField nameTextField;

    @Override
    public void initialize(Context context) {
        this.context = context;
    }

    @FXML
    private void handleJoinSessionButtonClicked(ActionEvent event) {
        context.getSceneManager().setScene(JOIN);
    }

    @FXML
    private void handleCreateSessionButtonClicked(ActionEvent event) {
        context.setName(nameTextField.getText());
        Platform.runLater(context.getTaskFactory().createCreateSessionTask());
    }
}
