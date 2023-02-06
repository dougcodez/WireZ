package io.github.dougcodez.wirez.platform;

public interface PlatformInfo {

    String getID();

    PlatformType getType();

    String getVersion();

    default String getPlatformInfo() {
        return String.valueOf(new PlatformRecord("Platform ID: " + getID() + "\n", "Platform Type: " + getType() + "\n",
                "Platform Version: " + getVersion()));
    }
}