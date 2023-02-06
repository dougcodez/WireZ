package io.github.dougcodez.wirezbungee;

import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;
import io.github.dougcodez.wirez.libraries.LibrarySetup;

public class BungeeLibrarySetup extends LibrarySetup<BungeeLibraryManager> {

    @Override
    public void loadLibraries() {
        getLibraryManager().addMavenCentral();
        getListOfLibraries().forEach(library -> getLibraryManager().loadLibrary(library));
    }

    @Override
    public BungeeLibraryManager getLibraryManager() {
        return WireZPlugin.getInstance().getLibraryManager();
    }

    @Override
    public WireZSettingsFile getSettingsFile() {
        return WireZPlugin.getInstance().getSettingsFile();
    }
}
