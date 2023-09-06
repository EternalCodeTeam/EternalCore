package com.eternalcode.core.user;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageSettings;

public interface UserSettings extends LanguageSettings {

    @Override
    Language getLanguage();

    @Override
    void setLanguage(Language language);

}
