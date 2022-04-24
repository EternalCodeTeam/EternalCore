package com.eternalcode.core.user;

import com.eternalcode.core.entity.Entity;
import com.eternalcode.core.user.client.ClientSettings;
import com.eternalcode.core.user.settings.Settings;
import com.eternalcode.core.user.settings.SettingsImpl;

import java.util.UUID;

public class User implements Entity {

    private Settings settings = new SettingsImpl(() -> this.clientSettings);
    private ClientSettings clientSettings = ClientSettings.NONE;

    private final String name;
    private final UUID uuid;

    User(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    public ClientSettings getClientSettings() {
        return this.clientSettings;
    }

    public void setClientSettings(ClientSettings clientSettings) {
        this.clientSettings = clientSettings;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

}

