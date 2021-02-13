package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.notblocking.PollingService;

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

    public Runnable createJoinSessionTask(PollingService pollingService) {
        return new JoinSessionTask(context, backendBlockingService, pollingService);
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
}