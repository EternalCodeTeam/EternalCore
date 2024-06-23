package com.eternalcode.core.feature.privatechat;

import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.multification.shared.Formatter;

import java.util.UUID;

class PrivateChatPresenter {

    private static final Placeholders<PrivateMessage> PLACEHOLDERS = Placeholders.<PrivateMessage>builder()
        .with("{MESSAGE}", PrivateMessage::message)
        .with("{TARGET}", privateMessage -> privateMessage.target().getName())
        .with("{SENDER}", privateMessage -> privateMessage.sender().getName())
        .build();

    private final NoticeService notice;

    public PrivateChatPresenter(NoticeService noticeService) {
        this.notice = noticeService;
    }

    void onPrivate(PrivateMessage event) {
        Formatter formatter = PLACEHOLDERS.toFormatter(event);
        UUID sender = event.sender().getUniqueId();
        UUID target = event.target().getUniqueId();

        if (!event.ignored()) {
            this.notice.player(target, translation -> translation.privateChat().privateMessageTargetToYou(), formatter);
        }

        this.notice.player(sender, translation -> translation.privateChat().privateMessageYouToTarget(), formatter);
        this.notice.players(event.spies(), translation -> translation.privateChat().socialSpyMessage(), formatter);
    }

}
