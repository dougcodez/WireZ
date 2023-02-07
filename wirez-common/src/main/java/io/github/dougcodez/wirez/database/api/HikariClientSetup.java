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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface HikariClientSetup extends ConnectionType {

    default HikariConfig getDataProperties(SQLTypes types, DatabaseAuthentication authentication, int timeOut, int poolSize) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(types.getDriverName());
        config.setUsername(authentication.user());
        config.setPassword(authentication.password());
        config.setJdbcUrl(generateURL(types.getDriverURL(), authentication));
        config.setConnectionTimeout(timeOut);
        config.setMaximumPoolSize(poolSize);
        return config;
    }

    default HikariClientSetup createTable(HikariDataSource dataSource, String tableOutput) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(tableOutput)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    default String generateURL(String jdUrl, DatabaseAuthentication authentication) {
        String url = jdUrl.replace("{host}", authentication.host());
        url = url.replace("{port}", String.valueOf(authentication.port()));
        url = url.replace("{database}", authentication.database());
        return url;
    }
}
