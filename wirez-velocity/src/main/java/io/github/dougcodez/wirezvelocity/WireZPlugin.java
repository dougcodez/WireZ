package io.github.dougcodez.wirezvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.dougcodez.wirez.WireZPluginExtension;
import io.github.dougcodez.wirez.commands.sub.SubCommandRegistry;
import io.github.dougcodez.wirez.commands.sub.types.impl.database.DumpTable;
import io.github.dougcodez.wirez.files.types.lang.LangFile;
import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;
import io.github.dougcodez.wirez.monitors.task.SystemsThreadExecutor;
import io.github.dougcodez.wirez.platform.PlatformInfo;
import io.github.dougcodez.wirez.platform.PlatformType;
import io.github.dougcodez.wirez.promise.PromiseGlobalExecutor;
import io.github.dougcodez.wirez.websocket.WireZDataTransfer;
import lombok.Getter;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Plugin(
        id = "wirez",
        name = "WireZ",
        version = "1.0.1",
        description = "A MC platform diagnostics tool used to collect system statistics and interactive database information",
        authors = {"DougCodez"}
)
@Getter
public class WireZPlugin implements WireZPluginExtension, PlatformInfo {

    private static WireZPlugin instance;
    private final ProxyServer proxyServer;
    private final Logger logger;
    private final Path dataDirectory;
    private WireZSettingsFile settingsFile;
    private VelocityLibrarySetup velocityLibrarySetup;
    private LangFile langFile;

    @Inject
    public WireZPlugin(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataDirectory) {
        instance = this;
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onEnable(ProxyInitializeEvent event) {
        this.getLogger().log(Level.INFO, getPlatformInfo());
        settingsFile = new WireZSettingsFile();
        settingsFile.initConfigFile(getDataDirectory().toFile());
        registerInstantiations();
        registerFiles();
        registerCommands();

        if (settingsFile.getConfigFile().getBoolean(("website-graph.enable"))) {
            String host = settingsFile.getConfigFile().getString("website-graph.host");
            int port = settingsFile.getConfigFile().getInt("website-graph.port");
            WireZDataTransfer dataTransfer = new WireZDataTransfer(host, port);
            proxyServer.getScheduler().buildTask(this, dataTransfer::sendData).repeat(20, TimeUnit.SECONDS).schedule();
        }

        if (settingsFile.getConfigFile().getBoolean("database-module.enabled")) {
            if (SubCommandRegistry.getSubCommandMap().containsKey(DumpTable.class.getSimpleName())) {
                DumpTable dumpTable = (DumpTable) SubCommandRegistry.getSubCommandMap().get(DumpTable.class.getSimpleName());
                dumpTable.setDataFolder(() -> getDataDirectory().toFile());
            }
        }
    }

    @Subscribe(order = PostOrder.LAST)
    public void onDisable(ProxyShutdownEvent event){
        this.getLogger().log(Level.INFO, "WireZ is shutting down...");
        SystemsThreadExecutor.close();
        PromiseGlobalExecutor.close();
    }

    @Override
    public void registerInstantiations() {
        velocityLibrarySetup = new VelocityLibrarySetup();
        langFile = new LangFile();
    }

    @Override
    public void registerLibraries() {
        velocityLibrarySetup.loadLibraries();
    }

    @Override
    public void registerFiles() {
        langFile.initLangFile(getDataDirectory().toFile());
    }

    @Override
    public void registerCommands() {
        SubCommandRegistry.registerCommands(getSettingsFile());
        proxyServer.getCommandManager().register("wirez", new WireZCommandImpl(), "wz");
    }

    public static WireZPlugin getInstance() {
        return instance;
    }

    @Override
    public String getID() {
        return "WireZ Velocity";
    }

    @Override
    public PlatformType getType() {
        return PlatformType.PROXY;
    }

    @Override
    public String getVersion() {
        return "1.0.1";
    }
}
