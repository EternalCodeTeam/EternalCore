package com.eternalcode.core.user;

import com.eternalcode.core.language.Language;

import java.util.Locale;

public interface Settings {

    Language getLanguage();

    void setLanguage(Language language);

    Settings NONE = new SettingsImpl(ClientSettings.NONE);

}
