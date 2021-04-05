package com.czareg.service.notblocking.listener;

import com.czareg.context.Context;
import com.czareg.context.VoteContext;
import com.czareg.dto.SessionDTO;
import com.czareg.model.Vote;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class InactiveVoteViewUpdater {
    private static final Logger LOG = LogManager.getLogger(InactiveVoteViewUpdater.class);

    private SessionDTO sessionDTO;
    private String userName;
    private boolean isCreator;
    private Button startButton;
    private Button stopButton;
    private HBox buttonsHBox;
    private Label userStatusLabel;
    private Label sessionStatusLabel;
    private Label usersStatusLabel;
    private Label votingStatusLabel;
    private TableView<Vote> voteTableView;
    private List<Vote> votes;
    private Button stealCreatorButton;
    private MenuItem kickUserButton;
    private MenuItem passCreatorButton;

    public InactiveVoteViewUpdater(Context context, SessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;
        userName = context.getUserName();
        VoteContext voteContext = context.getVoteContext();
        startButton = voteContext.getStartButton();
        stopButton = voteContext.getStopButton();
        buttonsHBox = voteContext.getButtonsHBox();
        userStatusLabel = voteContext.getUserStatusLabel();
        sessionStatusLabel = voteContext.getSessionStatusLabel();
        usersStatusLabel = voteContext.getUsersStatusLabel();
        votingStatusLabel = voteContext.getVotingStatusLabel();
        voteTableView = voteContext.getVoteTableView();
        stealCreatorButton = voteContext.getStealCreatorButton();
        kickUserButton = voteContext.getKickUserButton();
        passCreatorButton = voteContext.getPassCreatorButton();
    }

    public void update() {
        startButton.setDisable(true);
        stopButton.setDisable(true);
        buttonsHBox.setDisable(true);
        stealCreatorButton.setDisable(true);
        passCreatorButton.setDisable(true);
        kickUserButton.setDisable(true);

        String userStatus = String.format("Name: %s (%s)", userName, "inactive");
        userStatusLabel.setText(userStatus);
        String sessionStatus = String.format("Description: %s", "no active session");
        sessionStatusLabel.setText(sessionStatus);
        usersStatusLabel.setText("Active users: unknown");
        votingStatusLabel.setText("You are no longer in session");
        voteTableView.getItems().clear();
    }
}