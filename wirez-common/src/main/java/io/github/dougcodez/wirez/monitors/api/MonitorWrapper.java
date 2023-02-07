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
package io.github.dougcodez.wirez.monitors.api;

import com.sun.management.OperatingSystemMXBean;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.atomic.AtomicReference;

public interface MonitorWrapper<T> {

    boolean isThread();

    boolean isCPU();

    boolean isMemory();

    default ThreadMXBean initThreadMXBean() {
        AtomicReference<ThreadMXBean> threadMXBean = new AtomicReference<>();
        if (isThread()) {
            threadMXBean.set(ManagementFactory.getThreadMXBean());
        }

        return threadMXBean.get();
    }


    default OperatingSystemMXBean initOperatingSystemMXBean() {
        AtomicReference<OperatingSystemMXBean> operatingSystemMXBeanAtomicReference = new AtomicReference<>();
        if (isCPU()) {
            MBeanServerConnection connection = ManagementFactory.getPlatformMBeanServer();
            try {
                operatingSystemMXBeanAtomicReference.set(ManagementFactory.newPlatformMXBeanProxy(connection, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                        com.sun.management.OperatingSystemMXBean.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return operatingSystemMXBeanAtomicReference.get();
    }

    default MemoryMXBean initMemoryXBean() {
        AtomicReference<MemoryMXBean> memoryMXBean = new AtomicReference<>();
        if (isMemory()) {
            memoryMXBean.set(ManagementFactory.getMemoryMXBean());
        }

        return memoryMXBean.get();
    }

    T getElement();
}
