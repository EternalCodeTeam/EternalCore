package com.eternalcode.core.feature.jail;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import static com.eternalcode.core.feature.jail.JailServiceImpl.JAIL_TIME_UNITS;

@Task(period = 20, delay = 5)
public class JailTask implements Runnable {

    private final JailService jailService;
    private final Server server;
    private final NoticeService noticeService;


    @Inject
    public JailTask(JailService jailService, Server server, NoticeService noticeService) {
        this.jailService = jailService;
        this.server = server;
        this.noticeService = noticeService;
    }

    @Override
    public void run() {
        for (Prisoner prisoner : this.jailService.getJailedPlayers().values()) {

            Player player = this.server.getPlayer(prisoner.getUuid());

            if (player == null) {
                continue;
            }

            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailDetainCountdownActionbar())
                .placeholder("{TIME}", JAIL_TIME_UNITS.format(prisoner.getRemainingTime()))
                .player(prisoner.getUuid())
                .send();


            if (prisoner.isReleased()) {
                this.noticeService.create()
                    .notice(translation -> translation.jailSection().jailReleaseActionbar())
                    .player(prisoner.getUuid())
                    .send();

                this.jailService.releasePlayer(player, null);
            }
        }
    }
}
