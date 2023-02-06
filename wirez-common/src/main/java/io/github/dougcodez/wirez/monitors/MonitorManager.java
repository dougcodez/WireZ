package io.github.dougcodez.wirez.monitors;

import io.github.dougcodez.wirez.monitors.api.MonitorWrapper;
import io.github.dougcodez.wirez.monitors.types.cpu.ProcessCPUMonitor;
import io.github.dougcodez.wirez.monitors.types.cpu.SystemCPUMonitor;
import io.github.dougcodez.wirez.monitors.types.disk.DiskMonitor;
import io.github.dougcodez.wirez.monitors.types.memory.MemoryMonitor;
import io.github.dougcodez.wirez.monitors.types.thread.ThreadDumpMonitor;
import io.github.dougcodez.wirez.monitors.types.thread.ThreadInfoMonitor;
import lombok.experimental.UtilityClass;
import java.util.LinkedHashMap;
import java.util.Map;

@UtilityClass
public class MonitorManager {

    private final Map<String, MonitorWrapper<?>> monitors = new LinkedHashMap<>();

    public void registerMonitors() {
        monitors.put("processed_cpu", new ProcessCPUMonitor());
        monitors.put("systems_cpu", new SystemCPUMonitor());
        monitors.put("disk_monitor", new DiskMonitor());
        monitors.put("memory_monitor", new MemoryMonitor());
        monitors.put("thread_dump_monitor", new ThreadDumpMonitor());
        monitors.put("thread_info_monitor", new ThreadInfoMonitor());
    }

    public MonitorWrapper<?> getMonitor(String name) {
        return monitors.get(name);
    }

    public Map<String, MonitorWrapper<?>> getMonitors() {
        return monitors;
    }
}
