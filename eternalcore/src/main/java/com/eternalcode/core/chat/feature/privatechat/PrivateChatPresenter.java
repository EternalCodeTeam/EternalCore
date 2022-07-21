package com.eternalcode.core.chat.feature.privatechat;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.placeholder.Placeholders;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import panda.utilities.text.Formatter;

import java.util.UUID;

public class PrivateChatPresenter implements Subscriber {

    private final static Placeholders<PrivateMessage> PLACEHOLDERS = Placeholders.<PrivateMessage>builder()
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
            this.notice.player(target, messages -> messages.privateMessage().privateMessageTargetToYou(), formatter);
        }

        this.notice.player(sender, messages -> messages.privateMessage().privateMessageYouToTarget(), formatter);
        this.notice.players(event.getSpy(), messages -> messages.privateMessage().socialSpyMessage(), formatter);
    }

}
