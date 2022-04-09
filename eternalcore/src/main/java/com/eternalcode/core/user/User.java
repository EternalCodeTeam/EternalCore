package com.eternalcode.core.user;

import com.eternalcode.core.entity.Entity;
import com.eternalcode.core.user.client.ClientSettings;
import com.eternalcode.core.user.settings.Settings;
import com.eternalcode.core.user.settings.SettingsImpl;

import java.util.UUID;

public class User implements Entity {

    private final String name;
    private final UUID uuid;
    private ClientSettings clientSettings = ClientSettings.NONE;
    private Settings settings = new SettingsImpl(() -> this.clientSettings);

    User(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
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

