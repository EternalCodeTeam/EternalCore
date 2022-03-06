/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.user;

import com.eternalcode.core.user.client.ClientSettings;
import com.eternalcode.core.user.settings.Settings;
import com.eternalcode.core.user.settings.SettingsImpl;

import java.util.UUID;

public class User {

    private final String name;
    private final UUID uuid;
    private ClientSettings clientSettings = ClientSettings.NONE;
    private Settings settings = new SettingsImpl(() -> this.clientSettings);

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
