package com.eternalcode.core.user;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageSettings;

import java.util.Locale;

public interface Settings extends LanguageSettings {

    Language getLanguage();

    void setLanguage(Language language);

}
