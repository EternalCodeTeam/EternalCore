package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.MessageExtractor;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.language.NotificationExtractor;
import com.eternalcode.core.language.OptionalMessageExtractor;
import com.eternalcode.core.placeholder.PlaceholderBukkitRegistryImpl;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerProvider;
import panda.std.Option;
import panda.utilities.StringUtils;
import panda.utilities.text.Formatter;
import panda.utilities.text.Joiner;

import javax.annotation.CheckReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final PlaceholderBukkitRegistryImpl placeholderRegistry;

    private final List<Viewer> viewers = new ArrayList<>();
    private final List<NotificationExtractor> notifications = new ArrayList<>();

    private final Map<String, MessageExtractor> placeholders = new HashMap<>();
    private final List<Formatter> formatters = new ArrayList<>();

    Notice(LanguageManager languageManager, ViewerProvider viewerProvider, NotificationAnnouncer announcer, PlaceholderBukkitRegistryImpl placeholderRegistry) {
        this.languageManager = languageManager;
        this.viewerProvider = viewerProvider;
        this.announcer = announcer;
        this.placeholderRegistry = placeholderRegistry;
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
    public Notice onlinePlayers() {
        this.viewers.addAll(this.viewerProvider.onlinePlayers());
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
    public Notice noticeOption(NoticeType type, OptionalMessageExtractor extractor) {
        this.notifications.add(messages -> {
            Option<String> apply = extractor.extract(messages);

            if (apply.isPresent()) {
                return new Notification(apply.get(), type);
            }

            return new Notification(StringUtils.EMPTY, NoticeType.NONE);
        });
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
    public Notice placeholder(String from, Option<String> to) {
        if (to.isPresent()) {
            this.placeholders.put(from, messages -> to.get());
        }

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

    @CheckReturnValue
    public <T> Notice placeholder(Placeholders<T> placeholder, T context) {
        this.formatters.add(placeholder.toFormatter(context));
        return this;
    }

    @CheckReturnValue
    public Notice formatter(Formatter... formatters) {
        this.formatters.addAll(Arrays.asList(formatters));
        return this;
    }

    public void send() {
        ViewersWithLanguage viewersByLanguage = ViewersWithLanguage.of(this.viewers);
        TranslatedMessages translatedMessages = this.prepareTranslatedMessages(viewersByLanguage);

        this.sendTranslatedMessages(viewersByLanguage, translatedMessages);
    }

    private void sendTranslatedMessages(ViewersWithLanguage viewersByLanguage, TranslatedMessages translatedMessages) {
        for (Language language : viewersByLanguage.getLanguages()) {
            List<Notification> translatedNotifications = translatedMessages.getNotifications(language);

            if (translatedNotifications == null) {
                continue;
            }

            FormatterForLanguage formatterForLanguage = this.prepareFormatterForLanguage(language);

            for (Notification notification : translatedNotifications) {
                Set<Viewer> languageViewers = viewersByLanguage.getViewers(language);

                for (Viewer viewer : languageViewers) {
                    notification = notification.fork(text -> formatterForLanguage.format(text, viewer));

                    this.announcer.announce(viewer, notification);
                }
            }
        }
    }

    private TranslatedMessages prepareTranslatedMessages(ViewersWithLanguage viewersByLanguage) {
        Map<Language, List<Notification>> translatedMessage = new HashMap<>();

        for (Language language : viewersByLanguage.getLanguages()) {
            List<Notification> translatedNotifications = this.prepareTranslatedNotificationsPerViewer(language);

            translatedMessage.put(language, translatedNotifications);
        }

        return TranslatedMessages.of(translatedMessage);
    }

    private List<Notification> prepareTranslatedNotificationsPerViewer(Language language) {
        Messages messages = this.languageManager.getMessages(language);
        List<Notification> notificationsForLanguage = new ArrayList<>();

        for (NotificationExtractor extractor : this.notifications) {
            Notification notificationForLanguage = extractor.extract(messages);

            notificationsForLanguage.add(notificationForLanguage);
        }

        return notificationsForLanguage;
    }

    private FormatterForLanguage prepareFormatterForLanguage(Language language) {
        Messages messages = this.languageManager.getMessages(language);
        Formatter translatedFormatter = new Formatter();

        for (Map.Entry<String, MessageExtractor> entry : this.placeholders.entrySet()) {
            translatedFormatter.register(entry.getKey(), () -> entry.getValue().extract(messages));
        }

        return new FormatterForLanguage(translatedFormatter);
    }

    private class FormatterForLanguage {
        private final Formatter translatedPlaceholders;

        private FormatterForLanguage(Formatter translatedPlaceholders) {
            this.translatedPlaceholders = translatedPlaceholders;
        }

        public String format(String text, Viewer viewer) {
            text = placeholderRegistry.format(text, viewer);
            text = translatedPlaceholders.format(text);

            for (Formatter formatter : formatters) {
                text = formatter.format(text);
            }

            return text;
        }
    }

}
