package com.eternalcode.core.user.settings;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.user.client.ClientSettings;

import java.util.function.Supplier;

public class SettingsImpl implements Settings {

    private final Supplier<ClientSettings> clientSettings;
    private Language language;

    public SettingsImpl(Supplier<ClientSettings> clientSettings) {
        this.clientSettings = clientSettings;
    }

    @Override
    public Language getLanguage() {
        return this.language != null ? this.language : Language.fromLocate(this.clientSettings.get().getLocate());
    }

    @Override
    public void setLanguage(Language language) {
        this.language = language;
    }

}
