package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.context.VoteContext;
import com.czareg.dto.SessionDTO;
import com.czareg.dto.UserDTO;
import com.czareg.dto.UserTypeDTO;
import com.czareg.model.Vote;
import com.czareg.notifications.EstimateFxNotification;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceException;
import com.czareg.tasks.exception.TaskException;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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

public class GetSessionTask extends Task<Void> {
    private static final Logger LOG = LogManager.getLogger(GetSessionTask.class);
    private Context context;
    private BackendService backendService;
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

    public GetSessionTask(Context context, BackendService backendService, VoteContext voteContext) {
        this.context = context;
        this.backendService = backendService;
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
    protected Void call() throws BackendServiceException, TaskException {
        try {
            int sessionId = context.getSessionId();
            sessionDTO = backendService.getSession(sessionId);
            String userName = context.getUserName();
            if (userName == null) {
                LOG.info("Username stored in context is null");
                return null;
            }
            boolean hasUser = hasUser(userName, sessionDTO);
            if (!hasUser) {
                throw new TaskException("User is no longer in session");
            }
            LOG.info("Received sessionId: {} info, from backend", sessionId);
            return null;
        } catch (BackendServiceException | TaskException e) {
            LOG.error(e);
            throw e;
        }
    }

    private boolean hasUser(String userName, SessionDTO sessionDTO) {
        return sessionDTO.getUsers().stream()
                .anyMatch((userDTO -> userDTO.getUserName().equals(userName)));
    }

    @Override
    protected void failed() {
        EstimateFxNotification.showErrorNotification("Failed to get current session information from backend.");
    }

    @Override
    protected void succeeded() {
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
                updateVoteTableInVotingState();
                break;
            case WAITING:
                startButton.setDisable(!isCreator);
                stopButton.setDisable(true);
                buttonsHBox.setDisable(true);
                double average = getLastSessionVoteAverage();
                String waitingStatus = String.format("Voting stopped. Vote average: %.2f", average);
                votingStatusLabel.setText(waitingStatus);
                voteTableView.getColumns().forEach((column) -> column.setSortable(true));
                updateVoteTableInWaitingState();
                break;
            case CLOSED:
                startButton.setDisable(true);
                stopButton.setDisable(true);
                buttonsHBox.setDisable(true);
                votingStatusLabel.setText("Session is closed");
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

    private void updateVoteTableInVotingState() {
        votes = createVoteList();

        if (!votes.equals(voteTableView.getItems())) {
            LOG.info("Vote table list changed. Updating");
            voteTableView.getItems().clear();
            voteTableView.getItems().addAll(votes);
        }
    }

    private void updateVoteTableInWaitingState() {
        votes = createVoteList();

        if (listsDoNotContainThemselves()) {
            LOG.info("Vote table list changed. Updating");
            voteTableView.getItems().clear();
            voteTableView.getItems().addAll(votes);
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

    private boolean listsDoNotContainThemselves() {
        return !listsContainThemselves();
    }

    private boolean listsContainThemselves() {
        ObservableList<Vote> itemsInTableView = voteTableView.getItems();
        return votes.containsAll(itemsInTableView) && itemsInTableView.containsAll(votes);
    }
}