package com.czareg.service.notblocking.listener;

import com.czareg.context.Context;
import com.czareg.context.VoteContext;
import com.czareg.dto.SessionDTO;
import com.czareg.dto.UserDTO;
import com.czareg.dto.UserTypeDTO;
import com.czareg.model.Vote;
import com.czareg.notifications.NotificationMessageBuilder;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.czareg.dto.UserTypeDTO.CREATOR;
import static com.czareg.notifications.EstimateFxNotification.showErrorNotificationFromCustomThread;

public class SessionListener extends Listener {
    private static final Logger LOG = LogManager.getLogger(SessionListener.class);

    private Gson gson;
    private Context context;
    private SessionDTO sessionDTO;

    private Button startButton;
    private Button stopButton;
    private HBox buttonsHBox;
    private Label userStatusLabel;
    private Label sessionStatusLabel;
    private Label usersStatusLabel;
    private Label votingStatusLabel;
    private TableView<Vote> voteTableView;
    private List<Vote> votes;

    public SessionListener(Context context) {
        super(LOG);
        gson = new Gson();
        this.context = context;
        VoteContext voteContext = context.getVoteContext();
        this.startButton = voteContext.getStartButton();
        this.stopButton = voteContext.getStopButton();
        this.buttonsHBox = voteContext.getButtonsHBox();
        this.userStatusLabel = voteContext.getUserStatusLabel();
        this.sessionStatusLabel = voteContext.getSessionStatusLabel();
        this.usersStatusLabel = voteContext.getUsersStatusLabel();
        this.votingStatusLabel = voteContext.getVotingStatusLabel();
        this.voteTableView = voteContext.getVoteTableView();
    }

    @Override
    protected void onEvent(String jsonObject) {
        String userName = context.getUserName();
        if (userName == null) {
            LOG.error("Username stored in context is null");
            return;
        }
        sessionDTO = gson.fromJson(jsonObject, SessionDTO.class);
        boolean hasUser = hasUser(userName, sessionDTO);
        if (!hasUser) {
            String developerMessage = "User is no longer in session";
            LOG.error(developerMessage);
            NotificationMessageBuilder notificationMessageBuilder = new NotificationMessageBuilder();
            notificationMessageBuilder.developerMessage(developerMessage);
            notificationMessageBuilder.remediation("Try leaving and rejoining session.");
            showErrorNotificationFromCustomThread(notificationMessageBuilder.build());
            return;
        }

        Platform.runLater(this::updateStuff);
    }

    @Override
    protected void onFailure(Throwable throwable) {
        String developerMessage = "Failed to get current session information from backend.";
        LOG.error(developerMessage, throwable);
        NotificationMessageBuilder notificationMessageBuilder = new NotificationMessageBuilder();
        notificationMessageBuilder.developerMessage(developerMessage);
        notificationMessageBuilder.exceptionMessage(throwable);
        notificationMessageBuilder.remediation("There is a high probability that session polling mechanism is broken now. \nIn that case leave the session and try to rejoin.");
        showErrorNotificationFromCustomThread(notificationMessageBuilder.build());
    }

    private boolean hasUser(String userName, SessionDTO sessionDTO) {
        return sessionDTO.getUsers().stream()
                .anyMatch((userDTO -> userDTO.getUserName().equals(userName)));
    }

    private void updateStuff() {
        String userName = context.getUserName();

        updateUserStatusLabel();

        updateSessionStatusLabel();

        updateUsersStatusLabel();

        updateControlsState(isCreator(userName));
    }

    private void updateUserStatusLabel() {
        String userName = context.getUserName();
        String userType = findUserType(userName);
        if (userType == null) {
            LOG.error("Failed to find userType for user {} in users {}", userName, sessionDTO.getUsers());
            userType = "Unknown";
        }
        String userStatus = String.format("Name: %s (%s)", userName, userType);
        userStatusLabel.setText(userStatus);
    }

    private String findUserType(String userName) {
        return sessionDTO.getUsers().stream()
                .filter(userDTO -> userDTO.getUserName().equals(userName))
                .findFirst()
                .map(UserDTO::getUserType)
                .map(UserTypeDTO::toString)
                .map(String::toLowerCase)
                .orElse(null);
    }

    private void updateSessionStatusLabel() {
        String sessionDescription = sessionDTO.getDescription();
        String sessionStatus = String.format("Description: %s", sessionDescription);
        sessionStatusLabel.setText(sessionStatus);
    }

    private void updateUsersStatusLabel() {
        int usersCount = sessionDTO.getUsers().size();
        String sessionStatus = String.format("Active users: %d", usersCount);
        usersStatusLabel.setText(sessionStatus);
    }

    private boolean isCreator(String userName) {
        for (UserDTO user : sessionDTO.getUsers()) {
            if (user.getUserName().equals(userName)) {
                return user.getUserType().equals(CREATOR);
            }
        }
        return false;
    }

    private void updateControlsState(boolean isCreator) {
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
        return sessionDTO.getVotes().values().stream()
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
        for (Map.Entry<String, String> entry : sessionDTO.getVotes().entrySet()) {
            Vote vote = new Vote(entry.getKey(), entry.getValue());
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