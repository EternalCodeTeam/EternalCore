package com.eternalcode.core.notice;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.viewer.Viewer;
import com.google.common.collect.ImmutableBiMap;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.Sound.Source;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;

import java.util.Map;
import java.util.function.BiConsumer;

@Service
class PlatformBroadcasterAdventureImpl implements PlatformBroadcaster {

    private final Map<NoticeType, NoticePartAnnouncer<?>> announcers = new ImmutableBiMap.Builder<NoticeType, NoticePartAnnouncer<?>>()
        .put(NoticeType.CHAT,       this.text((audience, message) -> audience.sendMessage(message)))
        .put(NoticeType.ACTION_BAR, this.text((audience, message) -> audience.sendActionBar(message)))
        .put(NoticeType.TITLE,      this.text((audience, title) -> audience.sendTitlePart(TitlePart.TITLE, title)))
        .put(NoticeType.SUBTITLE,   this.text((audience, subtitle) -> audience.sendTitlePart(TitlePart.SUBTITLE, subtitle)))
        .put(NoticeType.TITLE_TIMES, new TimesNoticePartAnnouncer())
        .put(NoticeType.TITLE_HIDE, (viewer, audience, input) -> audience.clearTitle())
        .put(NoticeType.SOUND, new SoundNoticePartAnnouncer())
        .build();

    private final AudienceProvider audienceProvider;
    private final ComponentSerializer<Component, Component, String> componentSerializer;

    @Inject
    PlatformBroadcasterAdventureImpl(AudienceProvider audienceProvider, ComponentSerializer<Component, Component, String> componentSerializer) {
        this.audienceProvider = audienceProvider;
        this.componentSerializer = componentSerializer;
    }

    @Override
    public void announce(Viewer viewer, Notice notice) {
        for (Notice.Part<?> part : notice.parts()) {
            Audience audience = viewer.isConsole()
                ? this.audienceProvider.console()
                : this.audienceProvider.player(viewer.getUniqueId());

            this.announce(viewer, audience, part);
        }
    }

    @Override
    public void announce(Viewer viewer, Notice.Part<?> part) {
        Audience audience = viewer.isConsole()
            ? this.audienceProvider.console()
            : this.audienceProvider.player(viewer.getUniqueId());

        this.announce(viewer, audience, part);
    }

    @SuppressWarnings("unchecked")
    private <T extends NoticeContent> void announce(Viewer viewer, Audience audience, Notice.Part<T> part) {
        NoticePartAnnouncer<T> announcer = (NoticePartAnnouncer<T>) this.announcers.get(part.type());

        if (announcer == null) {
            throw new IllegalStateException("No announcer for " + part.type());
        }

        announcer.announce(viewer, audience, part.content());
    }

    interface NoticePartAnnouncer<T extends NoticeContent> {
        void announce(Viewer viewer, Audience audience, T input);
    }

    private NoticePartAnnouncer<NoticeContent.Text> text(BiConsumer<Audience, Component> consumer) {
        return (viewer, audience, input) -> {
            for (String text : input.messages()) {
                consumer.accept(audience, this.componentSerializer.deserialize(text));
            }
        };
    }

    static class TimesNoticePartAnnouncer implements NoticePartAnnouncer<NoticeContent.Times> {
        @Override
        public void announce(Viewer viewer, Audience audience, NoticeContent.Times timed) {
            Title.Times times = Title.Times.times(
                timed.fadeIn(),
                timed.stay(),
                timed.fadeOut()
            );

            audience.sendTitlePart(TitlePart.TIMES, times);
        }
    }

    static class SoundNoticePartAnnouncer implements NoticePartAnnouncer<NoticeContent.Music> {
        @Override
        public void announce(Viewer viewer, Audience audience, NoticeContent.Music music) {
            String soundKey = music.sound().getKey().getKey();
            Sound sound = music.category() != null
                ? Sound.sound(Key.key(soundKey), Source.valueOf(music.category().name()), music.volume(), music.pitch())
                : Sound.sound(Key.key(soundKey), Source.MASTER, music.volume(), music.pitch());

            audience.playSound(sound);
        }
    }

}
