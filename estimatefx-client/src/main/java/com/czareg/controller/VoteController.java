package com.czareg.controller;

import com.czareg.context.Context;
import com.czareg.model.Vote;
import com.czareg.model.VoteValue;
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

public class VoteController implements ContextAware {
    private static final Logger LOG = LogManager.getLogger(VoteController.class);
    private Context context;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private HBox buttonsHBox;

    @FXML
    private Label votingStatusLabel;

    @FXML
    private TableView<Vote> voteTableView;

    @FXML
    private TableColumn<Vote, String> nameColumn;

    @FXML
    private TableColumn<Vote, VoteValue> voteColumn;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        voteColumn.setCellValueFactory(new PropertyValueFactory<>("voteValue"));

        startButton.setDisable(false);
        stopButton.setDisable(true);
        buttonsHBox.setDisable(true);
        votingStatusLabel.setText("Waiting for voting to start");
    }

    @FXML
    private void handleVoteButtonClicked(ActionEvent event) {
        if (!(event.getSource() instanceof Button)) {
            throw new IllegalStateException("Handle was assigned to wrong UI element");
        }
        String name = context.getName();
        String voteValue = ((Button) event.getSource()).getText();
        Vote vote = new Vote(name, voteValue);

        voteTableView.getItems().add(vote);
        LOG.info("Voted {}", vote);
    }

    @FXML
    private void handleStartButtonClicked(ActionEvent event) {
        voteTableView.getItems().clear();

        startButton.setDisable(true);
        stopButton.setDisable(false);
        buttonsHBox.setDisable(false);

        votingStatusLabel.setText("Voting in progress");

        LOG.info("Started voting");
    }

    @FXML
    private void handleStopButtonClicked(ActionEvent event) {
        startButton.setText("Clear and start");

        startButton.setDisable(false);
        stopButton.setDisable(true);
        buttonsHBox.setDisable(true);

        votingStatusLabel.setText("Waiting for voting to start");

        LOG.info("Stopped voting");
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}