package com.eternalcode.core.feature.adminchat;

import com.eternalcode.commons.bukkit.runnable.OnlinePlayersRunnable;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.notice.NoticeService;
import java.util.concurrent.TimeUnit;
import org.bukkit.entity.Player;

@Task(period = 1, delay = 1, unit = TimeUnit.SECONDS)
final class AdminChatReminderTask extends OnlinePlayersRunnable {

    private final AdminChatService adminChatService;
    private final NoticeService noticeService;

    @Inject
    public AdminChatReminderTask(
        AdminChatService adminChatService,
        NoticeService noticeService
    ) {
        this.adminChatService = adminChatService;
        this.noticeService = noticeService;
    }

    @Override
    public void runFor(Player player) {
        if (!this.adminChatService.hasEnabledChat(player.getUniqueId())) {
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.adminChat().enabledReminder())
            .player(player.getUniqueId())
            .send();
    }
}
