package com.eternalcode.core.feature.essentials.tellraw;

import com.eternalcode.core.notice.NoticeTextType;
import lombok.Getter;

@Getter
public class TellRawNotice {

    private NoticeTextType noticeTextType;
    private String noticeText;

    public TellRawNotice(NoticeTextType noticeTextType, String noticeText) {
        this.noticeTextType = noticeTextType;
        this.noticeText = noticeText;
    }
}
