package com.eternalcode.core.notice;

import com.eternalcode.multification.notice.NoticeKey;

public enum NoticeTextType {

    CHAT(NoticeKey.CHAT),
    ACTION_BAR(NoticeKey.ACTION_BAR),
    TITLE(NoticeKey.TITLE),
    SUBTITLE(NoticeKey.SUBTITLE);

    private final NoticeKey type;

    NoticeTextType(NoticeKey type) {
        this.type = type;
    }

    public NoticeKey getType() {
        return this.type;
    }
}
