package com.czareg.service.notblocking.session;

import com.czareg.context.Context;
import com.czareg.service.notblocking.PollingService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.sse.RealEventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionPollingService extends PollingService {
    private static final Logger LOG = LogManager.getLogger(SessionPollingService.class);
    private final OkHttpClient client;
    private final RealEventSource realEventSource;

    public SessionPollingService(Context context, EventSourceListener eventSourceListener) {
        super(context);
        Request request = new Request.Builder()
                .url("http://localhost/pollSession/" + context.getSessionId())
                .build();
        realEventSource = new RealEventSource(request, eventSourceListener);
        client = createClient();
        realEventSource.connect(client);
    }

    @Override
    public void doClose() {
        LOG.info("Closing");
        realEventSource.cancel();
        client.dispatcher().executorService().shutdown();
    }
}