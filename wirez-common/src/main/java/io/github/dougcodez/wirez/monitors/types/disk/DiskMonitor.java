package io.github.dougcodez.wirez.monitors.types.disk;

import io.github.dougcodez.wirez.monitors.api.MonitorWrapper;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DiskMonitor implements MonitorWrapper<Double[]> {

    private static FileStore fileStore = null;

    static {
        try {
            fileStore = Files.getFileStore(Paths.get("."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isThread() {
        return false;
    }

    @Override
    public boolean isCPU() {
        return false;
    }

    @Override
    public boolean isMemory() {
        return true;
    }

    @Override
    public Double[] getElement() {
        final int mb = 1024 * 1024;
        try {
            return new Double[]{
                    (double) fileStore.getTotalSpace() / mb,
                    (double) fileStore.getUsableSpace() / mb,
                    (double) (fileStore.getTotalSpace() - fileStore.getUsableSpace()) / mb};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
