package com.czareg.scheduled;

import com.czareg.context.Context;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class GetSessionScheduledService extends ScheduledService<Void> {
    private Context context;

    public GetSessionScheduledService(Context context) {
        this.context = context;
        setPeriod(Duration.seconds(1));
        setBackoffStrategy(EXPONENTIAL_BACKOFF_STRATEGY);
        setMaximumCumulativePeriod(Duration.seconds(10));
    }

    @Override
    protected Task<Void> createTask() {
        return context.getTaskFactory().createGetSessionTask();
    }
}