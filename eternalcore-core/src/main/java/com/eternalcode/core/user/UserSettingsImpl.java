package com.eternalcode.core.user;

import com.eternalcode.core.feature.language.Language;

import java.util.function.Supplier;

class UserSettingsImpl implements UserSettings {

    private final Supplier<UserClientSettings> clientSettings;
    private Language language = Language.DEFAULT;

    public UserSettingsImpl(Supplier<UserClientSettings> clientSettings) {
        this.clientSettings = clientSettings;
    }

    @Override
    public Language getLanguage() {
        return this.language != Language.DEFAULT
            ? this.language
            : Language.fromLocate(this.clientSettings.get().getLocate());
    }

    @Override
    public void setLanguage(Language language) {
        this.language = language;
    }

}
