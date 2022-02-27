package com.eternalcode.core.language;

import com.eternalcode.core.configuration.lang.MessagesConfiguration;
import com.eternalcode.core.user.Settings;
import com.eternalcode.core.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LanguageManager {

    private MessagesConfiguration defaultMessages;
    private final Map<Language, MessagesConfiguration> translatedMessages = new HashMap<>();
    private final Map<UUID, Language> locale = new HashMap<>();

    public LanguageManager(MessagesConfiguration defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public void registerLang(Language language, MessagesConfiguration translated) {
        this.translatedMessages.put(language, translated);
    }

    public void setDefaultMessages(MessagesConfiguration defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public MessagesConfiguration getDefaultMessages() {
        return defaultMessages;
    }

    public MessagesConfiguration getMessages(User user) {
        Settings settings = user.getSettings();
        Language language = settings.getLanguage();

        return translatedMessages.getOrDefault(language, defaultMessages);
    }

}
