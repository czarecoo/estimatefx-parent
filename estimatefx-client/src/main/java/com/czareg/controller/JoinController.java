package com.czareg.controller;

import com.czareg.context.Context;
import com.czareg.controller.bindings.SessionChoiceBoxBooleanBinding;
import com.czareg.controller.bindings.UserNameTextFieldBooleanBinding;
import com.czareg.model.SessionIdentifier;
import com.czareg.scheduled.FillSessionsChoiceBoxScheduledService;
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
    private FillSessionsChoiceBoxScheduledService fillSessionsChoiceBoxScheduledService;
    private UserNameTextFieldBooleanBinding userNameTextFieldBooleanBinding;

    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<SessionIdentifier> existingSessionsChoiceBox;
    @FXML
    private Button joinSessionButton;
    private SessionChoiceBoxBooleanBinding sessionChoiceBoxBooleanBinding;

    @Override
    public void initialize(Context context) {
        this.context = context;
        userNameTextFieldBooleanBinding = new UserNameTextFieldBooleanBinding(nameTextField);
        sessionChoiceBoxBooleanBinding = new SessionChoiceBoxBooleanBinding(existingSessionsChoiceBox);
        joinSessionButton.disableProperty().bind(userNameTextFieldBooleanBinding.or(sessionChoiceBoxBooleanBinding));
        fillSessionsChoiceBoxScheduledService = new FillSessionsChoiceBoxScheduledService(context, existingSessionsChoiceBox);
        fillSessionsChoiceBoxScheduledService.start();
    }

    @FXML
    private void handleCreateSessionButtonClicked() {
        fillSessionsChoiceBoxScheduledService.cancel();
        context.getSceneManager().setScene(CREATE);
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

        new Thread(context.getTaskFactory().createJoinSessionTask(fillSessionsChoiceBoxScheduledService)).start();
    }
}
