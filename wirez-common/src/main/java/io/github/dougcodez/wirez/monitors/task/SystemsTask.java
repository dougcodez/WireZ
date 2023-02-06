package io.github.dougcodez.wirez.monitors.task;

import io.github.dougcodez.wirez.monitors.MonitorManager;
import io.github.dougcodez.wirez.monitors.types.cpu.ProcessCPUMonitor;
import io.github.dougcodez.wirez.monitors.types.cpu.SystemCPUMonitor;
import io.github.dougcodez.wirez.monitors.util.SystemConstants;
import io.github.dougcodez.wirez.monitors.util.SystemMovingAverage;

import java.math.BigDecimal;

public class SystemsTask implements Runnable {

    public void run() {

        BigDecimal systemCpuLoad = BigDecimal.valueOf(getSystemCPULoad());
        BigDecimal processCpuLoad = BigDecimal.valueOf(getProcessCPULoad());

        if (systemCpuLoad.signum() != -1) { // if value is not negative
            for (SystemMovingAverage average : SystemConstants.getSystemCPUAvgs()) {
                average.add(systemCpuLoad);
            }
        }

        if (processCpuLoad.signum() != -1) { // if value is not negative
            for (SystemMovingAverage average : SystemConstants.getProcessCPUAvgs()) {
                average.add(processCpuLoad);
            }
        }
    }

    private double getSystemCPULoad() {
        return MonitorManager.getMonitor("systems_cpu").getElement() instanceof Double ? (double) MonitorManager.getMonitor("systems_cpu").getElement() : 0;
    }

    private double getProcessCPULoad() {
        return MonitorManager.getMonitor("processed_cpu").getElement() instanceof Double ? (double) MonitorManager.getMonitor("processed_cpu").getElement() : 0;
    }
}
