/**
 * MIT License
 *
 * Copyright (c) 2022-2023 Douglas (dougcodez)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
