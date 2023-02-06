package io.github.dougcodez.wirez.files.types.lang;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.dougcodez.wirez.files.AbstractConfigFile;

import java.io.File;

public class LangFile extends AbstractConfigFile {

    private static LangFile instance = null;

    public static LangFile getInstance() {
        if (instance == null) {
            instance = new LangFile();
        }
        return instance;
    }

    public void initLangFile(File destinationFile) {
        initFile(destinationFile, "messages.yml");
    }

    @Override
    public void initData() {
        YamlDocument config = getConfigFile();
        if (config == null) return;
        if (config.getKeys().size() != 0) return;
        for (Lang item : Lang.CACHE) {
            config.set(item.getKey(), item.getValue());
        }
    }
}
