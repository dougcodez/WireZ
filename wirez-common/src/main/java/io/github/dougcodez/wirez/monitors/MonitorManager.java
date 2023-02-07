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
