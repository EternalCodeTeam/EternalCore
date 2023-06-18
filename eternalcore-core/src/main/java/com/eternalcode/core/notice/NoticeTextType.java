package com.eternalcode.core.notice;

public enum NoticeTextType {

    CHAT(NoticeType.CHAT),
    ACTION_BAR(NoticeType.ACTION_BAR),
    TITLE(NoticeType.TITLE),
    SUBTITLE(NoticeType.SUBTITLE);

    private final NoticeType type;

    NoticeTextType(NoticeType type) {
        this.type = type;
    }

    NoticeType getType() {
        return this.type;
    }

}
