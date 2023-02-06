package io.github.dougcodez.wirezbukkit;

import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;
import io.github.dougcodez.wirez.libraries.LibrarySetup;

public class BukkitLibrarySetup extends LibrarySetup<BukkitLibraryManager> {

    @Override
    public void loadLibraries() {
        getLibraryManager().addMavenCentral();
        getListOfLibraries().forEach(library -> getLibraryManager().loadLibrary(library));
    }

    @Override
    public BukkitLibraryManager getLibraryManager() {
        return WireZPlugin.getInstance().getLibraryManager();
    }

    @Override
    public WireZSettingsFile getSettingsFile() {
        return WireZPlugin.getInstance().getSettingsFile();
    }
}
