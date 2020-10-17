package com.czareg.controller;

import com.czareg.context.Context;
import com.czareg.model.Session;
import com.czareg.stage.ContextAware;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.CREATE;

public class JoinController implements ContextAware {
    private static final Logger LOG = LogManager.getLogger(JoinController.class);
    private Context context;

    @FXML
    private TextField nameTextField;

    @FXML
    private ChoiceBox<Session> existingSessionsChoiceBox;

    @Override
    public void initialize(Context context) {
        this.context = context;
        afterContextInitialize();
    }

    private void afterContextInitialize() {
        Platform.runLater(context.getTaskFactory().createSessionsFillChoiceBoxTask(existingSessionsChoiceBox));
    }

    @FXML
    private void handleCreateSessionButtonClicked(ActionEvent event) {
        context.getSceneManager().setScene(CREATE);
    }

    @FXML
    private void handleJoinSessionButtonClicked(ActionEvent event) {
        context.setName(nameTextField.getText());
        context.setSessionId(existingSessionsChoiceBox.getSelectionModel().getSelectedItem().getSessionDTO().getSessionId());

        Platform.runLater(context.getTaskFactory().createJoinSessionTask());
    }
}
