package com.eternalcode.core.user;

import com.eternalcode.core.language.Language;

import java.util.function.Supplier;

public class SettingsImpl implements Settings {

    private final Supplier<ClientSettings> clientSettings;
    private Language language;

    public SettingsImpl(Supplier<ClientSettings> clientSettings) {
        this.clientSettings = clientSettings;
    }

    @Override
    public Language getLanguage() {
        return language != null ? language : Language.fromLocate(clientSettings.get().getLocate());
    }

    @Override
    public void setLanguage(Language language) {
        this.language = language;
    }

}
