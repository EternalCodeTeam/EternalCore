package com.eternalcode.core.notice;

import com.eternalcode.core.notice.extractor.NoticeExtractor;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerProvider;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import panda.utilities.text.Formatter;

import javax.annotation.CheckReturnValue;
import java.util.UUID;

public class NoticeService {

    private final PlatformBroadcaster announcer;

    private final Scheduler scheduler;
    private final TranslationManager translationManager;
    private final ViewerProvider viewerProvider;
    private final PlaceholderRegistry placeholderRegistry;

    private NoticeService(PlatformBroadcaster announcer, Scheduler scheduler, TranslationManager translationManager, ViewerProvider viewerProvider, PlaceholderRegistry placeholderRegistry) {
        this.announcer = announcer;
        this.scheduler = scheduler;
        this.translationManager = translationManager;
        this.viewerProvider = viewerProvider;
        this.placeholderRegistry = placeholderRegistry;
    }

    @CheckReturnValue
    public NoticeBroadcast create() {
        return new NoticeBroadcastImpl(this.scheduler, this.translationManager, this.viewerProvider, this.announcer, this.placeholderRegistry);
    }

    public void player(UUID player, NoticeExtractor extractor, Formatter... formatters) {
        this.create()
            .player(player)
            .notice(extractor)
            .formatter(formatters)
            .send();
    }

    public void players(Iterable<UUID> players, NoticeExtractor extractor, Formatter... formatters) {
        this.create()
            .players(players)
            .notice(extractor)
            .formatter(formatters)
            .send();
    }

    public void user(User user, NoticeExtractor extractor, Formatter... formatters) {
        this.create()
            .user(user)
            .notice(extractor)
            .formatter(formatters)
            .send();
    }

    public void viewer(Viewer viewer, NoticeExtractor extractor, Formatter... formatters) {
        this.create()
            .viewer(viewer)
            .notice(extractor)
            .formatter(formatters)
            .send();
    }


    public void console(NoticeExtractor extractor, Formatter... formatters) {
        this.create()
            .console()
            .notice(extractor)
            .formatter(formatters)
            .send();
    }

    public void all(NoticeExtractor extractor, Formatter... formatters) {
        this.create()
            .all()
            .notice(extractor)
            .formatter(formatters)
            .send();
    }

    public static NoticeService adventure(
        AudienceProvider audienceProvider,
        ComponentSerializer<Component, Component, String> componentSerializer,
        Scheduler scheduler,
        ViewerProvider viewerProvider,
        TranslationManager translationManager,
        PlaceholderRegistry placeholderRegistry
    ) {
        PlatformBroadcasterAdventureImpl noticeAnnouncer = new PlatformBroadcasterAdventureImpl(audienceProvider, componentSerializer);

        return new NoticeService(noticeAnnouncer, scheduler, translationManager, viewerProvider, placeholderRegistry);
    }

}
