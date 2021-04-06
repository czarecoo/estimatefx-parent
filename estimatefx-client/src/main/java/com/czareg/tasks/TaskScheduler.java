package com.czareg.tasks;

import com.czareg.model.SessionIdentifier;
import javafx.scene.control.ChoiceBox;

import java.util.concurrent.Future;

public class TaskScheduler {
    private TaskFactory taskFactory;
    private ThreadPoolManager threadPoolManager;

    public TaskScheduler(TaskFactory taskFactory, ThreadPoolManager threadPoolManager) {
        this.taskFactory = taskFactory;
        this.threadPoolManager = threadPoolManager;
    }

    public Future<?> scheduleCreateSessionTask() {
        Runnable createSessionTask = taskFactory.createCreateSessionTask();
        return threadPoolManager.submit(createSessionTask);
    }

    public Future<?> scheduleJoinSessionTask() {
        Runnable joinSessionTask = taskFactory.createJoinSessionTask();
        return threadPoolManager.submit(joinSessionTask);
    }

    public Future<?> scheduleFillSessionsChoiceBoxTask(ChoiceBox<SessionIdentifier> sessionChoiceBox) {
        Runnable fillSessionsChoiceBoxTask = taskFactory.createFillSessionsChoiceBoxTask(sessionChoiceBox);
        return threadPoolManager.submit(fillSessionsChoiceBoxTask);
    }

    public Future<?> scheduleLeaveSessionTask() {
        Runnable leaveSessionTask = taskFactory.createLeaveSessionTask();
        return threadPoolManager.submit(leaveSessionTask);
    }

    public Future<?> scheduleStartVotingOnSessionTask() {
        Runnable startVotingOnSessionTask = taskFactory.createStartVotingOnSessionTask();
        return threadPoolManager.submit(startVotingOnSessionTask);
    }

    public Future<?> scheduleStopVotingOnSessionTask() {
        Runnable stopVotingOnSessionTask = taskFactory.createStopVotingOnSessionTask();
        return threadPoolManager.submit(stopVotingOnSessionTask);
    }

    public Future<?> scheduleVoteOnSessionTask(String voteValue) {
        Runnable voteOnSessionTask = taskFactory.createVoteOnSessionTask(voteValue);
        return threadPoolManager.submit(voteOnSessionTask);
    }

    public Future<?> scheduleKickUserTask(String userToKick) {
        Runnable kickUserTask = taskFactory.createKickUserTask(userToKick);
        return threadPoolManager.submit(kickUserTask);
    }

    public Future<?> schedulePassCreatorTask(String newCreator) {
        Runnable passCreatorTask = taskFactory.createPassCreatorTask(newCreator);
        return threadPoolManager.submit(passCreatorTask);
    }

    public Future<?> scheduleStealCreatorTask() {
        Runnable stealCreatorTask = taskFactory.createStealCreatorTask();
        return threadPoolManager.submit(stealCreatorTask);
    }
}