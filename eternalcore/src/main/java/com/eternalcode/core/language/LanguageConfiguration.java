package com.eternalcode.core.language;

import com.eternalcode.core.configuration.ReloadableConfig;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class LanguageConfiguration implements ReloadableConfig {

    public Language defaultLanguage = Language.EN;
    public List<Language> languages = Arrays.asList(Language.EN, Language.PL);

    public LanguageSelector languageSelector = new LanguageSelector();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "language.yml");
    }
}
