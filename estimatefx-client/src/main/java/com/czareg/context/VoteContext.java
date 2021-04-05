package com.czareg.context;

import com.czareg.model.Vote;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class VoteContext {
    private Button startButton;
    private Button stopButton;
    private HBox buttonsHBox;
    private Label userStatusLabel;
    private Label sessionStatusLabel;
    private Label votingStatusLabel;
    private Label usersStatusLabel;
    private TableView<Vote> voteTableView;
    private Button stealCreatorButton;
    private MenuItem passCreatorButton;
    private MenuItem kickUserButton;

    public VoteContext(Button startButton, Button stopButton, HBox buttonsHBox, Label userStatusLabel,
                       Label sessionStatusLabel, Label usersStatusLabel, Label votingStatusLabel, TableView<Vote> voteTableView,
                       Button stealCreatorButton, MenuItem passCreatorButton, MenuItem kickUserButton) {
        this.startButton = startButton;
        this.stopButton = stopButton;
        this.buttonsHBox = buttonsHBox;
        this.userStatusLabel = userStatusLabel;
        this.sessionStatusLabel = sessionStatusLabel;
        this.votingStatusLabel = votingStatusLabel;
        this.usersStatusLabel = usersStatusLabel;
        this.voteTableView = voteTableView;
        this.stealCreatorButton = stealCreatorButton;
        this.passCreatorButton = passCreatorButton;
        this.kickUserButton = kickUserButton;
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

    public Label getUserStatusLabel() {
        return userStatusLabel;
    }

    public Label getSessionStatusLabel() {
        return sessionStatusLabel;
    }

    public Label getUsersStatusLabel() {
        return usersStatusLabel;
    }

    public Button getStealCreatorButton() {
        return stealCreatorButton;
    }

    public MenuItem getPassCreatorButton() {
        return passCreatorButton;
    }

    public MenuItem getKickUserButton() {
        return kickUserButton;
    }
}