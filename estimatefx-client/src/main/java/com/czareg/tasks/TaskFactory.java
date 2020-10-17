package com.czareg.tasks;

import com.czareg.context.Context;
import com.czareg.model.Session;
import com.czareg.service.BackendService;
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

    public Runnable createJoinSessionTask() {
        return new JoinSessionTask(context, backendService);
    }

    public Runnable createSessionsFillChoiceBoxTask(ChoiceBox<Session> sessionChoiceBox) {
        return new FillSessionsChoiceBoxTask(sessionChoiceBox, backendService);
    }

    public Runnable createLeaveSessionTask() {
        return new LeaveSessionTask(context, backendService);
    }
}