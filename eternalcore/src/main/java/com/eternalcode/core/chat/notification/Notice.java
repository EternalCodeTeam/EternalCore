package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.MessageExtractor;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.language.NotificationExtractor;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerProvider;
import panda.utilities.text.Formatter;
import panda.utilities.text.Joiner;

import javax.annotation.CheckReturnValue;
import java.util.ArrayList;
import java.util.Collection;
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
    private final ViewerProvider viewerProvider;
    private final NotificationAnnouncer announcer;

    private final List<Viewer> viewers = new ArrayList<>();
    private final List<NotificationExtractor> notifications = new ArrayList<>();
    private final Map<String, MessageExtractor> placeholders = new HashMap<>();

    Notice(LanguageManager languageManager, ViewerProvider viewerProvider, NotificationAnnouncer announcer) {
        this.languageManager = languageManager;
        this.viewerProvider = viewerProvider;
        this.announcer = announcer;
    }

    @CheckReturnValue
    public Notice user(User user) {
        this.viewers.add(user);
        return this;
    }

    @CheckReturnValue
    public Notice player(UUID player) {
        this.viewers.add(this.viewerProvider.player(player));
        return this;
    }

    @CheckReturnValue
    public Notice players(Iterable<UUID> players) {
        Set<Viewer> viewers = new HashSet<>();

        for (UUID player : players) {
            viewers.add(this.viewerProvider.player(player));
        }

        this.viewers.addAll(viewers);
        return this;
    }

    @CheckReturnValue
    public Notice viewer(Viewer viewer) {
        this.viewers.add(viewer);
        return this;
    }

    @CheckReturnValue
    public Notice console() {
        this.viewers.add(this.viewerProvider.console());
        return this;
    }

    @CheckReturnValue
    public Notice all() {
        this.viewers.addAll(this.viewerProvider.all());
        return this;
    }

    @CheckReturnValue
    public Notice allPlayers() {
        this.viewers.addAll(this.viewerProvider.allPlayers());
        return this;
    }

    @CheckReturnValue
    public Notice message(MessageExtractor extractor) {
        this.notifications.add(messages -> new Notification(extractor.extract(messages), NoticeType.CHAT));
        return this;
    }

    @CheckReturnValue
    public Notice messages(Function<Messages, List<String>> function) {
        MessageExtractor messageExtractor = (messages) -> {
            List<String> apply = function.apply(messages);

            return Joiner.on("\n").join(apply).toString();
        };

        return this.message(messageExtractor);
    }

    @CheckReturnValue
    public Notice staticNotice(Notification notification) {
        this.notifications.add(messages -> notification);
        return this;
    }

    @CheckReturnValue
    public Notice notice(NoticeType type, MessageExtractor extractor) {
        this.notifications.add(messages -> new Notification(extractor.extract(messages), type));
        return this;
    }

    @CheckReturnValue
    public Notice notice(NotificationExtractor extractor) {
        this.notifications.add(extractor);
        return this;
    }

    @CheckReturnValue
    public Notice placeholder(String from, String to) {
        this.placeholders.put(from, messages -> to);
        return this;
    }

    @CheckReturnValue
    public Notice placeholder(String from, Supplier<String> to) {
        this.placeholders.put(from, messages -> to.get());
        return this;
    }

    @CheckReturnValue
    public Notice placeholder(String from, MessageExtractor extractor) {
        this.placeholders.put(from, extractor);
        return this;
    }

    public void send() {
        Map<Language, Set<Viewer>> viewerByLang = new HashMap<>();

        for (Viewer viewer : viewers) {
            viewerByLang
                .computeIfAbsent(viewer.getLanguage(), (key) -> new HashSet<>())
                .add(viewer);
        }

        Map<Language, List<Notification>> formattedMessages = new HashMap<>();

        for (Language language : viewerByLang.keySet()) {
            Messages messages = languageManager.getMessages(language);
            ArrayList<Notification> notifications = new ArrayList<>();
            Formatter translatedFormatter = new Formatter();

            for (Map.Entry<String, MessageExtractor> entry : placeholders.entrySet()) {
                translatedFormatter.register(entry.getKey(), entry.getValue().extract(messages));
            }

            for (NotificationExtractor extractor : this.notifications) {
                Notification notification = extractor.extract(messages)
                    .edit(translatedFormatter::format);

                notifications.add(notification);
            }

            formattedMessages.put(language, notifications);
        }

        for (Map.Entry<Language, Set<Viewer>> entry : viewerByLang.entrySet()) {
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
