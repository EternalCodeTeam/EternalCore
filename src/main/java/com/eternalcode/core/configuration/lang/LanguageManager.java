package com.eternalcode.core.configuration.lang;

import java.util.HashMap;
import java.util.Map;

public class LanguageManager {

    private final Map<String, MessagesConfiguration> translatedMessages = new HashMap<>();

    public void registerLang(String lang, MessagesConfiguration translated) {
        this.translatedMessages.put(lang, translated);
    }

}
