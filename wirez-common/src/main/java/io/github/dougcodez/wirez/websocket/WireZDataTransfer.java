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
package io.github.dougcodez.wirez.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.dougcodez.wirez.monitors.MonitorManager;
import io.github.dougcodez.wirez.monitors.api.MonitorWrapper;
import io.github.dougcodez.wirez.monitors.types.disk.DiskMonitor;
import io.github.dougcodez.wirez.monitors.types.memory.MemoryMonitor;
import io.github.dougcodez.wirez.monitors.util.SystemConstants;
import io.github.dougcodez.wirez.promise.Promise;
import io.github.dougcodez.wirez.promise.PromiseGlobalExecutor;

import static io.github.dougcodez.wirez.monitors.util.SystemConstants.*;

public class WireZDataTransfer {

    private final WireZDataServer socketServer;
    private static final Gson gson = new Gson();

    public WireZDataTransfer(String host, int port) {
        socketServer = new WireZDataServer(host, port);
        Promise.createNew().fulfillInAsync(() -> {
            socketServer.run();
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }

    public void sendData() {
        JsonElement parent = new JsonObject();
        JsonObject jsonObject = parent.getAsJsonObject();
        for (MonitorWrapper<?> monitor : MonitorManager.getMonitors().values()) {
            if (monitor.getElement().getClass().isArray()) {
                if (monitor.isThread()) continue;
                break;
            } else {
                jsonObject.add(monitor.getClass().getSimpleName(), gson.toJsonTree(monitor.getElement()));
            }
        }

        jsonObject.addProperty("System CPU 10s", SystemConstants.getSystemCPU10Sec());
        jsonObject.addProperty("System CPU 1m", SystemConstants.getSystemCPU1Min());
        jsonObject.addProperty("System CPU 15m", SystemConstants.getSystemCPU15Min());

        jsonObject.addProperty("Processed CPU 10s", SystemConstants.getProcessCPU10Sec());
        jsonObject.addProperty("Processed CPU 1m", SystemConstants.getProcessCPU1Min());
        jsonObject.addProperty("Processed CPU 15m", SystemConstants.getProcessCPU15Min());

        MemoryMonitor memoryMonitor = (MemoryMonitor) MonitorManager.getMonitor("memory_monitor");
        double memDiv = (memoryMonitor.getElement()[1] / memoryMonitor.getElement()[0]) * 100;
        jsonObject.addProperty(memoryMonitor.getClass().getSimpleName(), String.valueOf(memDiv));

        DiskMonitor diskMonitor = (DiskMonitor) MonitorManager.getMonitor("disk_monitor");
        double diskDiv = (diskMonitor.getElement()[2] / diskMonitor.getElement()[0]) * 100;
        jsonObject.addProperty(diskMonitor.getClass().getSimpleName(), String.valueOf(diskDiv));

        socketServer.broadcast(gson.toJson(parent));
    }
}
