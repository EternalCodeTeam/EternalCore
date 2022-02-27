package com.eternalcode.core.language;

import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.user.Settings;
import com.eternalcode.core.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LanguageManager {

    private Messages defaultMessages;
    private final Map<Language, Messages> translatedMessages = new HashMap<>();

    public LanguageManager(Messages defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public void registerLang(Language language, Messages translated) {
        this.translatedMessages.put(language, translated);
    }

    public void setDefaultMessages(Messages defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public Messages getDefaultMessages() {
        return defaultMessages;
    }

    public Messages getMessages(User user) {
        LanguageSettings settings = user.getSettings();
        Language language = settings.getLanguage();

        return translatedMessages.getOrDefault(language, defaultMessages);
    }

    public Messages getMessages(Language language) {
        return translatedMessages.getOrDefault(language, defaultMessages);
    }

}
