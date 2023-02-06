package io.github.dougcodez.wirez.libraries;

import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;
import io.github.dougcodez.wirez.library.Library;
import io.github.dougcodez.wirez.library.LibraryObject;

import java.util.ArrayList;
import java.util.List;

public abstract class LibrarySetup<LibraryManager> {

    public List<Library> getListOfLibraries() {
        WireZSettingsFile settingsFile = getSettingsFile();
        List<Library> libraries = new ArrayList<>();
        if (settingsFile != null) {
            if (settingsFile.getConfigFile().getBoolean("database-module.enable")) {
                libraries.add(createLibrary(Libraries.MYSQL_CONNECTOR));
                libraries.add(createLibrary(Libraries.HIKARI));
                if (settingsFile.getConfigFile().getString("database-module.driver").equalsIgnoreCase("POSTGRES")) {
                    libraries.add(createLibrary(Libraries.POSTGRES));
                }
            }
        }

        return libraries;
    }

    public abstract void loadLibraries();

    public abstract LibraryManager getLibraryManager();

    public abstract WireZSettingsFile getSettingsFile();

    public Library createLibrary(LibraryObject libraryObject) {
        return Library.builder().groupId(libraryObject.groupID())
                .artifactId(libraryObject.artifactID())
                .version(libraryObject.version())
                .id(libraryObject.id())
                .relocate(libraryObject.oldRelocation(), libraryObject.newRelocation()).build();
    }
}
