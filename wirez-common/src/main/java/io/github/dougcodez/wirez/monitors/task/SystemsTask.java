/**
 * MIT License
 *
 * Copyright (c) 2022-2023 Douglas (dougcodez)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
