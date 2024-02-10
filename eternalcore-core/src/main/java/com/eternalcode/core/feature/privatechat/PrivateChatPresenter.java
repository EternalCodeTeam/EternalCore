package com.eternalcode.core.feature.privatechat;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import panda.utilities.text.Formatter;

import java.util.UUID;

class PrivateChatPresenter {

    private static final Placeholders<PrivateMessage> PLACEHOLDERS = Placeholders.<PrivateMessage>builder()
        .with("{MESSAGE}", PrivateMessage::message)
        .with("{TARGET}", privateMessage -> privateMessage.target().getName())
        .with("{SENDER}", privateMessage -> privateMessage.sender().getName())
        .build();

    private final NoticeService notice;
    private final EventCaller eventCaller;

    public PrivateChatPresenter(NoticeService noticeService, EventCaller eventCaller) {
        this.notice = noticeService;
        this.eventCaller = eventCaller;
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

        this.eventCaller.callEvent(new PrivateChatEvent(sender, target, event.message()));
    }

}
