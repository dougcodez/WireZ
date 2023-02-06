package io.github.dougcodez.wirez.database.api;

public record DatabaseAuthentication(String host, int port, String database, String user, String password) {

}
