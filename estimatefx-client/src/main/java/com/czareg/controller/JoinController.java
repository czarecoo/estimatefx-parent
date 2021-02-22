package com.czareg.controller;

import com.czareg.context.Context;
import com.czareg.controller.bindings.SessionChoiceBoxBooleanBinding;
import com.czareg.controller.bindings.UserNameTextFieldBooleanBinding;
import com.czareg.model.SessionIdentifier;
import com.czareg.service.notblocking.listener.SessionIdentifiersListener;
import com.czareg.service.notblocking.polling.SessionIdentifierPollingService;
import com.czareg.stage.ContextAware;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.CREATE;

public class JoinController implements ContextAware {
    private static final Logger LOG = LogManager.getLogger(JoinController.class);
    private Context context;
    private UserNameTextFieldBooleanBinding userNameTextFieldBooleanBinding;

    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<SessionIdentifier> existingSessionsChoiceBox;
    @FXML
    private Button joinSessionButton;
    private SessionChoiceBoxBooleanBinding sessionChoiceBoxBooleanBinding;

    private SessionIdentifierPollingService sessionIdentifierPollingService;

    @Override
    public void initialize(Context context) {
        this.context = context;
        nameTextField.setText(context.getUserName());
        nameTextField.textProperty().addListener(((observable, oldValue, newValue) -> context.setUserName(newValue)));
        userNameTextFieldBooleanBinding = new UserNameTextFieldBooleanBinding(nameTextField);
        sessionChoiceBoxBooleanBinding = new SessionChoiceBoxBooleanBinding(existingSessionsChoiceBox);
        joinSessionButton.disableProperty().bind(userNameTextFieldBooleanBinding.or(sessionChoiceBoxBooleanBinding));

        SessionIdentifiersListener sessionIdentifiersListener = new SessionIdentifiersListener(existingSessionsChoiceBox);
        sessionIdentifierPollingService = new SessionIdentifierPollingService(context, sessionIdentifiersListener);
    }

    @FXML
    private void handleCreateSessionButtonClicked() {
        sessionIdentifierPollingService.close();
        context.getSceneManager().setScene(CREATE);
        LOG.info("Switch to create session button clicked");
    }

    @FXML
    private void handleJoinSessionButtonClicked() {
        String userName = nameTextField.getText();

        SessionIdentifier selectedItem = existingSessionsChoiceBox.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            LOG.info("Cannot join session because no session is selected");
            return;
        }
        context.setUserName(userName);
        context.setSessionId(selectedItem.getSessionId());

        new Thread(context.getTaskFactory().createJoinSessionTask(sessionIdentifierPollingService)).start();
        LOG.info("Join session button clicked");
    }
}