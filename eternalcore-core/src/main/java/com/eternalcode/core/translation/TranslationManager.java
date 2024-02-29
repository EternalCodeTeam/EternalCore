package com.eternalcode.core.translation;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.feature.language.LanguageSettings;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.multification.translation.TranslationProvider;
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

    private final Map<Language, Translation> translatedMessages = new HashMap<>();
    private Translation defaultTranslation;

    TranslationManager(Translation defaultTranslation) {
        this.defaultTranslation = defaultTranslation;
    }

    public void loadLanguage(Language language, Translation translated) {
        this.translatedMessages.put(language, translated);
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

    public Translation getMessages(User user) {
        LanguageSettings settings = user.getSettings();
        Language language = settings.getLanguage();

        return this.getMessages(language);
    }

    public Translation getDefaultMessages() {
        return this.defaultTranslation;
    }

    public void setDefaultMessages(Translation defaultTranslation) {
        this.defaultTranslation = defaultTranslation;
    }

    public Translation getMessages(Viewer viewer) {
        return this.getMessages(viewer.getLanguage());
    }

    @NotNull
    @Override
    public Translation provide(Locale locale) {
        return this.getMessages(Language.fromLocate(locale));
    }
}
