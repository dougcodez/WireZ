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
package io.github.dougcodez.wirez.monitors.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SystemConstants {

    //System CPU
    private static final SystemMovingAverage SYSTEM_CPU_10_SEC = new SystemMovingAverage(10);
    private static final SystemMovingAverage SYSTEM_CPU_1_MIN = new SystemMovingAverage(60);
    private static final SystemMovingAverage SYSTEM_CPU_15_MIN = new SystemMovingAverage(60 * 15);

    //Process CPU
    private static final SystemMovingAverage PROCESS_CPU_10_SEC = new SystemMovingAverage(10);
    private static final SystemMovingAverage PROCESS_CPU_1_MIN = new SystemMovingAverage(60);
    private static final SystemMovingAverage PROCESS_CPU_15_MIN = new SystemMovingAverage(60 * 15);

    private static List<SystemMovingAverage> systemCPUAvgs = Collections.synchronizedList(Arrays.asList(SYSTEM_CPU_10_SEC, SYSTEM_CPU_1_MIN, SYSTEM_CPU_15_MIN));

    private static List<SystemMovingAverage> processCPUAvgs = Collections.synchronizedList(Arrays.asList(PROCESS_CPU_10_SEC, PROCESS_CPU_1_MIN, PROCESS_CPU_15_MIN));


    public static double getSystemCPU10Sec() {
        return SYSTEM_CPU_10_SEC.getAverage();
    }

    public static double getSystemCPU1Min() {
        return SYSTEM_CPU_1_MIN.getAverage();
    }

    public static double getSystemCPU15Min() {
        return SYSTEM_CPU_15_MIN.getAverage();
    }

    public static double getProcessCPU10Sec() {
        return PROCESS_CPU_10_SEC.getAverage();
    }

    public static double getProcessCPU1Min() {
        return PROCESS_CPU_1_MIN.getAverage();
    }

    public static double getProcessCPU15Min() {
        return PROCESS_CPU_15_MIN.getAverage();
    }

    public static List<SystemMovingAverage> getSystemCPUAvgs() {
        return systemCPUAvgs;
    }

    public static List<SystemMovingAverage> getProcessCPUAvgs() {
        return processCPUAvgs;
    }

}
