package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.Messages;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.user.User;
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
    private final NotificationAnnouncer announcer;

    private final List<Audience> audiences = new ArrayList<>();
    private final List<NotificationExtractor> notifications = new ArrayList<>();
    private final Formatter placeholders = new Formatter();

    Notice(LanguageManager languageManager, AudienceProvider audienceProvider, NotificationAnnouncer announcer) {
        this.languageManager = languageManager;
        this.audienceProvider = audienceProvider;
        this.announcer = announcer;
    }

    public Notice user(User user) {
        this.audiences.add(audienceProvider.user(user));
        return this;
    }

    public Notice player(UUID uuid) {
        this.audiences.add(audienceProvider.player(uuid));
        return this;
    }

    @Deprecated
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

    public Notice staticNotice(Notification notification) {
        this.notifications.add(messages -> notification);
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
        Map<Language, Set<Audience>> audiencesByLang = new HashMap<>();

        for (Audience audience : audiences) {
            audiencesByLang
                .computeIfAbsent(audience.getLanguage(), (key) -> new HashSet<>())
                .add(audience);
        }

        Map<Language, List<Notification>> formattedMessages = new HashMap<>();

        for (Language language : audiencesByLang.keySet()) {
            Messages messages = languageManager.getMessages(language);
            ArrayList<Notification> notifications = new ArrayList<>();

            for (NotificationExtractor extractor : this.notifications) {
                Notification notification = extractor.extract(messages)
                    .edit(placeholders::format);

                notifications.add(notification);
            }

            formattedMessages.put(language, notifications);
        }

        for (Map.Entry<Language, Set<Audience>> entry : audiencesByLang.entrySet()) {
            Language language = entry.getKey();
            List<Notification> notifications = formattedMessages.get(language);

            if (notifications == null) {
                continue;
            }

            for (Notification notification : notifications) {
                announcer.announce(entry.getValue(), notification);
            }
        }
    }

}
