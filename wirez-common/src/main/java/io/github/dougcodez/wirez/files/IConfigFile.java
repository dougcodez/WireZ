package io.github.dougcodez.wirez.files;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;

public interface IConfigFile {

    void createFile(File fileDestination, String fileName);

    void save();

    void reload();

    YamlDocument getConfigFile();
}
