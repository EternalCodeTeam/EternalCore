package com.eternalcode.core.language;

import com.eternalcode.core.user.User;

import java.util.HashMap;
import java.util.Map;

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
        return this.translatedMessages.getOrDefault(language, this.defaultMessages);
    }

    public Messages getMessages(User user) {
        LanguageSettings settings = user.getSettings();
        Language language = settings.getLanguage();

        return this.translatedMessages.getOrDefault(language, this.defaultMessages);
    }

    public void setDefaultMessages(Messages defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public Messages getDefaultMessages() {
        return this.defaultMessages;
    }

}
