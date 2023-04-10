package com.eternalcode.core.feature.privatechat;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import panda.utilities.text.Formatter;

import java.util.UUID;

public class PrivateChatPresenter implements Subscriber {

    private static final Placeholders<PrivateMessage> PLACEHOLDERS = Placeholders.<PrivateMessage>builder()
        .with("{MESSAGE}", PrivateMessage::getMessage)
        .with("{TARGET}", privateMessage -> privateMessage.getTarget().getName())
        .with("{SENDER}", privateMessage -> privateMessage.getSender().getName())
        .build();

    private final NoticeService notice;

    public PrivateChatPresenter(NoticeService noticeService) {
        this.notice = noticeService;
    }

    @Subscribe
    void onPrivate(PrivateMessage event) {
        Formatter formatter = PLACEHOLDERS.toFormatter(event);
        UUID sender = event.getSender().getUniqueId();
        UUID target = event.getTarget().getUniqueId();

        if (!event.isIgnored()) {
            this.notice.player(target, translation -> translation.privateChat().privateMessageTargetToYou(), formatter);
        }

        this.notice.player(sender, translation -> translation.privateChat().privateMessageYouToTarget(), formatter);
        this.notice.players(event.getSpies(), translation -> translation.privateChat().socialSpyMessage(), formatter);
    }

}
