package com.eternalcode.core.feature.punishment;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Disconnects online players on the main thread. Safe to call from any thread.
 */
@Service
public class PlayerKicker {

    private final Server server;
    private final Scheduler scheduler;

    @Inject
    PlayerKicker(Server server, Scheduler scheduler) {
        this.server = server;
        this.scheduler = scheduler;
    }

    public void kick(UUID targetUuid, List<Component> message) {
        this.scheduler.run(() -> {
            Player player = this.server.getPlayer(targetUuid);

            if (player != null) {
                player.kick(this.join(message));
            }
        });
    }

    public void kickAllOnIp(String ip, List<Component> message) {
        this.scheduler.run(() -> {
            Component joined = this.join(message);

            for (Player player : this.server.getOnlinePlayers()) {
                if (player.getAddress() != null && ip.equals(player.getAddress().getAddress().getHostAddress())) {
                    player.kick(joined);
                }
            }
        });
    }

    private Component join(List<Component> message) {
        return Component.join(JoinConfiguration.newlines(), message);
    }
}
