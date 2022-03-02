package com.eternalcode.core.language;

import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.configuration.lang.en.ENMessagesConfiguration;
import com.eternalcode.core.configuration.lang.pl.PLMessagesConfiguration;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.google.common.collect.ImmutableMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.stream.PandaStream;

import java.io.File;
import java.util.Collection;
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
        return translatedMessages.getOrDefault(language, defaultMessages);
    }

    public Messages getMessages(User user) {
        LanguageSettings settings = user.getSettings();
        Language language = settings.getLanguage();

        return translatedMessages.getOrDefault(language, defaultMessages);
    }

    public void setDefaultMessages(Messages defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public Messages getDefaultMessages() {
        return defaultMessages;
    }

}
