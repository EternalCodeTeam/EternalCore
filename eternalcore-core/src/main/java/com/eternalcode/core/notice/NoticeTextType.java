package com.eternalcode.core.notice;

import com.eternalcode.multification.notice.NoticeKey;
import com.eternalcode.multification.notice.resolver.NoticeContent;

public enum NoticeTextType {
    CHAT(NoticeKey.CHAT),
    ACTIONBAR(NoticeKey.ACTION_BAR),
    TITLE(NoticeKey.TITLE),
    SUBTITLE(NoticeKey.SUBTITLE);

    private final NoticeKey<? extends NoticeContent> noticeKey;

    NoticeTextType(NoticeKey<? extends NoticeContent> noticeKey) {
        this.noticeKey = noticeKey;
    }

    public <T extends NoticeContent> NoticeKey<T> getNoticeKey() {
        return (NoticeKey<T>) this.noticeKey;
    }
}
