package com.eternalcode.core.translation;

import com.eternalcode.multification.translation.TranslationProvider;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class TranslationManager implements TranslationProvider<Translation> {

    private final Map<Language, Translation> translations;
    private final Language serverLanguage;

    TranslationManager(Map<Language, Translation> translations, Language serverLanguage) {
        this.translations = translations;
        this.serverLanguage = serverLanguage;
    }

    public Translation getMessages() {
        return translations.getOrDefault(serverLanguage, translations.get(Language.EN));
    }

    @Deprecated
    public Translation getMessages(UUID playerId) {
        return getMessages();
    }

    @Deprecated
    public Translation getMessages(Language language) {
        return getMessages();
    }

    @NotNull
    @Override
    public Translation provide(Locale locale) {
        return getMessages();
    }
}
