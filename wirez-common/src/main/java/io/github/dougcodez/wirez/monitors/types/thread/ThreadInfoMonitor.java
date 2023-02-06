package io.github.dougcodez.wirez.monitors.types.thread;

import io.github.dougcodez.wirez.monitors.api.MonitorWrapper;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

public class ThreadInfoMonitor  implements MonitorWrapper<ThreadInfo[]> {

    @Override
    public boolean isThread() {
        return true;
    }

    @Override
    public boolean isCPU() {
        return false;
    }

    @Override
    public boolean isMemory() {
        return false;
    }

    @Override
    public ThreadInfo[] getElement() {
        ThreadMXBean threadMXBean = initThreadMXBean();
        return threadMXBean.getThreadInfo(Arrays.stream(threadMXBean.getAllThreadIds()).toArray());
    }
}
