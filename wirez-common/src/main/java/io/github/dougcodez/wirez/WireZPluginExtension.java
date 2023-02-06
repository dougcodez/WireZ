package io.github.dougcodez.wirez;

public interface WireZPluginExtension {

    void registerInstantiations();

    void registerLibraries();

    void registerFiles();

    void registerCommands();

}
