package com.czareg.controller;

import com.czareg.context.Context;
import com.czareg.context.CreateContext;
import com.czareg.controller.bindings.UserNameTextFieldBooleanBinding;
import com.czareg.stage.ContextAware;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.JOIN;

public class CreateController implements ContextAware {
    private static final Logger LOG = LogManager.getLogger(CreateController.class);
    private Context context;
    private UserNameTextFieldBooleanBinding userNameTextFieldBooleanBinding;

    @FXML
    private TextField nameTextField;
    @FXML
    private Button createSessionButton;
    @FXML
    private CheckBox allowPassingCreatorCheckBox;
    @FXML
    private CheckBox allowStealingCreatorCheckBox;
    @FXML
    private CheckBox passCreatorWhenLeavingCheckBox;

    @Override
    public void initialize(Context context) {
        this.context = context;
        CreateContext createContext = new CreateContext(allowPassingCreatorCheckBox, allowStealingCreatorCheckBox,
                passCreatorWhenLeavingCheckBox);
        context.setCreateContext(createContext);
        nameTextField.setText(context.getUserName());
        nameTextField.textProperty().addListener(((observable, oldValue, newValue) -> context.setUserName(newValue)));
        userNameTextFieldBooleanBinding = new UserNameTextFieldBooleanBinding(nameTextField);
        createSessionButton.disableProperty().bind(userNameTextFieldBooleanBinding);
    }

    @FXML
    private void handleJoinSessionButtonClicked() {
        context.getSceneManager().setScene(JOIN);
        LOG.info("Switch to join session button clicked");
    }

    @FXML
    private void handleCreateSessionButtonClicked() {
        String userName = nameTextField.getText();
        context.setUserName(userName);
        new Thread(context.getTaskFactory().createCreateSessionTask()).start();
        LOG.info("Create session button clicked");
    }
}