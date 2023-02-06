package io.github.dougcodez.wirez.monitors.task;

import lombok.experimental.UtilityClass;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class SystemsThreadExecutor {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setName("wirez-systems-monitor");
        thread.setDaemon(true);
        return thread;
    });

    public void call() {
        executor.scheduleAtFixedRate(new SystemsTask(), 1, 1, TimeUnit.SECONDS);
    }

    public  void close() {
        if (executor.isTerminated()) {
            return;
        }

        executor.shutdownNow();
    }
}
