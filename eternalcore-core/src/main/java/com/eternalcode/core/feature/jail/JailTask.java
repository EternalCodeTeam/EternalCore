package com.eternalcode.core.feature.jail;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

import java.util.concurrent.TimeUnit;

import org.bukkit.Server;
import org.bukkit.entity.Player;

@Task(period = 30, delay = 30, unit = TimeUnit.SECONDS)
class JailTask implements Runnable {

    private final JailService jailService;
    private final Server server;
    private final NoticeService noticeService;

    @Inject
    JailTask(JailService jailService, Server server, NoticeService noticeService) {
        this.jailService = jailService;
        this.server = server;
        this.noticeService = noticeService;
    }

    @Override
    public void run() {
        for (JailedPlayer jailedPlayer : this.jailService.getJailedPlayers()) {
            Player player = this.server.getPlayer(jailedPlayer.playerUniqueId());

            if (player == null) {
                continue;
            }

            if (jailedPlayer.isPrisonExpired()) {
                this.noticeService.create()
                    .notice(translation -> translation.jail().released())
                    .player(jailedPlayer.playerUniqueId())
                    .send();

                this.jailService.releasePlayer(player);
                continue;
            }

            this.noticeService.create()
                .notice(translation -> translation.jail().detainCountdown())
                .placeholder("{REMAINING_TIME}", DurationUtil.format(jailedPlayer.remainingTime(), true))
                .player(jailedPlayer.playerUniqueId())
                .send();
        }
    }
}
