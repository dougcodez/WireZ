package io.github.dougcodez.wirez.database.api;

import java.sql.Connection;

public interface ConnectionType {

    Connection getConnection();
}
