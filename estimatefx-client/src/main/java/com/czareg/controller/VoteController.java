package com.czareg.controller;

import com.czareg.context.Context;
import com.czareg.context.VoteContext;
import com.czareg.model.Vote;
import com.czareg.model.VoteValue;
import com.czareg.service.notblocking.listener.SessionListener;
import com.czareg.service.notblocking.polling.SessionPollingService;
import com.czareg.stage.ContextAware;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.JOIN;

public class VoteController implements ContextAware {
    private static final Logger LOG = LogManager.getLogger(VoteController.class);
    private Context context;
    private VoteContext voteContext;
    private SessionPollingService sessionPollingService;

    @FXML
    private Label userStatusLabel;
    @FXML
    private Label sessionStatusLabel;
    @FXML
    private Label usersStatusLabel;
    @FXML
    private Label votingStatusLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private HBox buttonsHBox;
    @FXML
    private TableView<Vote> voteTableView;
    @FXML
    private TableColumn<Vote, String> nameColumn;
    @FXML
    private TableColumn<Vote, VoteValue> voteColumn;
    @FXML
    private Button stealCreatorButton;
    @FXML
    private MenuItem passCreatorButton;
    @FXML
    private MenuItem kickUserButton;

    @FXML
    public void initialize() {
        startButton.setDisable(true);
        stopButton.setDisable(true);
        buttonsHBox.setDisable(true);
        stealCreatorButton.setDisable(true);
        passCreatorButton.setDisable(true);
        kickUserButton.setDisable(true);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        voteColumn.setCellValueFactory(new PropertyValueFactory<>("voteValue"));
    }

    @Override
    public void initialize(Context context) {
        this.context = context;
        voteContext = new VoteContext(startButton, stopButton, buttonsHBox, userStatusLabel, sessionStatusLabel,
                usersStatusLabel, votingStatusLabel, voteTableView, stealCreatorButton, passCreatorButton, kickUserButton);
        context.setVoteContext(voteContext);
        SessionListener sessionListener = new SessionListener(context);
        sessionPollingService = new SessionPollingService(context, sessionListener);
    }

    @FXML
    private void handleLeaveButtonClicked() {
        LOG.info("Leave session button clicked");
        sessionPollingService.close();
        context.getTaskScheduler().scheduleLeaveSessionTask();
        context.getSceneManager().setScene(JOIN);
    }

    @FXML
    private void handleVoteButtonClicked(ActionEvent event) {
        LOG.info("Vote button clicked");
        if (!(event.getSource() instanceof Button)) {
            LOG.warn("Handle was assigned to wrong UI element");
            return;
        }
        String voteValue = ((Button) event.getSource()).getText();
        LOG.info("Vote value {}", voteValue);
        context.getTaskScheduler().scheduleVoteOnSessionTask(voteValue);
    }

    @FXML
    private void handleStartButtonClicked() {
        LOG.info("Start voting button clicked");
        context.getTaskScheduler().scheduleStartVotingOnSessionTask();
    }

    @FXML
    private void handleStopButtonClicked() {
        LOG.info("Stop voting button clicked");
        context.getTaskScheduler().scheduleStopVotingOnSessionTask();
    }

    @FXML
    private void handleStealCreatorButtonClicked() {
        LOG.info("handleStealCreatorButtonClicked");
        context.getTaskScheduler().scheduleStealCreatorTask();
    }

    @FXML
    private void handlePassCreatorButtonClicked() {
        LOG.info("handlePassCreatorButtonClicked");

        Vote selectedItem = voteTableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            LOG.warn("no user selected");
            return;
        }
        context.getTaskScheduler().schedulePassCreatorTask(selectedItem.getName());
    }

    @FXML
    private void handleKickUserButtonClicked() {
        LOG.info("handleKickUserButtonClicked");
        Vote selectedItem = voteTableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            LOG.warn("no user selected");
            return;
        }
        context.getTaskScheduler().scheduleKickUserTask(selectedItem.getName());
    }
}