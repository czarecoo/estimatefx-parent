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

    public void scheduleCreateSessionTask() {
        Runnable createSessionTask = taskFactory.createCreateSessionTask();
        threadPoolManager.submit(createSessionTask);
    }

    public void scheduleJoinSessionTask() {
        Runnable joinSessionTask = taskFactory.createJoinSessionTask();
        threadPoolManager.submit(joinSessionTask);
    }

    public void scheduleFillSessionsChoiceBoxTask(ChoiceBox<SessionIdentifier> sessionChoiceBox) {
        Runnable fillSessionsChoiceBoxTask = taskFactory.createFillSessionsChoiceBoxTask(sessionChoiceBox);
        threadPoolManager.submit(fillSessionsChoiceBoxTask);
    }

    public Future<?> scheduleLeaveSessionTask() {
        Runnable leaveSessionTask = taskFactory.createLeaveSessionTask();
        return threadPoolManager.submit(leaveSessionTask);
    }

    public void scheduleStartVotingOnSessionTask() {
        Runnable startVotingOnSessionTask = taskFactory.createStartVotingOnSessionTask();
        threadPoolManager.submit(startVotingOnSessionTask);
    }

    public void scheduleStopVotingOnSessionTask() {
        Runnable stopVotingOnSessionTask = taskFactory.createStopVotingOnSessionTask();
        threadPoolManager.submit(stopVotingOnSessionTask);
    }

    public void scheduleVoteOnSessionTask(String voteValue) {
        Runnable voteOnSessionTask = taskFactory.createVoteOnSessionTask(voteValue);
        threadPoolManager.submit(voteOnSessionTask);
    }

    public void scheduleKickUserTask(String userToKick) {
        Runnable kickUserTask = taskFactory.createKickUserTask(userToKick);
        threadPoolManager.submit(kickUserTask);
    }

    public void schedulePassCreatorTask(String newCreator) {
        Runnable passCreatorTask = taskFactory.createPassCreatorTask(newCreator);
        threadPoolManager.submit(passCreatorTask);
    }

    public void scheduleStealCreatorTask() {
        Runnable stealCreatorTask = taskFactory.createStealCreatorTask();
        threadPoolManager.submit(stealCreatorTask);
    }
}