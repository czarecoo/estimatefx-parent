package com.czareg.controller;

import com.czareg.context.Context;
import com.czareg.scene.fxml.FxmlScene;
import com.czareg.stage.ContextAware;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JoinController implements ContextAware {
    private static final Logger LOG = LogManager.getLogger(JoinController.class);
    private Context context;

    @FXML
    private TextField nameTextField;


    @FXML
    private void handleJoinSessionButtonClicked(ActionEvent event) {
        context.setName(nameTextField.getText());
        context.getSceneManager().setScene(FxmlScene.VOTE);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
