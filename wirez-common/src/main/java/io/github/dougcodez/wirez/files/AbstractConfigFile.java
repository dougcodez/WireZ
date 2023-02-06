package io.github.dougcodez.wirez.files;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;

public abstract class AbstractConfigFile implements IConfigFile{

        private YamlDocument customConfig;

        public void initFile(File fileDestination, String fileName) {
            createFile(fileDestination, fileName);
            initData();
            save();
        }

        @Override
        public void createFile(File fileDestination, String fileName) {
            File file = new File(fileDestination, fileName);
            try {
                customConfig = YamlDocument.create(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void save() {
            try {
                customConfig.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void reload() {
            try {
                customConfig.reload();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public YamlDocument getConfigFile() {
            return customConfig;
        }


        public abstract void initData();
}
