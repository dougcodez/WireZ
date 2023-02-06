package io.github.dougcodez.wirezbukkit;

import io.github.dougcodez.wirez.library.LibraryManager;
import io.github.dougcodez.wirez.library.classloader.URLClassLoaderHelper;
import io.github.dougcodez.wirez.library.logging.adapter.JDKLogAdapter;
import org.bukkit.plugin.Plugin;

import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class BukkitLibraryManager extends LibraryManager {
    /**
     * Plugin classpath helper
     */
    private final URLClassLoaderHelper classLoader;

    /**
     * Creates a new Bukkit library manager.
     *
     * @param plugin the plugin to manage
     */
    public BukkitLibraryManager(Plugin plugin) {
        this(plugin, "lib");
    }

    /**
     * Creates a new Bukkit library manager.
     *
     * @param plugin the plugin to manage
     * @param directoryName download directory name
     */
    public BukkitLibraryManager(Plugin plugin, String directoryName) {
        super(new JDKLogAdapter(requireNonNull(plugin, "plugin").getLogger()), plugin.getDataFolder().toPath(), directoryName);
        classLoader = new URLClassLoaderHelper((URLClassLoader) plugin.getClass().getClassLoader(), this);
    }

    /**
     * Adds a file to the Bukkit plugin's classpath.
     *
     * @param file the file to add
     */
    @Override
    protected void addToClasspath(Path file) {
        classLoader.addToClasspath(file);
    }
}
