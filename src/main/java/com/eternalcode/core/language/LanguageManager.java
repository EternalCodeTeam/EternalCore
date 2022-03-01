package com.eternalcode.core.language;

import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LanguageManager {

    private final UserManager userManager;

    private Messages defaultMessages;
    private final Map<Language, Messages> translatedMessages = new HashMap<>();

    public LanguageManager(UserManager userManager, Messages defaultMessages) {
        this.userManager = userManager;
        this.defaultMessages = defaultMessages;
    }

    public void registerLang(Language language, Messages translated) {
        this.translatedMessages.put(language, translated);
    }

    public Messages getMessages(Language language) {
        return translatedMessages.getOrDefault(language, defaultMessages);
    }

    public Messages getMessages(CommandSender commandSender) {
        if (commandSender instanceof Player player) {
            return this.userManager.getUser(player.getUniqueId())
                .map(user -> user.getSettings().getLanguage())
                .map(this.translatedMessages::get)
                .orElseGet(this.defaultMessages);
        }

        return this.defaultMessages;
    }

    public Messages getMessages(Player player) {
        return this.userManager.getUser(player.getUniqueId())
            .map(user -> user.getSettings().getLanguage())
            .map(this.translatedMessages::get)
            .orElseGet(this.defaultMessages);
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
