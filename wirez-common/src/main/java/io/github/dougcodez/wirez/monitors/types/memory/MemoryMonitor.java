package io.github.dougcodez.wirez.monitors.types.memory;

import io.github.dougcodez.wirez.monitors.api.MonitorWrapper;
import java.lang.management.MemoryMXBean;

public class MemoryMonitor implements MonitorWrapper<Double[]> {

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
        MemoryMXBean memoryMXBean = initMemoryXBean();
        final int mb = 1024 * 1024;
        return new Double[]{
                ((double) memoryMXBean.getHeapMemoryUsage().getMax() / mb),
                ((double) memoryMXBean.getHeapMemoryUsage().getUsed() / mb),
                ((double) (memoryMXBean.getHeapMemoryUsage().getMax() - memoryMXBean.getHeapMemoryUsage().getUsed()) / mb)
        };
    }
}
