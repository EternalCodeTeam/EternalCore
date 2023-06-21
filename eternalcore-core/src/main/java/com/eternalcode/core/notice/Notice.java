package com.eternalcode.core.notice;


import org.bukkit.Sound;
import org.bukkit.SoundCategory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.eternalcode.core.notice.NoticeContent.Music;
import static com.eternalcode.core.notice.NoticeContent.None;
import static com.eternalcode.core.notice.NoticeContent.Text;
import static com.eternalcode.core.notice.NoticeContent.Times;

public class Notice {

    private final Map<NoticeType, Part<?>> parts = new LinkedHashMap<>();

    private Notice(Map<NoticeType, Part<?>> parts) {
        this.parts.putAll(parts);
    }

    public List<Part<?>> parts() {
        return this.parts.values().stream()
            .toList();
    }

    public static <T extends NoticeContent> Notice of(NoticeType type, T content) {
        return Notice.builder()
            .withPart(new Part<>(type, content))
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
        return new Notice(Collections.emptyMap());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<NoticeType, Part<?>> parts = new LinkedHashMap<>();

        Builder withPart(Part<?> part) {
            this.parts.put(part.type, part);
            return this;
        }

        public Notice build() {
            return new Notice(this.parts);
        }

        public Builder chat(String... messages) {
            return this.chat(List.of(messages));
        }

        public Builder chat(Collection<String> messages) {
            Part<?> removed = this.parts.remove(NoticeType.CHAT);
            List<String> newMessages = new ArrayList<>();

            if (removed != null && removed.content instanceof Text text) {
                newMessages.addAll(text.messages());
            }

            newMessages.addAll(messages);

            return this.withPart(new Part<>(NoticeType.CHAT, new Text(Collections.unmodifiableList(newMessages))));
        }

        public Builder actionBar(String message) {
            return this.withPart(new Part<>(NoticeType.ACTION_BAR, new Text(List.of(message))));
        }

        public Builder title(String title) {
            return this.withPart(new Part<>(NoticeType.TITLE, new Text(List.of(title))));
        }

        public Builder title(String title, String subtitle) {
            return this.withPart(new Part<>(NoticeType.TITLE, new Text(List.of(title))))
                .withPart(new Part<>(NoticeType.SUBTITLE, new Text(List.of(subtitle))));
        }

        public Builder subtitle(String subtitle) {
            return this.withPart(new Part<>(NoticeType.SUBTITLE, new Text(List.of(subtitle))));
        }

        public Builder hideTitle() {
            return this.withPart(new Part<>(NoticeType.TITLE_HIDE, None.INSTANCE));
        }

        public Builder times(Duration in, Duration stay, Duration out) {
            return this.withPart(new Part<>(NoticeType.TITLE_TIMES, new Times(in, stay, out)));
        }

        public Builder sound(Sound sound, float pitch, float volume) {
            return this.withPart(new Part<>(NoticeType.SOUND, new Music(sound, null, pitch, volume)));
        }

        public Builder sound(Sound sound, SoundCategory category, float pitch, float volume) {
            return this.withPart(new Part<>(NoticeType.SOUND, new Music(sound, category, pitch, volume)));
        }

    }

    record Part<T extends NoticeContent>(NoticeType type, T content) {
    }

}
