package com.czareg.service.notblocking.listener;

import com.czareg.context.Context;
import com.czareg.context.VoteContext;
import com.czareg.dto.SessionDTO;
import com.czareg.dto.UserDTO;
import com.czareg.dto.UserType;
import com.czareg.dto.VoteDTO;
import com.czareg.model.Vote;
import com.czareg.model.VoteValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.czareg.dto.UserType.CREATOR;

public class ActiveVoteViewUpdater {
    private static final Logger LOG = LogManager.getLogger(ActiveVoteViewUpdater.class);

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

    public ActiveVoteViewUpdater(Context context, SessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;
        userName = context.getUserName();
        isCreator = isCreator();
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
        updateUserStatusLabel();

        updateSessionStatusLabel();

        updateUsersStatusLabel();

        updateControlsState();
    }

    private void updateUserStatusLabel() {
        String userType = findUserType(userName);
        String userStatus = String.format("Name: %s (%s)", userName, userType);
        userStatusLabel.setText(userStatus);
    }

    private String findUserType(String userName) {
        return sessionDTO.getUserDTOs().stream()
                .filter(userDTO -> userDTO.getUserName().equals(userName))
                .findFirst()
                .map(UserDTO::getUserType)
                .map(UserType::toString)
                .map(String::toLowerCase)
                .orElse("unknown");
    }

    private void updateSessionStatusLabel() {
        String sessionDescription = sessionDTO.getDescription();
        String sessionStatus = String.format("Description: %s", sessionDescription);
        sessionStatusLabel.setText(sessionStatus);
    }

    private void updateUsersStatusLabel() {
        int usersCount = sessionDTO.getUserDTOs().size();
        String sessionStatus = String.format("Active users: %d", usersCount);
        usersStatusLabel.setText(sessionStatus);
    }

    private boolean isCreator() {
        for (UserDTO user : sessionDTO.getUserDTOs()) {
            if (user.getUserName().equals(userName)) {
                return user.getUserType().equals(CREATOR);
            }
        }
        return false;
    }

    private void updateControlsState() {
        updateStatelessControls();
        updateControlsDependingOnState();
    }

    private void updateStatelessControls() {
        if (sessionDTO.isAllowStealingCreator()) {
            stealCreatorButton.setDisable(isCreator);
        }
        passCreatorButton.setDisable(!isCreator);
        kickUserButton.setDisable(!isCreator);
    }

    private void updateControlsDependingOnState() {
        switch (sessionDTO.getState()) {
            case VOTING:
                startButton.setDisable(true);
                stopButton.setDisable(!isCreator);
                buttonsHBox.setDisable(false);
                votingStatusLabel.setText("Voting in progress.");
                voteTableView.getColumns().forEach((column) -> column.setSortable(false));
                updateVoteTableForceOrder();
                break;
            case WAITING:
                startButton.setDisable(!isCreator);
                stopButton.setDisable(true);
                buttonsHBox.setDisable(true);
                double average = getLastSessionVoteAverage();
                String waitingStatus = String.format("Voting stopped. Vote average: %.2f", average);
                votingStatusLabel.setText(waitingStatus);
                voteTableView.getColumns().forEach((column) -> column.setSortable(true));
                updateVoteTableDoNotForceOrder();
                break;
            default:
                throw new IllegalArgumentException("Unknown state");
        }
    }

    private double getLastSessionVoteAverage() {
        return sessionDTO.getVoteValues().stream()
                .filter(vote -> vote.matches("\\d+"))
                .mapToInt(Integer::valueOf)
                .average()
                .orElse(0);
    }

    private void updateVoteTableForceOrder() {
        votes = createVoteList();

        if (!votes.equals(voteTableView.getItems())) {
            clearAndRepopulateVoteTable();
        }
    }

    private void updateVoteTableDoNotForceOrder() {
        votes = createVoteList();

        if (listsDoNotContainThemselves()) {
            clearAndRepopulateVoteTable();
        }
    }

    private List<Vote> createVoteList() {
        List<Vote> votes = new ArrayList<>();
        for (VoteDTO voteDTO : sessionDTO.getVoteDTOs()) {
            UserDTO userDTO = voteDTO.getUserDTO();
            VoteValue voteValue = new VoteValue(voteDTO.getVoteValue());
            Vote vote = new Vote(userDTO.getUserName(), userDTO.getUserType(), voteValue);
            votes.add(vote);
        }
        return votes;
    }

    private void clearAndRepopulateVoteTable() {
        LOG.info("Vote table list changed. Updating");
        voteTableView.getItems().clear();
        voteTableView.getItems().addAll(votes);
    }

    private boolean listsDoNotContainThemselves() {
        return !listsContainThemselves();
    }

    private boolean listsContainThemselves() {
        ObservableList<Vote> itemsInTableView = voteTableView.getItems();
        return votes.containsAll(itemsInTableView) && itemsInTableView.containsAll(votes);
    }
}