package io.github.dougcodez.wirez.libraries;

import io.github.dougcodez.wirez.library.LibraryObject;

public enum Libraries implements LibraryObject {

    MYSQL_CONNECTOR("mysql", "mysql-connector-java", "8.0.30", "mysql_connector_library", "mysql", "wirez.relocated.mysql"),
    HIKARI("com.zaxxer", "HikariCP", "4.0.3", "hikari_library", "com.zaxxer", "wirez.relocated.com.zaxxer"),
    POSTGRES("org.postgresql", "postgresql", "42.5.1", "postgres_library", "org.postgresql", "wirez.relocated.org.postgresql");

    private final String groupID;
    private final String artifactID;
    private final String version;
    private final String id;
    private final String oldRelocation;
    private final String newRelocation;

    Libraries(String groupID, String artifactID, String version, String id, String oldRelocation, String newRelocation) {
        this.groupID = groupID;
        this.artifactID = artifactID;
        this.version = version;
        this.id = id;
        this.oldRelocation = oldRelocation;
        this.newRelocation = newRelocation;
    }

    @Override
    public String groupID() {
        return groupID;
    }

    @Override
    public String artifactID() {
        return artifactID;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String oldRelocation() {
        return oldRelocation;
    }

    @Override
    public String newRelocation() {
        return newRelocation;
    }
}
