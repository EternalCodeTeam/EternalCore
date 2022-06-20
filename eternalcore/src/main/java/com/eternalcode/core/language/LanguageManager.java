package com.eternalcode.core.language;

import com.eternalcode.core.configuration.ConfigurationManager;
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

    private Messages defaultMessages;
    private final Map<Language, Messages> translatedMessages = new HashMap<>();

    public LanguageManager(Messages defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public void loadLanguage(Language language, Messages translated) {
        this.translatedMessages.put(language, translated);
    }

    public Messages getMessages(Language language) {
        Messages messages = translatedMessages.get(language);

        if (messages != null) {
            return messages;
        }

        for (Entry<Language, Messages> entry : translatedMessages.entrySet()) {
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

    public void setDefaultMessages(Messages defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public Messages getDefaultMessages() {
        return this.defaultMessages;
    }

    public Messages getMessages(Viewer viewer) {
        return this.getMessages(viewer.getLanguage());
    }

    public static LanguageManager create(ConfigurationManager configurationManager, File dataFolder) {
        LanguageConfiguration languageConfiguration = configurationManager.getLanguageConfiguration();

        File langFolder = new File(dataFolder, "lang");
        Map<Language, Messages> defaultImplementations = new ImmutableMap.Builder<Language, Messages>()
            .put(Language.EN, new ENMessagesConfiguration(langFolder, "en_messages.yml"))
            .put(Language.PL, new PLMessagesConfiguration(langFolder, "pl_messages.yml"))
            .build();

        List<Messages> messages = PandaStream.of(languageConfiguration.languages)
            .map(lang -> defaultImplementations.getOrDefault(lang, new ENMessagesConfiguration(langFolder, lang.getLang() + "_messages.yml")))
            .toList();

        Messages defaultMessages = PandaStream.of(messages)
            .find(m -> m.getLanguage().equals(languageConfiguration.defaultLanguage))
            .orThrow(() -> new RuntimeException("Default language not found!"));

        LanguageManager languageManager = new LanguageManager(defaultMessages);

        for (Messages message : messages) {
            configurationManager.loadAndRender(message);
            languageManager.loadLanguage(message.getLanguage(), message);
        }

        return languageManager;
    }

}
