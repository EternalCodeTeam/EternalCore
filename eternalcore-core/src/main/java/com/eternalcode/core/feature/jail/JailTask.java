package com.eternalcode.core.feature.jail;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import java.util.concurrent.TimeUnit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Task(period = 1, delay = 1, unit = TimeUnit.SECONDS)
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
            Player player = this.server.getPlayer(jailedPlayer.getPlayerUniqueId());

            if (player == null) {
                continue;
            }

            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailDetainCountdown())
                .placeholder("{TIME}", DurationUtil.format(jailedPlayer.getRemainingTime()))
                .player(jailedPlayer.getPlayerUniqueId())
                .send();

            if (jailedPlayer.isPrisonExpired()) {
                this.noticeService.create()
                    .notice(translation -> translation.jailSection().jailReleasePrivate())
                    .player(jailedPlayer.getPlayerUniqueId())
                    .send();

                this.jailService.releasePlayer(player);
            }
        }
    }
}
