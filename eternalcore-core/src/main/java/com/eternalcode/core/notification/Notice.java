package com.eternalcode.core.notification;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.notification.extractor.NotificationExtractor;
import com.eternalcode.core.notification.extractor.NotificationsExtractor;
import com.eternalcode.core.notification.extractor.OptionalNotificationExtractor;
import com.eternalcode.core.notification.extractor.TranslatedMessageExtractor;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class Notice {

    private final Scheduler scheduler;
    private final TranslationManager translationManager;
    private final ViewerProvider viewerProvider;
    private final NotificationAnnouncer announcer;
    private final PlaceholderRegistry placeholderRegistry;

    private final List<Viewer> viewers = new ArrayList<>();
    private final List<NotificationsExtractor> notifications = new ArrayList<>();

    private final Map<String, TranslatedMessageExtractor> placeholders = new HashMap<>();
    private final List<Formatter> formatters = new ArrayList<>();

    Notice(Scheduler scheduler, TranslationManager translationManager, ViewerProvider viewerProvider, NotificationAnnouncer announcer, PlaceholderRegistry placeholderRegistry) {
        this.scheduler = scheduler;
        this.translationManager = translationManager;
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
    public Notice message(TranslatedMessageExtractor extractor) {
        this.notifications.add(NotificationsExtractor.of(translation -> new Notification(extractor.extract(translation), NoticeType.CHAT)));
        return this;
    }

    public Notice message(NotificationExtractor extractor) {
        this.notifications.add(extractor);

        return this;
    }

    @CheckReturnValue
    public Notice messages(Function<Translation, List<String>> function) {
        TranslatedMessageExtractor translatedMessageExtractor = translation -> {
            List<String> apply = function.apply(translation);

            return Joiner.on("\n").join(apply).toString();
        };

        return this.message(translatedMessageExtractor);
    }

    @CheckReturnValue
    public Notice staticNotice(Notification notification) {
        this.notifications.add(NotificationsExtractor.of(notification));

        return this;
    }

    @CheckReturnValue
    public Notice notice(NoticeType type, TranslatedMessageExtractor extractor) {
        this.notifications.add(NotificationsExtractor.of(translation -> new Notification(extractor.extract(translation), type)));

        return this;
    }

    @CheckReturnValue
    public Notice noticeOption(OptionalNotificationExtractor extractor) {
        this.notifications.add((NotificationExtractor) translation -> {
            Option<Notification> apply = extractor.extract(translation);

            if (apply.isPresent()) {
                return apply.get();
            }

            return new Notification(StringUtils.EMPTY, NoticeType.DISABLED);
        });
        return this;
    }

    @CheckReturnValue
    public Notice notice(NotificationExtractor extractor) {
        this.notifications.add(extractor);
        return this;
    }

    @CheckReturnValue
    public Notice notifications(Function<Translation, List<Notification>> function) {
        NotificationsExtractor notificationsExtractor = function::apply;

        this.notifications.add(notificationsExtractor);

        return this;
    }

    @CheckReturnValue
    public Notice placeholder(String from, String to) {
        this.placeholders.put(from, translation -> to);
        return this;
    }

    @CheckReturnValue
    public Notice placeholder(String from, Option<String> to) {
        if (to.isPresent()) {
            this.placeholders.put(from, translation -> to.get());
        }

        return this;
    }


    @CheckReturnValue
    public Notice placeholder(String from, Optional<String> to) {
        if (to.isPresent()) {
            this.placeholders.put(from, translation -> to.get());
        }

        return this;
    }


    @CheckReturnValue
    public Notice placeholder(String from, Supplier<String> to) {
        this.placeholders.put(from, translation -> to.get());
        return this;
    }

    @CheckReturnValue
    public Notice placeholder(String from, TranslatedMessageExtractor extractor) {
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

    public void sendAsync() {
        this.scheduler.async(this::send);
    }

    public void send() {
        ViewersWithLanguage viewersByLanguage = ViewersWithLanguage.of(this.viewers);
        TranslatedNotifications translatedNotifications = this.prepareTranslatedMessagesSet(viewersByLanguage);

        this.sendTranslatedMessages(viewersByLanguage, translatedNotifications);
    }

    private void sendTranslatedMessages(ViewersWithLanguage viewersByLanguage, TranslatedNotifications translatedNotifications) {
        for (Language language : viewersByLanguage.getLanguages()) {
            List<Notification> notificationsForLang = translatedNotifications.forLanguage(language);

            if (notificationsForLang == null) {
                continue;
            }

            FormatterForLanguage formatterForLanguage = this.prepareFormatterForLanguage(language);

            for (Notification notification : notificationsForLang) {
                Set<Viewer> languageViewers = viewersByLanguage.getViewers(language);

                for (Viewer viewer : languageViewers) {
                    notification = notification.fork(text -> formatterForLanguage.format(text, viewer));

                    this.announcer.announce(viewer, notification);
                }
            }
        }
    }

    private TranslatedNotifications prepareTranslatedMessagesSet(ViewersWithLanguage viewersByLanguage) {
        Map<Language, List<Notification>> translatedMessage = new HashMap<>();

        for (Language language : viewersByLanguage.getLanguages()) {
            List<Notification> translatedNotifications = this.prepareTranslatedNotificationsPerViewer(language);

            translatedMessage.put(language, translatedNotifications);
        }

        return TranslatedNotifications.of(translatedMessage);
    }

    private List<Notification> prepareTranslatedNotificationsPerViewer(Language language) {
        Translation translation = this.translationManager.getMessages(language);
        List<Notification> notificationsForLanguage = new ArrayList<>();

        for (NotificationsExtractor extractor : this.notifications) {
            List<Notification> notificationForLanguage = extractor.extractNotifications(translation);

            notificationsForLanguage.addAll(notificationForLanguage);
        }

        return notificationsForLanguage;
    }

    private FormatterForLanguage prepareFormatterForLanguage(Language language) {
        Translation translation = this.translationManager.getMessages(language);
        Formatter translatedFormatter = new Formatter();

        for (Map.Entry<String, TranslatedMessageExtractor> entry : this.placeholders.entrySet()) {
            translatedFormatter.register(entry.getKey(), () -> entry.getValue().extract(translation));
        }

        return new FormatterForLanguage(translatedFormatter);
    }

    private class FormatterForLanguage {

        private final Formatter translatedPlaceholders;

        private FormatterForLanguage(Formatter translatedPlaceholders) {
            this.translatedPlaceholders = translatedPlaceholders;
        }

        public String format(String text, Viewer viewer) {
            text = Notice.this.placeholderRegistry.format(text, viewer);
            text = this.translatedPlaceholders.format(text);

            for (Formatter formatter : Notice.this.formatters) {
                text = formatter.format(text);
            }

            return text;
        }

    }

}
