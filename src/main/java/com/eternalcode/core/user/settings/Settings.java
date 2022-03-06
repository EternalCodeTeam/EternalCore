package com.eternalcode.core.user.settings;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageSettings;

public interface Settings extends LanguageSettings {

    Language getLanguage();

    void setLanguage(Language language);

}
