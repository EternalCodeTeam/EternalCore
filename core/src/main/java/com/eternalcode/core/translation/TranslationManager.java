package com.eternalcode.core.translation;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageSettings;
import com.eternalcode.core.language.config.LanguageConfiguration;
import com.eternalcode.core.translation.implementation.TranslationFactory;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import panda.std.stream.PandaStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TranslationManager {

    private final Map<Language, Translation> translatedMessages = new HashMap<>();
    private Translation defaultTranslation;

    public TranslationManager(Translation defaultTranslation) {
        this.defaultTranslation = defaultTranslation;
    }

    public static TranslationManager create(ConfigurationManager configurationManager, LanguageConfiguration languageConfiguration) {
        List<AbstractTranslation> usedMessagesList = PandaStream.of(languageConfiguration.languages)
            .map(TranslationFactory::create)
            .toList();

        Translation defaultTranslation = PandaStream.of(usedMessagesList)
            .find(usedMessages -> usedMessages.getLanguage().equals(languageConfiguration.defaultLanguage))
            .orThrow(() -> new RuntimeException("Default language not found!"));

        TranslationManager translationManager = new TranslationManager(defaultTranslation);

        for (ReloadableTranslation message : usedMessagesList) {
            configurationManager.load(message);
            translationManager.loadLanguage(message.getLanguage(), message);
        }

        return translationManager;
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

}
