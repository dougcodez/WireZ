package io.github.dougcodez.wirezvelocity;

import io.github.dougcodez.wirez.files.types.settings.WireZSettingsFile;
import io.github.dougcodez.wirez.libraries.LibrarySetup;

public class VelocityLibrarySetup extends LibrarySetup<VelocityLibraryManager<?>> {

    private final VelocityLibraryManager<WireZPlugin> velocityLibraryManager = new VelocityLibraryManager<>(
            WireZPlugin.getInstance().getLogger(),
            WireZPlugin.getInstance().getDataDirectory(),
            WireZPlugin.getInstance().getProxyServer().getPluginManager(),
            WireZPlugin.getInstance());


    @Override
    public void loadLibraries() {
        velocityLibraryManager.addMavenCentral();
        getListOfLibraries().forEach(library -> velocityLibraryManager.loadLibrary(library));
    }

    @Override
    public VelocityLibraryManager<WireZPlugin> getLibraryManager() {
        return velocityLibraryManager;
    }

    @Override
    public WireZSettingsFile getSettingsFile() {
        return WireZPlugin.getInstance().getSettingsFile();
    }
}
