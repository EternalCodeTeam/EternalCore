package com.eternalcode.core.user;

import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.feature.language.LanguageSettings;

public interface UserSettings extends LanguageSettings {

    @Override
    Language getLanguage();

    @Override
    void setLanguage(Language language);

}
