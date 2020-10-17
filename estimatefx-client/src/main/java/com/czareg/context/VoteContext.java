package com.czareg.context;

import com.czareg.model.Vote;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class VoteContext {
    private final Button startButton;
    private final Button stopButton;
    private final HBox buttonsHBox;
    private final Label votingStatusLabel;
    private final TableView<Vote> voteTableView;

    public VoteContext(Button startButton, Button stopButton, HBox buttonsHBox, Label votingStatusLabel, TableView<Vote> voteTableView) {
        this.startButton = startButton;
        this.stopButton = stopButton;
        this.buttonsHBox = buttonsHBox;
        this.votingStatusLabel = votingStatusLabel;
        this.voteTableView = voteTableView;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public HBox getButtonsHBox() {
        return buttonsHBox;
    }

    public Label getVotingStatusLabel() {
        return votingStatusLabel;
    }

    public TableView<Vote> getVoteTableView() {
        return voteTableView;
    }
}