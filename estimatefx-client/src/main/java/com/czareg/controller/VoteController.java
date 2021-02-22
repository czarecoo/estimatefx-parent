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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        voteContext = new VoteContext(startButton, stopButton, buttonsHBox, userStatusLabel, sessionStatusLabel,
                usersStatusLabel, votingStatusLabel, voteTableView);
        context.setVoteContext(voteContext);
        SessionListener sessionListener = new SessionListener(context);
        sessionPollingService = new SessionPollingService(context, sessionListener);
    }

    @FXML
    private void handleLeaveButtonClicked() {
        sessionPollingService.close();
        new Thread(context.getTaskFactory().createLeaveSessionTask()).start();
        context.getSceneManager().setScene(JOIN);
        LOG.info("Leave session button clicked");
    }

    @FXML
    private void handleVoteButtonClicked(ActionEvent event) {
        if (!(event.getSource() instanceof Button)) {
            throw new IllegalStateException("Handle was assigned to wrong UI element");
        }
        String voteValue = ((Button) event.getSource()).getText();

        new Thread(context.getTaskFactory().createVoteOnSessionTask(voteValue)).start();
        LOG.info("Vote {} button clicked");
    }

    @FXML
    private void handleStartButtonClicked() {
        new Thread(context.getTaskFactory().createStartVotingOnSessionTask()).start();
        LOG.info("Start voting button clicked");
    }

    @FXML
    private void handleStopButtonClicked() {
        new Thread(context.getTaskFactory().createStopVotingOnSessionTask()).start();
        LOG.info("Stop voting button clicked");
    }
}