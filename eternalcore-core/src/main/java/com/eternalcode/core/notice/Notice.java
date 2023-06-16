package com.eternalcode.core.notice;


import org.bukkit.Sound;
import org.bukkit.SoundCategory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.eternalcode.core.notice.NoticeContent.*;

public class Notice {

    private final List<Part<?>> parts = new ArrayList<>();

    private Notice(List<Part<?>> parts) {
        this.parts.addAll(parts);
    }

    public List<Part<?>> parts() {
        return Collections.unmodifiableList(this.parts);
    }

    public static <T extends NoticeContent> Notice of(NoticeType<T> type, T content) {
        return Notice.builder()
            .with(new Part<>(type, content))
            .build();
    }

    public static Notice chat(String... messages) {
        return Notice.builder()
            .chat(messages)
            .build();
    }

    public static Notice chat(Collection<String> messages) {
        return Notice.builder()
            .chat(messages)
            .build();
    }

    public static Notice actionbar(String actionBar) {
        return Notice.builder()
            .actionBar(actionBar)
            .build();
    }

    public static Notice title(String title) {
        return Notice.builder()
            .title(title)
            .build();
    }

    public static Notice subtitle(String subtitle) {
        return Notice.builder()
            .subtitle(subtitle)
            .build();
    }

    public static Notice title(String title, String subtitle) {
        return Notice.builder()
            .title(title)
            .subtitle(subtitle)
            .build();
    }

    public static Notice title(String title, String subtitle, Duration fadeIn, Duration stay, Duration fadeOut) {
        return Notice.builder()
            .title(title)
            .subtitle(subtitle)
            .times(fadeIn, stay, fadeOut)
            .build();
    }

    public static Notice hideTitle() {
        return Notice.builder()
            .hideTitle()
            .build();
    }

    public static Notice sound(Sound sound, SoundCategory category, float volume, float pitch) {
        return Notice.builder()
            .sound(sound, category, pitch, volume)
            .build();
    }

    public static Notice sound(Sound sound, float volume, float pitch) {
        return Notice.builder()
            .sound(sound, pitch, volume)
            .build();
    }

    public static Notice empty() {
        return new Notice(Collections.emptyList());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<Part<?>> parts = new ArrayList<>();

        Builder with(Part<?> part) {
            this.parts.add(part);
            return this;
        }

        public Notice build() {
            return new Notice(this.parts);
        }

        public Builder chat(String... messages) {
            return this.with(NoticeType.CHAT.of(new Text(List.of(messages))));
        }

        public Builder chat(Collection<String> messages) {
            return this.with(NoticeType.CHAT.of(new Text(List.copyOf(messages))));
        }

        public Builder actionBar(String message) {
            return this.with(NoticeType.ACTION_BAR.of(new Text(List.of(message))));
        }

        public Builder title(String title) {
            return this.with(NoticeType.TITLE.of(new Text(List.of(title))));
        }

        public Builder title(String title, String subtitle) {
            return this.with(NoticeType.TITLE.of(new Text(List.of(title))))
                .with(NoticeType.SUBTITLE.of(new Text(List.of(subtitle))));
        }

        public Builder subtitle(String subtitle) {
            return this.with(NoticeType.SUBTITLE.of(new Text(List.of(subtitle))));
        }

        public Builder hideTitle() {
            return this.with(NoticeType.TITLE_HIDE.of(None.INSTANCE));
        }

        public Builder times(Duration in, Duration stay, Duration out) {
            return this.with(NoticeType.TITLE_TIMES.of(new Times(in, stay, out)));
        }

        public Builder sound(Sound sound, float pitch, float volume) {
            return this.with(NoticeType.SOUND.of(new Music(sound, null, pitch, volume)));
        }

        public Builder sound(Sound sound,  SoundCategory category,  float pitch, float volume) {
            return this.with(NoticeType.SOUND.of(new Music(sound, category, pitch, volume)));
        }

    }

    record Part<T extends NoticeContent>(NoticeType<T> type, T content) { }

}
