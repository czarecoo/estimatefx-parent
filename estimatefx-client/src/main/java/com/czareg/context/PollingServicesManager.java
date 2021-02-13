package com.czareg.context;

import com.czareg.service.notblocking.PollingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PollingServicesManager {
    private static final Logger LOG = LogManager.getLogger(PollingServicesManager.class);
    private List<PollingService> pollingServiceList = new LinkedList<>();

    public void add(PollingService pollingService) {
        pollingServiceList.add(pollingService);
    }

    public void remove(PollingService pollingService) {
        pollingServiceList.remove(pollingService);
    }

    public void close() {
        LOG.info("Closing {} polling services", pollingServiceList.size());
        Iterator<PollingService> iterator = pollingServiceList.iterator();
        while (iterator.hasNext()) {
            iterator.next().close();
        }
    }
}