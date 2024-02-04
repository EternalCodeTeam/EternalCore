package com.eternalcode.core.notice;

import com.eternalcode.multification.notice.NoticeType;

public enum NoticeTextType {

    CHAT(NoticeType.CHAT),
    ACTION_BAR(NoticeType.ACTION_BAR),
    TITLE(NoticeType.TITLE),
    SUBTITLE(NoticeType.SUBTITLE);

    private final NoticeType type;

    NoticeTextType(NoticeType type) {
        this.type = type;
    }

    public NoticeType getType() {
        return this.type;
    }
}
