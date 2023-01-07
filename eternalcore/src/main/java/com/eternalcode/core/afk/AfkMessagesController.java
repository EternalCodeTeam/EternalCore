package com.eternalcode.core.afk;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;

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
            .notice(translation -> event.isAfk() ? translation.afk().afkOn() : translation.afk().afkOff())
            .placeholder("{PLAYER}", this.userManager.getUser(event.getPlayer()).map(User::getName))
            .send();
    }

}
