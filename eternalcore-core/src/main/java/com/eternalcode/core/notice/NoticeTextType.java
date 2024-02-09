package com.eternalcode.core.notice;

import com.eternalcode.multification.notice.NoticeType;

public enum NoticeTextType {

    CHAT(NoticeType.CHAT),
    ACTION_BAR(NoticeType.ACTION_BAR),
    TITLE(NoticeType.TITLE_WITH_EMPTY_SUBTITLE),
    SUBTITLE(NoticeType.SUBTITLE_WITH_EMPTY_TITLE);

    private final NoticeType type;

    NoticeTextType(NoticeType type) {
        this.type = type;
    }

    public NoticeType getType() {
        return this.type;
    }
}
