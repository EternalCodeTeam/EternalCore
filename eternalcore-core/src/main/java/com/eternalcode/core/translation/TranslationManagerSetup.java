package com.eternalcode.core.translation;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.translation.implementation.TranslationFactory;

@Setup
class TranslationManagerSetup {

    @Bean
    TranslationManager translationManager(ConfigurationManager configurationManager, PluginConfiguration pluginConfiguration) {
        String languageCode = pluginConfiguration.language;
        Translation translation = TranslationFactory.create(languageCode);
        configurationManager.load((ReloadableTranslation) translation);
        return new TranslationManager(translation);
    }
}
