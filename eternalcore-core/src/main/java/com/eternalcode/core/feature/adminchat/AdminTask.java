package com.eternalcode.core.feature.adminchat;

import com.eternalcode.core.feature.adminchat.event.AdminChatService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.Server;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Task(period = 1, delay = 1, unit = TimeUnit.SECONDS)
class AdminTask implements Runnable {

    private final AdminChatService adminChatService;
    private final NoticeService noticeService;
    private final Server server;

    @Inject
    public AdminTask(AdminChatService adminChatService, NoticeService noticeService,  Server server) {
        this.adminChatService = adminChatService;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Override
    public void run() {
        for (UUID enabledChatPlayerUUID : this.adminChatService.getAdminChatEnabledPlayers()) {

            if (this.server.getPlayer(enabledChatPlayerUUID) == null) {
                continue;
            }

            this.noticeService.create()
                .notice(translation -> translation.adminChat().actionbarPersistentChatNotify())
                .player(enabledChatPlayerUUID)
                .send();
        }
    }
}
