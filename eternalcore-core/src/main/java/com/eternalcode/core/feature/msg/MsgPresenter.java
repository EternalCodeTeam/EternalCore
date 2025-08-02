package com.eternalcode.core.feature.msg;

import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.multification.shared.Formatter;

import java.util.UUID;

class MsgPresenter {

    private static final Placeholders<Message> PLACEHOLDERS = Placeholders.<Message>builder()
        .with("{MESSAGE}", Message::message)
        .with("{TARGET}", privateMessage -> privateMessage.target().getName())
        .with("{SENDER}", privateMessage -> privateMessage.sender().getName())
        .build();

    private final NoticeService notice;

    public MsgPresenter(NoticeService noticeService) {
        this.notice = noticeService;
    }

    void onPrivate(Message event) {
        Formatter formatter = PLACEHOLDERS.toFormatter(event);
        UUID sender = event.sender().getUniqueId();
        UUID target = event.target().getUniqueId();

        if (!event.ignored()) {
            this.notice.player(target, translation -> translation.msg().msgTargetToYou(), formatter);
        }

        this.notice.player(sender, translation -> translation.msg().msgYouToTarget(), formatter);
        this.notice.players(event.spies(), translation -> translation.msg().socialSpyMessage(), formatter);
    }

}
