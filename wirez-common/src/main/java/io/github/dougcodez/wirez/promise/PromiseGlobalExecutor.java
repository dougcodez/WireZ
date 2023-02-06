package io.github.dougcodez.wirez.promise;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@UtilityClass
public class PromiseGlobalExecutor {

    private static final ExecutorService globalExecutor = Executors.newFixedThreadPool(2);

    public ExecutorService getGlobalExecutor() {
        return globalExecutor;
    }


    public void close() {
        if (globalExecutor.isTerminated()) return;
        globalExecutor.shutdownNow();
    }
}
