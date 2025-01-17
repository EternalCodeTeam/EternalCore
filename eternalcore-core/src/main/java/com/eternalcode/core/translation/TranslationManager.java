package com.eternalcode.core.translation;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.feature.language.LanguageService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.multification.translation.TranslationProvider;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

@FeatureDocs(
    name = "Translations",
    description = "EternalCore can use multiple languages at once, the player can determine which EternalCore language I want to use, or you can determine from above what language all players want to use"
)
public class TranslationManager implements TranslationProvider<Translation> {

    private final LanguageService languageService;

    private final Map<Language, Translation> translatedMessages = new HashMap<>();
    private Translation defaultTranslation;

    TranslationManager(LanguageService languageService, Translation defaultTranslation) {
        this.languageService = languageService;
        this.defaultTranslation = defaultTranslation;
    }

    public void loadLanguage(Language language, Translation translated) {
        this.translatedMessages.put(language, translated);
    }

    public Translation getMessages(UUID uniqueId) {
        return getMessages(this.languageService.getLanguageNow(uniqueId));
    }

    public Translation getMessages(Language language) {
        Translation translation = this.translatedMessages.get(language);

        if (translation != null) {
            return translation;
        }

        for (Entry<Language, Translation> entry : this.translatedMessages.entrySet()) {
            if (entry.getKey().isEquals(language)) {
                return entry.getValue();
            }
        }

        return this.defaultTranslation;
    }

    public Translation getDefaultMessages() {
        return this.defaultTranslation;
    }

    public void setDefaultMessages(Translation defaultTranslation) {
        this.defaultTranslation = defaultTranslation;
    }

    @NotNull
    @Override
    public Translation provide(Locale locale) {
        return this.getMessages(Language.fromLocale(locale));
    }

    public Set<Language> getAvailableLanguages() {
        return Collections.unmodifiableSet(this.translatedMessages.keySet());
    }

}
