package io.github.dougcodez.wirez.monitors.types.cpu;

import com.sun.management.OperatingSystemMXBean;
import io.github.dougcodez.wirez.monitors.api.MonitorWrapper;

public class ProcessCPUMonitor implements MonitorWrapper<Double> {

    @Override
    public boolean isCPU() {
        return true;
    }

    @Override
    public boolean isThread() {
        return false;
    }

    @Override
    public boolean isMemory() {
        return false;
    }

    @Override
    public Double getElement() {
        OperatingSystemMXBean systemMXBean = initOperatingSystemMXBean();
        return systemMXBean.getProcessCpuLoad() * 100;
    }
}