package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.context.VoteContext;
import com.czareg.dto.SessionDTO;
import com.czareg.dto.UserDTO;
import com.czareg.model.Vote;
import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceException;
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
    private Label votingStatusLabel;
    private TableView<Vote> voteTableView;
    private List<Vote> votes;

    public GetSessionTask(Context context, BackendService backendService, VoteContext voteContext) {
        this.context = context;
        this.backendService = backendService;
        this.startButton = voteContext.getStartButton();
        this.stopButton = voteContext.getStopButton();
        this.buttonsHBox = voteContext.getButtonsHBox();
        this.votingStatusLabel = voteContext.getVotingStatusLabel();
        this.voteTableView = voteContext.getVoteTableView();
    }

    @Override
    protected Void call() throws BackendServiceException {
        try {
            int sessionId = context.getSessionId();
            sessionDTO = backendService.getSession(sessionId);
            return null;
        } catch (BackendServiceException e) {
            LOG.error(e);
            throw e;
        }
    }

    @Override
    protected void succeeded() {
        String userName = context.getUserName();

        updateControlsState(isCreator(userName));

        updateVoteTable();
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
                votingStatusLabel.setText("Voting in progress");
                break;
            case WAITING:
                startButton.setDisable(!isCreator);
                stopButton.setDisable(true);
                buttonsHBox.setDisable(true);
                votingStatusLabel.setText("Waiting for voting to start");
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

    private void updateVoteTable() {
        votes = createVoteList();

        if (listsNotEqual()) {
            LOG.info("Vote table list changed.");
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

    private boolean listsNotEqual() {
        return !votes.equals(voteTableView.getItems());
    }
}