package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.model.SessionIdentifier;
import com.czareg.service.blocking.BackendBlockingService;
import javafx.scene.control.ChoiceBox;

public class TaskFactory {
    private BackendBlockingService backendBlockingService;
    private Context context;

    public TaskFactory(BackendBlockingService backendBlockingService, Context context) {
        this.backendBlockingService = backendBlockingService;
        this.context = context;
    }

    public Runnable createCreateSessionTask() {
        return new CreateSessionTask(context, backendBlockingService);
    }

    public Runnable createJoinSessionTask() {
        return new JoinSessionTask(context, backendBlockingService);
    }

    public Runnable createFillSessionsChoiceBoxTask(ChoiceBox<SessionIdentifier> sessionChoiceBox) {
        return new FillSessionsChoiceBoxTask(sessionChoiceBox, backendBlockingService);
    }

    public Runnable createLeaveSessionTask() {
        return new LeaveSessionTask(context, backendBlockingService);
    }

    public Runnable createStartVotingOnSessionTask() {
        return new StartVotingOnSessionTask(context, backendBlockingService);
    }

    public Runnable createStopVotingOnSessionTask() {
        return new StopVotingOnSessionTask(context, backendBlockingService);
    }

    public Runnable createVoteOnSessionTask(String voteValue) {
        return new VoteOnSessionTask(context, backendBlockingService, voteValue);
    }

    public Runnable createKickUserTask(String userToKick) {
        return new KickUserTask(context, backendBlockingService, userToKick);
    }

    public Runnable createPassCreatorTask(String newCreator) {
        return new PassCreatorTask(context, backendBlockingService, newCreator);
    }

    public Runnable createStealCreatorTask() {
        return new StealCreatorTask(context, backendBlockingService);
    }
}