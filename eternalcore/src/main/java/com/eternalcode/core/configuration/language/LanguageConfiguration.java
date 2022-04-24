package com.eternalcode.core.configuration.language;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.language.Language;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class LanguageConfiguration extends AbstractConfigWithResource {

    public LanguageConfiguration(File folder, String child) {
        super(folder, child);
    }

    public Language defaultLanguage = Language.EN;
    public List<Language> languages = Arrays.asList(Language.EN, Language.PL);

    public LanguageSelector languageSelector = new LanguageSelector();
}
