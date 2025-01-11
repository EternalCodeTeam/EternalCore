package com.eternalcode.core.translation;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.feature.language.config.LanguageConfiguration;
import com.eternalcode.core.translation.implementation.TranslationFactory;
import java.util.List;
import panda.std.stream.PandaStream;

@Setup
class TranslationManagerSetup {

    @Bean
    TranslationManager translationManager(ConfigurationManager configurationManager, LanguageConfiguration languageConfiguration) {
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

}
