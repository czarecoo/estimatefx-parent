package com.czareg.controller;

import com.czareg.context.Context;
import com.czareg.context.VoteContext;
import com.czareg.model.Vote;
import com.czareg.model.VoteValue;
import com.czareg.scheduled.GetSessionScheduledService;
import com.czareg.stage.ContextAware;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoteController implements ContextAware {
    private static final Logger LOG = LogManager.getLogger(VoteController.class);
    private Context context;
    private GetSessionScheduledService getSessionScheduledService;

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
    public void initialize() {
        startButton.setDisable(true);
        stopButton.setDisable(true);
        buttonsHBox.setDisable(true);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        voteColumn.setCellValueFactory(new PropertyValueFactory<>("voteValue"));
    }

    @Override
    public void initialize(Context context) {
        this.context = context;
        VoteContext voteContext = new VoteContext(startButton, stopButton, buttonsHBox, userStatusLabel, sessionStatusLabel,
                usersStatusLabel, votingStatusLabel, voteTableView);
        getSessionScheduledService = new GetSessionScheduledService(context, voteContext);
        getSessionScheduledService.start();
    }

    @FXML
    private void handleVoteButtonClicked(ActionEvent event) {
        if (!(event.getSource() instanceof Button)) {
            throw new IllegalStateException("Handle was assigned to wrong UI element");
        }
        String voteValue = ((Button) event.getSource()).getText();

        Platform.runLater(context.getTaskFactory().createVoteOnSessionTask(voteValue));
        LOG.info("Voted {}", voteValue);
    }

    @FXML
    private void handleStartButtonClicked(ActionEvent event) {
        Platform.runLater(context.getTaskFactory().createStartVotingOnSessionTask());
        LOG.info("Started voting");
    }

    @FXML
    private void handleStopButtonClicked(ActionEvent event) {
        Platform.runLater(context.getTaskFactory().createStopVotingOnSessionTask());
        LOG.info("Stopped voting");
    }
}