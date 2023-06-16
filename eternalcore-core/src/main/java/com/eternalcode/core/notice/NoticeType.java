package com.eternalcode.core.notice;

import java.util.List;

import static com.eternalcode.core.notice.NoticeContent.None;
import static com.eternalcode.core.notice.NoticeContent.Music;
import static com.eternalcode.core.notice.NoticeContent.Text;
import static com.eternalcode.core.notice.NoticeContent.Times;

class NoticeType<T extends NoticeContent> {

    static final NoticeType<Text> CHAT = of(Text.class, "chat");
    static final NoticeType<Text> ACTION_BAR = of(Text.class, "actionbar");
    static final NoticeType<Text> TITLE = of(Text.class, "title");
    static final NoticeType<Text> SUBTITLE = of(Text.class, "subtitle");
    static final NoticeType<Times> TITLE_TIMES = of(Times.class, "times");
    static final NoticeType<None> TITLE_HIDE = of(None.class, "titleHide");
    static final NoticeType<Music> SOUND = of(Music.class, "sound");

    public static final List<NoticeType<?>> VALUES = List.of(
        CHAT,
        ACTION_BAR,
        TITLE,
        SUBTITLE,
        TITLE_TIMES,
        TITLE_HIDE,
        SOUND
    );

    private final Class<T> inputType;
    private final String name;

    NoticeType(Class<T> inputType, String name) {
        this.inputType = inputType;
        this.name = name;
    }

    String name() {
        return this.name;
    }

    Class<T> contentType() {
        return this.inputType;
    }

    Notice.Part<T> of(T input) {
        return new Notice.Part<>(this, input);
    }

    static <T extends NoticeContent> NoticeType<T> of(Class<T> inputType, String name) {
        return new NoticeType<>(inputType, name);
    }

    static NoticeType<?> of(String name) {
        return VALUES.stream()
            .filter(type -> type.name().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown notice type: " + name));
    }

    @Override
    public String toString() {
        return this.name;
    }

}
