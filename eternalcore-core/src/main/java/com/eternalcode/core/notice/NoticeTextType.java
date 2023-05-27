package com.eternalcode.core.notice;

public enum NoticeTextType {

    CHAT(NoticeType.CHAT),
    ACTION_BAR(NoticeType.ACTION_BAR),
    TITLE(NoticeType.TITLE),
    SUBTITLE(NoticeType.SUBTITLE);

    private final NoticeType<NoticeContent.Text> type;

    NoticeTextType(NoticeType<NoticeContent.Text> type) {
        this.type = type;
    }

    NoticeType<NoticeContent.Text> getType() {
        return type;
    }

}
