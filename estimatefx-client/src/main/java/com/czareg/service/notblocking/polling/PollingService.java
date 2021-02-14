package com.czareg.service.notblocking.polling;

import com.czareg.context.Context;

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
}