package io.github.dougcodez.wirezvelocity;

import com.velocitypowered.api.plugin.PluginManager;
import io.github.dougcodez.wirez.library.LibraryManager;
import io.github.dougcodez.wirez.library.logging.adapter.JDKLogAdapter;

import java.nio.file.Path;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class VelocityLibraryManager<T> extends LibraryManager {

    /**
     * Velocity plugin manager used for adding files to the plugin's classpath
     */
    private final PluginManager pluginManager;

    /**
     * The plugin instance required by the plugin manager to add files to the
     * plugin's classpath
     */
    private final T plugin;

    /**
     * Creates a new Velocity library manager.
     *
     * @param logger        the plugin logger
     * @param dataDirectory plugin's data directory
     * @param pluginManager Velocity plugin manager
     * @param plugin        the plugin to manage
     * @param directoryName download directory name
     */
    public VelocityLibraryManager(Logger logger,
                                  Path dataDirectory,
                                  PluginManager pluginManager,
                                  T plugin,
                                  String directoryName) {

        super(new JDKLogAdapter(logger), dataDirectory, directoryName);
        this.pluginManager = requireNonNull(pluginManager, "pluginManager");
        this.plugin = requireNonNull(plugin, "plugin");
    }

    /**
     * Creates a new Velocity library manager.
     *
     * @param logger        the plugin logger
     * @param dataDirectory plugin's data directory
     * @param pluginManager Velocity plugin manager
     * @param plugin        the plugin to manage
     */
    public VelocityLibraryManager(Logger logger,
                                  Path dataDirectory,
                                  PluginManager pluginManager,
                                  T plugin) {
        this(logger, dataDirectory, pluginManager, plugin, "lib");
    }

    /**
     * Adds a file to the Velocity plugin's classpath.
     *
     * @param file the file to add
     */
    @Override
    protected void addToClasspath(Path file) {
        pluginManager.addToClasspath(plugin, file);
    }
}
