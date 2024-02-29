package com.eternalcode.core.feature.jail;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Task(period = 20 * 60 * 5, delay = 20 * 60 * 5)
public class JailTask implements Runnable {

    private final JailService jailService;
    private final Server server;

    @Inject
    public JailTask(JailService jailService, Server server) {
        this.jailService = jailService;
        this.server = server;
    }

    @Override
    public void run() {
        for (Prisoner prisoner : this.jailService.getJailedPlayers().values()) {

            Player player = this.server.getPlayer(prisoner.getUuid());

            if (player == null) {
                continue;
            }

            if (prisoner.isReleased()) {
                this.jailService.releasePlayer(player, null);
            }
        }
    }
}
