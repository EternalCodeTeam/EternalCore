package com.eternalcode.core.translation;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.feature.language.LanguageService;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.feature.language.config.LanguageConfiguration;
import com.eternalcode.core.translation.implementation.TranslationFactory;
import java.util.List;

@Setup
class TranslationManagerSetup {

    @Bean
    TranslationManager translationManager(
        ConfigurationManager configurationManager,
        LanguageService languageService,
        LanguageConfiguration languageConfiguration
    ) {
        List<AbstractTranslation> usedMessagesList = languageConfiguration.languages.stream()
            .map(TranslationFactory::create)
            .toList();

        Translation defaultTranslation = usedMessagesList.stream()
            .filter(usedMessages -> usedMessages.getLanguage().equals(languageConfiguration.defaultLanguage))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Default language not found!"));

        TranslationManager translationManager = new TranslationManager(languageService, defaultTranslation);

        for (AbstractTranslation message : usedMessagesList) {
            configurationManager.load(message);
            translationManager.loadLanguage(message.getLanguage(), message);
        }

        return translationManager;
    }

}
