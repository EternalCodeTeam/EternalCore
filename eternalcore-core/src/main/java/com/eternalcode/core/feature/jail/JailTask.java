package com.eternalcode.core.feature.jail;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Task(period = 20, delay = 5)
public class JailTask implements Runnable {

    private final PrisonerService prisonerService;
    private final Server server;
    private final NoticeService noticeService;

    @Inject
    public JailTask(PrisonerService prisonerService, Server server, NoticeService noticeService) {
        this.prisonerService = prisonerService;
        this.server = server;
        this.noticeService = noticeService;
    }

    @Override
    public void run() {
        for (Prisoner prisoner : this.prisonerService.getCollectionPrisoners()) {
            Player player = this.server.getPlayer(prisoner.getPlayerUniqueId());

            if (player == null) {
                continue;
            }

            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailDetainCountdownActionbar())
                .placeholder("{TIME}", DurationUtil.format(prisoner.getRemainingTime()))
                .player(prisoner.getPlayerUniqueId())
                .send();

            if (prisoner.isReleased()) {
                this.noticeService.create()
                    .notice(translation -> translation.jailSection().jailReleaseActionbar())
                    .player(prisoner.getPlayerUniqueId())
                    .send();

                this.prisonerService.releasePlayer(player, null);
            }
        }
    }
}
