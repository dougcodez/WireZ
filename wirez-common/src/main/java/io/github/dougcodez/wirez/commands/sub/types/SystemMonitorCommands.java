package io.github.dougcodez.wirez.commands.sub.types;

import io.github.dougcodez.wirez.commands.user.CommandUser;
import io.github.dougcodez.wirez.files.types.lang.Lang;
import io.github.dougcodez.wirez.health.HealthFormat;
import io.github.dougcodez.wirez.monitors.MonitorManager;
import io.github.dougcodez.wirez.monitors.types.cpu.ProcessCPUMonitor;
import io.github.dougcodez.wirez.monitors.types.cpu.SystemCPUMonitor;
import io.github.dougcodez.wirez.monitors.types.disk.DiskMonitor;
import io.github.dougcodez.wirez.monitors.types.memory.MemoryMonitor;
import io.github.dougcodez.wirez.monitors.types.thread.ThreadDumpMonitor;
import io.github.dougcodez.wirez.monitors.types.thread.ThreadInfoMonitor;
import io.github.dougcodez.wirez.monitors.util.SystemConstants;
import io.github.dougcodez.wirez.promise.Promise;
import io.github.dougcodez.wirez.promise.PromiseGlobalExecutor;
import io.github.dougcodez.wirez.util.ByteBinClient;
import lombok.experimental.UtilityClass;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class SystemMonitorCommands {

    public void sendSystemsCPUInformation(CommandUser sender) {
        String systemsCPU = Lang.CPU_INFO_SYSTEMS.toConfigString()
                .replace("%10s%", String.valueOf(Math.round(SystemConstants.getSystemCPU10Sec())))
                .replace("%1m%", String.valueOf(Math.round(SystemConstants.getSystemCPU1Min())))
                .replace("%15m%", String.valueOf(Math.round(SystemConstants.getSystemCPU15Min())));
        sender.sendMessage(systemsCPU);
        SystemCPUMonitor systemCPUMonitor = (SystemCPUMonitor) MonitorManager.getMonitor("systems_cpu");
        sender.sendMessage(HealthFormat.format(systemCPUMonitor.getElement()));
    }

    public void sendProcessedCPUInformation(CommandUser sender) {
        String processedCPU = Lang.CPU_INFO_PROCESSED.toConfigString()
                .replace("%10s%", String.valueOf(Math.round(SystemConstants.getProcessCPU10Sec())))
                .replace("%1m%", String.valueOf(Math.round(SystemConstants.getProcessCPU1Min())))
                .replace("%15m%", String.valueOf(Math.round(SystemConstants.getProcessCPU15Min())));
        sender.sendMessage(processedCPU);
        ProcessCPUMonitor processCPUMonitor = (ProcessCPUMonitor) MonitorManager.getMonitor("processed_cpu");
        sender.sendMessage(HealthFormat.format(processCPUMonitor.getElement()));
    }

    public void sendDiskInformation(CommandUser sender) {
        DiskMonitor diskMonitor = (DiskMonitor) MonitorManager.getMonitor("disk_monitor");
        String diskInfo = Lang.DISK_INFO.toConfigString()
                .replace("%used%", String.valueOf(Math.round(diskMonitor.getElement()[2])))
                .replace("%total%", String.valueOf(Math.round(diskMonitor.getElement()[0])));
        sender.sendMessage(diskInfo);
        double div = (diskMonitor.getElement()[2] / diskMonitor.getElement()[0]) * 100;
        sender.sendMessage(HealthFormat.format(div));
    }

    public void sendRamInformation(CommandUser sender) {
        MemoryMonitor memoryMonitor = (MemoryMonitor) MonitorManager.getMonitor("memory_monitor");
        String ramInfo = Lang.RAM_INFO.toConfigString()
                .replace("%used%", String.valueOf(Math.round(memoryMonitor.getElement()[1])))
                .replace("%total%", String.valueOf(Math.round(memoryMonitor.getElement()[0])));
        sender.sendMessage(ramInfo);
        double div = (memoryMonitor.getElement()[1] / memoryMonitor.getElement()[0]) * 100;
        sender.sendMessage(HealthFormat.format(div));
    }

    public void sendThreadInformation(CommandUser sender) {
        Promise.createNew().fulfillInAsync(() -> {
            ThreadInfoMonitor threadInfoMonitor = (ThreadInfoMonitor) MonitorManager.getMonitor("thread_info_monitor");
            ByteBinClient.postRequest(List.of(Arrays.toString(threadInfoMonitor.getElement())), () -> sender.sendMessage(Lang.THREAD_DUMP_COMPLETE.toConfigString().replace("%key%", ByteBinClient.getKey())));
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }

    public void sendThreadDump(CommandUser sender) {
        Promise.createNew().fulfillInAsync(() -> {
            ThreadDumpMonitor threadDumpMonitor = (ThreadDumpMonitor) MonitorManager.getMonitor("thread_dump_monitor");
            ByteBinClient.postRequest(List.of(Arrays.toString(threadDumpMonitor.getElement())), () -> sender.sendMessage(Lang.THREAD_DUMP_COMPLETE.toConfigString().replace("%key%", ByteBinClient.getKey())));
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }

    public void printHeapSummary(CommandUser sender) {
        Promise.createNew().fulfillInAsync(() -> {
            try {
                MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
                ObjectName diagnosticName = ObjectName.getInstance("com.sun.management:type=DiagnosticCommand");
                GCHistogram diagnosticCommandMBean = JMX.newMXBeanProxy(beanServer, diagnosticName, GCHistogram.class);
                String histogramVisual = diagnosticCommandMBean.gcClassHistogram(new String[0]);
                ByteBinClient.postRequest(Collections.singletonList(histogramVisual), () -> sender.sendMessage(Lang.HEAP_DUMP_COMPLETE.toConfigString().replace("%key%", ByteBinClient.getKey())));
            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            }
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }

    public interface GCHistogram {
        String gcClassHistogram(String[] args);
    }
}
