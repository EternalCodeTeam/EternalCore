package com.eternalcode.core.translation;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.translation.implementation.TranslationFactory;
import java.util.HashMap;
import java.util.Map;

@Setup
class TranslationManagerSetup {

    private final TranslationSettings translationSettings;

    @Inject
    TranslationManagerSetup(TranslationSettings translationSettings) {
        this.translationSettings = translationSettings;
    }

    @Bean
    TranslationManager translationManager(
        ConfigurationManager configurationManager
    ) {
        Map<Language, Translation> translations = new HashMap<>();

        AbstractTranslation enTranslation = TranslationFactory.create(Language.EN);
        configurationManager.load(enTranslation);
        translations.put(Language.EN, enTranslation);

        if (!this.translationSettings.language().equals(Language.EN)) {
            AbstractTranslation selectedTranslation = TranslationFactory.create(translationSettings.language());
            configurationManager.load(selectedTranslation);
            translations.put(this.translationSettings.language(), selectedTranslation);
        }

        return new TranslationManager(translations, translationSettings.language());
    }
}
