package com.eternalcode.core.notice;

import static com.eternalcode.core.notice.NoticeContent.Music;
import static com.eternalcode.core.notice.NoticeContent.None;
import static com.eternalcode.core.notice.NoticeContent.Text;
import static com.eternalcode.core.notice.NoticeContent.Times;

enum NoticeType {
    CHAT(Text.class, "chat"),
    ACTION_BAR(Text.class, "actionbar"),
    BOTH_TITLE(NoticeContent.Title.class, "bothTitle"),
    TITLE(Text.class, "title"),
    SUBTITLE(Text.class, "subtitle"),
    TITLE_TIMES(Times.class, "times"),
    TITLE_HIDE(None.class, "titleHide"),
    SOUND(Music.class, "sound");

    private final Class<?> inputType;
    private final String name;

    NoticeType(Class<?> inputType, String name) {
        this.inputType = inputType;
        this.name = name;
    }

    String getKey() {
        return this.name;
    }

    Class<?> contentType() {
        return this.inputType;
    }

    static NoticeType fromKey(String key) {
        for (NoticeType type : values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown notice type: " + key);
    }

}
