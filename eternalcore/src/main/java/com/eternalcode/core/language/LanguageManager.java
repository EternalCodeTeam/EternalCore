package com.eternalcode.core.language;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.ReloadableMessages;
import com.eternalcode.core.configuration.lang.CustomMessagesConfiguration;
import com.eternalcode.core.configuration.lang.ENMessagesConfiguration;
import com.eternalcode.core.configuration.lang.PLMessagesConfiguration;
import com.eternalcode.core.configuration.language.LanguageConfiguration;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import com.google.common.collect.ImmutableMap;
import panda.std.stream.PandaStream;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LanguageManager {

    private final Map<Language, Messages> translatedMessages = new HashMap<>();
    private Messages defaultMessages;

    public LanguageManager(Messages defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public static LanguageManager create(ConfigurationManager configurationManager, LanguageConfiguration languageConfiguration, File dataFolder) {
        File langFolder = new File(dataFolder, "lang");
        Map<Language, ReloadableMessages> defaultImplementations = new ImmutableMap.Builder<Language, ReloadableMessages>()
            .put(Language.EN, new ENMessagesConfiguration())
            .put(Language.PL, new PLMessagesConfiguration())
            .build();

        List<ReloadableMessages> messages = PandaStream.of(languageConfiguration.languages)
            .map(lang -> defaultImplementations.getOrDefault(lang, new CustomMessagesConfiguration(lang.getLang())))
            .toList();

        Messages defaultMessages = PandaStream.of(messages)
            .find(m -> m.getLanguage().equals(languageConfiguration.defaultLanguage))
            .orThrow(() -> new RuntimeException("Default language not found!"));

        LanguageManager languageManager = new LanguageManager(defaultMessages);

        for (ReloadableMessages message : messages) {
            configurationManager.load(message);
            languageManager.loadLanguage(message.getLanguage(), message);
        }

        return languageManager;
    }

    public void loadLanguage(Language language, Messages translated) {
        this.translatedMessages.put(language, translated);
    }

    public Messages getMessages(Language language) {
        Messages messages = this.translatedMessages.get(language);

        if (messages != null) {
            return messages;
        }

        for (Entry<Language, Messages> entry : this.translatedMessages.entrySet()) {
            if (entry.getKey().isEquals(language)) {
                return entry.getValue();
            }
        }

        return this.defaultMessages;
    }

    public Messages getMessages(User user) {
        LanguageSettings settings = user.getSettings();
        Language language = settings.getLanguage();

        return this.getMessages(language);
    }

    public Messages getDefaultMessages() {
        return this.defaultMessages;
    }

    public void setDefaultMessages(Messages defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public Messages getMessages(Viewer viewer) {
        return this.getMessages(viewer.getLanguage());
    }

}
