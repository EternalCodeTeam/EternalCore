package com.eternalcode.core.database;

public interface DatabaseSettings {

    DatabaseDriverType databaseType();

    String hostname();

    int port();

    String database();

    String username();

    String password();

    boolean ssl();

    int poolSize();

    int timeout();
}
