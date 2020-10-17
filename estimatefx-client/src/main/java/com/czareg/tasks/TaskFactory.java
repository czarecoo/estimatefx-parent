package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.context.VoteContext;
import com.czareg.model.SessionIdentifier;
import com.czareg.scheduled.FillSessionsChoiceBoxScheduledService;
import com.czareg.service.BackendService;
import javafx.concurrent.Task;
import javafx.scene.control.ChoiceBox;

public class TaskFactory {
    private BackendService backendService;
    private Context context;

    public TaskFactory(BackendService backendService, Context context) {
        this.backendService = backendService;
        this.context = context;
    }

    public Runnable createCreateSessionTask() {
        return new CreateSessionTask(context, backendService);
    }

    public JoinSessionTask createJoinSessionTask(FillSessionsChoiceBoxScheduledService fillSessionsChoiceBoxScheduledService) {
        return new JoinSessionTask(context, backendService, fillSessionsChoiceBoxScheduledService);
    }

    public Task<Void> createSessionsFillChoiceBoxTask(ChoiceBox<SessionIdentifier> sessionChoiceBox) {
        return new FillSessionsChoiceBoxTask(sessionChoiceBox, backendService);
    }

    public Runnable createLeaveSessionTask() {
        return new LeaveSessionTask(context, backendService);
    }

    public Runnable createStartVotingOnSessionTask() {
        return new StartVotingOnSessionTask(context, backendService);
    }

    public Runnable createStopVotingOnSessionTask() {
        return new StopVotingOnSessionTask(context, backendService);
    }

    public Task<Void> createGetSessionTask(VoteContext voteContext) {
        return new GetSessionTask(context, backendService, voteContext);
    }

    public Runnable createVoteOnSessionTask(String voteValue) {
        return new VoteOnSessionTask(context, backendService, voteValue);
    }
}