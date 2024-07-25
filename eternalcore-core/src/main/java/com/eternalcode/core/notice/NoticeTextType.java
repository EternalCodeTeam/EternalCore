package com.eternalcode.core.notice;

import com.eternalcode.multification.notice.NoticeKey;
import com.eternalcode.multification.notice.resolver.NoticeContent;
import com.eternalcode.multification.notice.resolver.actionbar.ActionbarContent;
import com.eternalcode.multification.notice.resolver.chat.ChatContent;
import com.eternalcode.multification.notice.resolver.title.TitleContent;

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
        // Here be very careful since class cast exceptions may occur at runtime
        return (NoticeKey<T>) this.noticeKey;
    }
}
