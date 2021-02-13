package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.model.SessionIdentifier;
import com.czareg.scheduled.FillSessionsChoiceBoxScheduledService;
import com.czareg.service.blocking.BackendBlockingService;
import javafx.concurrent.Task;
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

    public Runnable createJoinSessionTask(FillSessionsChoiceBoxScheduledService fillSessionsChoiceBoxScheduledService) {
        return new JoinSessionTask(context, backendBlockingService, fillSessionsChoiceBoxScheduledService);
    }

    public Task<Void> createSessionsFillChoiceBoxTask(ChoiceBox<SessionIdentifier> sessionChoiceBox) {
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

    public Task<Void> createGetSessionTask() {
        return new GetSessionTask(context, backendBlockingService, context.getVoteContext());
    }

    public Runnable createVoteOnSessionTask(String voteValue) {
        return new VoteOnSessionTask(context, backendBlockingService, voteValue);
    }
}