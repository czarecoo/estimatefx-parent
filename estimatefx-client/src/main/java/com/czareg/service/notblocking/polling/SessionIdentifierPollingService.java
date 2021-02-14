package com.czareg.service.notblocking.polling;

import com.czareg.context.Context;
import com.czareg.context.PropertiesManager;
import com.czareg.service.shared.OkHttpClientFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.sse.RealEventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionIdentifierPollingService extends PollingService {
    private static final Logger LOG = LogManager.getLogger(SessionIdentifierPollingService.class);
    private final OkHttpClient client;
    private final RealEventSource realEventSource;

    public SessionIdentifierPollingService(Context context, EventSourceListener eventSourceListener) {
        super(context);
        Request request = new Request.Builder()
                .url("http://localhost/pollSessionIdentifiers")
                .build();
        realEventSource = new RealEventSource(request, eventSourceListener);
        PropertiesManager propertiesManager = context.getPropertiesManager();
        OkHttpClientFactory okHttpClientFactory = new OkHttpClientFactory(propertiesManager);
        client = okHttpClientFactory.createClientForNotBlockingService();
        realEventSource.connect(client);
    }

    @Override
    public void doClose() {
        LOG.info("Closing");
        realEventSource.cancel();
        client.dispatcher().executorService().shutdown();
    }
}