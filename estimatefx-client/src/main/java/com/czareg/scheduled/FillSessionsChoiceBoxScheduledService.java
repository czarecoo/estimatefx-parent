package com.czareg.scheduled;

import com.czareg.context.Context;
import com.czareg.model.SessionIdentifier;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.ChoiceBox;
import javafx.util.Duration;

public class FillSessionsChoiceBoxScheduledService extends ScheduledService<Void> {
    private Context context;
    private ChoiceBox<SessionIdentifier> existingSessionsChoiceBox;

    public FillSessionsChoiceBoxScheduledService(Context context, ChoiceBox<SessionIdentifier> existingSessionsChoiceBox) {
        this.context = context;
        this.existingSessionsChoiceBox = existingSessionsChoiceBox;
        setPeriod(Duration.seconds(1));
        setBackoffStrategy(EXPONENTIAL_BACKOFF_STRATEGY);
        setMaximumCumulativePeriod(Duration.seconds(10));
    }

    @Override
    protected Task<Void> createTask() {
        return context.getTaskFactory().createSessionsFillChoiceBoxTask(existingSessionsChoiceBox);
    }
}