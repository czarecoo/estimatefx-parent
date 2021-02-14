package com.czareg.service.notblocking.polling;

import com.czareg.context.Context;
import okhttp3.OkHttpClient;

import java.time.Duration;

public abstract class PollingService {
    private Context context;

    public PollingService(Context context) {
        this.context = context;
        context.add(this);
    }

    public final void close() {
        doClose();
        context.remove(this);
    }

    protected abstract void doClose();

    protected OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .readTimeout(Duration.ZERO)
                .retryOnConnectionFailure(true)
                .build();
    }
}