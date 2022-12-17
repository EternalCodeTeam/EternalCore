package com.eternalcode.core.afk;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;

import java.util.Collections;

public class AfkMessagesController implements Subscriber {

    private final NoticeService noticeService;
    private final UserManager userManager;

    public AfkMessagesController(NoticeService noticeService, UserManager userManager) {
        this.noticeService = noticeService;
        this.userManager = userManager;
    }

    @Subscribe
    void onAfkChange(AfkChangeEvent event) {
        this.noticeService.create()
            .onlinePlayers()
            .player(event.getPlayer())
            .notice(messages -> event.isAfk() ? messages.afk().afkOn() : messages.afk().afkOff())
            .placeholder("{player}", userManager.getUser(event.getPlayer()).map(User::getName))
            .send();
    }

}
