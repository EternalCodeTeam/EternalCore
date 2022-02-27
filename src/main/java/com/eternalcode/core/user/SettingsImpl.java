package com.eternalcode.core.user;

import com.eternalcode.core.language.Language;

public class SettingsImpl implements Settings {

    private final ClientSettings clientSettings;
    private Language language;

    public SettingsImpl(ClientSettings clientSettings) {
        this.clientSettings = clientSettings;
    }

    @Override
    public Language getLanguage() {
        return language != null ? language : Language.fromLocate(clientSettings.getLocate());
    }

    @Override
    public void setLanguage(Language language) {
        this.language = language;
    }

}
