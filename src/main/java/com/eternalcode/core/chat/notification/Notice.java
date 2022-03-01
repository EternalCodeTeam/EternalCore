package com.eternalcode.core.chat.notification;

import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;
import panda.utilities.text.Joiner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class Notice {

    private final LanguageManager languageManager;
    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    private final List<Audience> audiences = new ArrayList<>();
    private final List<NotificationExtractor> notifications = new ArrayList<>();
    private final Formatter placeholders = new Formatter();

    Notice(LanguageManager languageManager, AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.languageManager = languageManager;
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    public Notice user(User user) {
        this.audiences.add(audienceProvider.user(user));
        return this;
    }

    public Notice player(UUID uuid) {
        this.audiences.add(audienceProvider.player(uuid));
        return this;
    }

    public Notice sender(CommandSender commandSender) {
        if (commandSender instanceof Player player) {
            this.player(player.getUniqueId());
            return this;
        }

        if (commandSender instanceof ConsoleCommandSender) {
            this.console();
        }

        return this;
    }

    public Notice console() {
        this.audiences.add(audienceProvider.console());
        return this;
    }

    public Notice all() {
        this.audiences.addAll(audienceProvider.all());
        return this;
    }

    public Notice allPlayers() {
        this.audiences.addAll(audienceProvider.allPlayers());
        return this;
    }

    public Notice message(MessageExtractor extractor) {
        this.notifications.add(messages -> new Notification(extractor.extract(messages), NotificationType.CHAT));
        return this;
    }

    public Notice messages(Function<Messages, List<String>> function) {
        MessageExtractor messageExtractor = (messages) -> {
            List<String> apply = function.apply(messages);

            return Joiner.on("\n").join(apply).toString();
        };

        return this.message(messageExtractor);
    }

    public Notice staticNotice(Component component) {
        this.notifications.add(messages -> new AdventureNotification(component, NotificationType.CHAT));
        return this;
    }

    public Notice staticNotice(NotificationType type, Component component) {
        this.notifications.add(messages -> new AdventureNotification(component, type));
        return this;
    }

    public Notice notice(NotificationType type, MessageExtractor extractor) {
        this.notifications.add(messages -> new Notification(extractor.extract(messages), type));
        return this;
    }

    public Notice notice(NotificationExtractor extractor) {
        this.notifications.add(extractor);
        return this;
    }

    public Notice placeholder(String from, String to) {
        this.placeholders.register(from, () -> to);
        return this;
    }

    public Notice placeholder(String from, Supplier<String> to) {
        this.placeholders.register(from, to);
        return this;
    }

    public void send() {
        Set<Language> languages = new HashSet<>();

        for (Audience audience : audiences) {
            languages.add(audience.getLanguage());
        }

        Map<Language, List<AdventureNotification>> formattedMessages = new HashMap<>();

        for (Language language : languages) {
            Messages messages = languageManager.getMessages(language);
            ArrayList<AdventureNotification> notifications = new ArrayList<>();

            for (NotificationExtractor extractor : this.notifications) {
                AdventureNotification notification = extractor.extract(messages)
                    .edit(placeholders::format)
                    .toAdventure(miniMessage::deserialize);

                notifications.add(notification);
            }

            formattedMessages.put(language, notifications);
        }

        for (Audience audience : audiences) {
            List<AdventureNotification> notifications = formattedMessages.get(audience.getLanguage());

            if (notifications == null) {
                return;
            }

            for (AdventureNotification notification : notifications) {
                notification.notify(audience);
            }
        }
    }

}
