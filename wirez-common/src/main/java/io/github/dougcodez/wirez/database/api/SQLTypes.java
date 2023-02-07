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
package io.github.dougcodez.wirez.database.api;

public enum SQLTypes {


    MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://{host}:{port}/{database}"),
    SQLITE("org.sqlite.JDBC", "jdbc:sqlite:{database}"),
    POSTGRES("org.postgresql.ds.PGSimpleDataSource", "jdbc:postgresql://{host}:{port}/{database}");

    private final String driverName;
    private final String driverURL;

    SQLTypes(String driverName, String driverURL) {
        this.driverName = driverName;
        this.driverURL = driverURL;
    }

    public static SQLTypes fromName(String name) {
        for (SQLTypes type : values()) {
            if (type.name().equalsIgnoreCase(name))
                return type;
        }
        return SQLITE;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverURL() {
        return driverURL;
    }
}
