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
package io.github.dougcodez.wirezvelocity4;

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
