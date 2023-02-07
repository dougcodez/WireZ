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
package io.github.dougcodez.wirez.files.types.settings;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.dougcodez.wirez.files.AbstractConfigFile;

import java.io.File;
import java.util.Collections;

public class WireZSettingsFile extends AbstractConfigFile {

    public void initConfigFile(File destination) {
        initFile(destination, "wirez-settings.yml");
    }

    @Override
    public void initData() {
        YamlDocument config = getConfigFile();
        if (config == null) return;
        if (config.getKeys().size() != 0) return;
        config.set("website-graph.enable", true);
        config.set("website-graph.host", "localhost");
        config.set("website-graph.port", 1000);
        config.set("database-module.enable", true);
        config.set("database-module.player-whitelist", Collections.singletonList("DougDevelopz"));
        config.set("database-module.driver", "MYSQL");
    }
}
