package com.czareg.scheduled;

import com.czareg.context.Context;
import com.czareg.context.VoteContext;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class GetSessionScheduledService extends ScheduledService<Void> {
    private Context context;
    private VoteContext voteContext;

    public GetSessionScheduledService(Context context, VoteContext voteContext) {
        this.context = context;
        this.voteContext = voteContext;
        setPeriod(Duration.millis(500));
        setBackoffStrategy(EXPONENTIAL_BACKOFF_STRATEGY);
        setMaximumCumulativePeriod(Duration.seconds(5));
    }

    @Override
    protected Task<Void> createTask() {
        return context.getTaskFactory().createGetSessionTask(voteContext);
    }
}