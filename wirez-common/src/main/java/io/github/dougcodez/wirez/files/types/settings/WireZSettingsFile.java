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
