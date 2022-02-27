/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.user;

import java.util.UUID;

public class User {

    private final String name;
    private final UUID uuid;
    private ClientSettings clientSettings;
    private Settings settings;

    User(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ClientSettings getClientSettings() {
        return clientSettings;
    }

    public void setClientSettings(ClientSettings clientSettings) {
        this.clientSettings = clientSettings;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

}
