package com.czareg.tasks;

import com.czareg.context.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolManager implements AutoCloseable {
    private static final Logger LOG = LogManager.getLogger(ThreadPoolManager.class);
    private final ExecutorService threadPool;

    public ThreadPoolManager(PropertiesManager propertiesManager) {
        int threadCount = propertiesManager.getThreadCount();
        threadPool = Executors.newFixedThreadPool(threadCount);
    }

    public Future<?> submit(Runnable task) {
        return threadPool.submit(task);
    }

    @Override
    public void close() {
        LOG.info("Closing thread pool");
        threadPool.shutdownNow();
    }
}