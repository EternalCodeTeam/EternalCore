package com.eternalcode.core.notice;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.notice.extractor.NoticeExtractor;
import com.eternalcode.core.notice.extractor.OptionalNoticeExtractor;
import com.eternalcode.core.notice.extractor.TranslatedMessageExtractor;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerProvider;
import panda.std.Option;
import panda.utilities.text.Formatter;
import panda.utilities.text.Joiner;

import javax.annotation.CheckReturnValue;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

class NoticeBroadcastImpl implements NoticeBroadcast {

    private final Scheduler scheduler;
    private final TranslationManager translationManager;
    private final ViewerProvider viewerProvider;
    private final PlatformBroadcaster announcer;
    private final PlaceholderRegistry placeholderRegistry;

    private final List<Viewer> viewers = new ArrayList<>();
    private final List<NoticeExtractor> notifications = new ArrayList<>();

    private final Map<String, TranslatedMessageExtractor> placeholders = new HashMap<>();
    private final List<Formatter> formatters = new ArrayList<>();

    NoticeBroadcastImpl(Scheduler scheduler, TranslationManager translationManager, ViewerProvider viewerProvider, PlatformBroadcaster announcer, PlaceholderRegistry placeholderRegistry) {
        this.scheduler = scheduler;
        this.translationManager = translationManager;
        this.viewerProvider = viewerProvider;
        this.announcer = announcer;
        this.placeholderRegistry = placeholderRegistry;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl user(User user) {
        this.viewers.add(user);
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl player(UUID player) {
        this.viewers.add(this.viewerProvider.player(player));
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl players(Iterable<UUID> players) {
        Set<Viewer> viewers = new HashSet<>();

        for (UUID player : players) {
            viewers.add(this.viewerProvider.player(player));
        }

        this.viewers.addAll(viewers);
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl viewer(Viewer viewer) {
        this.viewers.add(viewer);
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl console() {
        this.viewers.add(this.viewerProvider.console());
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl all() {
        this.viewers.addAll(this.viewerProvider.all());
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl onlinePlayers() {
        this.viewers.addAll(this.viewerProvider.onlinePlayers());
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl message(TranslatedMessageExtractor extractor) {
        this.notifications.add(translation -> Notice.chat(extractor.extract(translation)));
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl messages(Function<Translation, List<String>> function) {
        TranslatedMessageExtractor translatedMessageExtractor = translation -> {
            List<String> apply = function.apply(translation);

            return Joiner.on("\n").join(apply).toString();
        };

        return this.message(translatedMessageExtractor);
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl staticNotice(Notice notification) {
        this.notifications.add(translation -> notification);

        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl noticeOption(OptionalNoticeExtractor extractor) {
        this.notifications.add((NoticeExtractor) translation -> {
            Option<Notice> apply = extractor.extract(translation);

            if (apply.isPresent()) {
                return apply.get();
            }

            return Notice.none();
        });
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl notice(NoticeExtractor extractor) {
        this.notifications.add(extractor);
        return this;
    }

    @Override
    public NoticeBroadcast notice(NoticeTextType type, String... text) {
        this.notifications.add(translation -> Notice.chat(text));
        return this;
    }

    @Override
    public NoticeBroadcast notice(NoticeTextType type, Collection<String> text) {
        this.notifications.add(translation -> Notice.chat(text));
        return this;
    }

    @Override
    public NoticeBroadcast notice(NoticeTextType type, TranslatedMessageExtractor extractor) {
        this.notifications.add(translation -> {
            List<String> list = Collections.singletonList(extractor.extract(translation));
            NoticeContent.Text content = new NoticeContent.Text(list);

            return Notice.of(type.getType(), content);
        });

        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl placeholder(String from, String to) {
        this.placeholders.put(from, translation -> to);
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl placeholder(String from, Option<String> to) {
        if (to.isPresent()) {
            this.placeholders.put(from, translation -> to.get());
        }

        return this;
    }


    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl placeholder(String from, Optional<String> to) {
        if (to.isPresent()) {
            this.placeholders.put(from, translation -> to.get());
        }

        return this;
    }


    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl placeholder(String from, Supplier<String> to) {
        this.placeholders.put(from, translation -> to.get());
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl placeholder(String from, TranslatedMessageExtractor extractor) {
        this.placeholders.put(from, extractor);
        return this;
    }

    @Override
    @CheckReturnValue
    public <T> NoticeBroadcastImpl placeholder(Placeholders<T> placeholder, T context) {
        this.formatters.add(placeholder.toFormatter(context));
        return this;
    }

    @Override
    @CheckReturnValue
    public NoticeBroadcastImpl formatter(Formatter... formatters) {
        this.formatters.addAll(Arrays.asList(formatters));
        return this;
    }

    @Override
    public void sendAsync() {
        this.scheduler.async(this::send);
    }

    @Override
    public void send() {
        LanguageViewersIndex viewersIndex = LanguageViewersIndex.of(this.viewers);
        TranslatedNoticesIndex translatedNoticesIndex = this.prepareTranslatedNotices(viewersIndex.getLanguages());

        this.sendTranslatedMessages(viewersIndex, translatedNoticesIndex);
    }

    private void sendTranslatedMessages(LanguageViewersIndex viewersIndex, TranslatedNoticesIndex translatedNoticesIndex) {
        for (Language language : viewersIndex.getLanguages()) {
            List<Notice> notificationsForLang = translatedNoticesIndex.forLanguage(language);

            if (notificationsForLang == null) {
                continue;
            }

            TranslatedFormatter translatedFormatter = this.prepareFormatterForLanguage(language);

            for (Notice notice : notificationsForLang) {
                Set<Viewer> languageViewers = viewersIndex.getViewers(language);

                for (Viewer viewer : languageViewers) {
                    for (Notice.Part<?> part : notice.parts()) {
                        part = this.formatter(part, message -> translatedFormatter.format(message, viewer));

                        this.announcer.announce(viewer, part);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends NoticeContent> Notice.Part<T> formatter(Notice.Part<T> part, UnaryOperator<String> function) {
        if (part.input() instanceof NoticeContent.Text text) {
            List<String> messages = text.messages().stream()
                .map(function)
                .toList();

            NoticeContent.Text context = new NoticeContent.Text(messages);

            part = new Notice.Part<>(part.type(), (T) context);
        }

        return part;
    }

    private TranslatedNoticesIndex prepareTranslatedNotices(Set<Language> languages) {
        return TranslatedNoticesIndex.of(languages, language -> {
            Translation translation = this.translationManager.getMessages(language);
            List<Notice> notificationsForLanguage = new ArrayList<>();

            for (NoticeExtractor extractor : this.notifications) {
                notificationsForLanguage.add(extractor.extract(translation));
            }

            return notificationsForLanguage;
        });
    }

    private TranslatedFormatter prepareFormatterForLanguage(Language language) {
        Translation translation = this.translationManager.getMessages(language);
        Formatter translatedFormatter = new Formatter();

        for (Map.Entry<String, TranslatedMessageExtractor> entry : this.placeholders.entrySet()) {
            translatedFormatter.register(entry.getKey(), () -> entry.getValue().extract(translation));
        }

        return new TranslatedFormatter(translatedFormatter);
    }

    private class TranslatedFormatter {

        private final Formatter translatedPlaceholders;

        private TranslatedFormatter(Formatter translatedPlaceholders) {
            this.translatedPlaceholders = translatedPlaceholders;
        }

        public String format(String text, Viewer viewer) {
            text = NoticeBroadcastImpl.this.placeholderRegistry.format(text, viewer);
            text = this.translatedPlaceholders.format(text);

            for (Formatter formatter : NoticeBroadcastImpl.this.formatters) {
                text = formatter.format(text);
            }

            return text;
        }

    }

}
