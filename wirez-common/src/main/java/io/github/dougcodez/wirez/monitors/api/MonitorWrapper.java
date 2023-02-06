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
