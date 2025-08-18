package com.eternalcode.core.feature.teleport;

import com.eternalcode.core.feature.teleport.config.BukkitTeleportCommandSettings;
import com.eternalcode.core.feature.teleport.config.TeleportConfig;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class BukkitTeleportCommandService implements TeleportCommandService {

    private final TeleportConfig config;

    @Inject
    public BukkitTeleportCommandService(TeleportConfig config) {
        this.config = config;
    }

    @Override
    public Duration getTeleportDelay(Player player, String command) {
        TeleportCommandSettings settings = this.getCommandSettings(command.toLowerCase());

        if (!settings.useDelay()) {
            return Duration.ZERO;
        }

        if (player.hasPermission(settings.bypassPermission())) {
            return Duration.ZERO;
        }

        Map<String, Duration> delayTiers = settings.delayTiers();
        for (Map.Entry<String, Duration> tier : delayTiers.entrySet()) {
            if (player.hasPermission(tier.getKey())) {
                return tier.getValue();
            }
        }

        return settings.defaultDelay();
    }

    @Override
    public Duration getTeleportDelay(Player player) {
        return this.getTeleportDelay(player, "default");
    }

    @Override
    public boolean hasInstantTeleport(Player player, String command) {
        return this.getTeleportDelay(player, command).isZero();
    }

    @Override
    public TeleportCommandSettings getCommandSettings(String command) {
        return this.config.commands.computeIfAbsent(
            command.toLowerCase(),
            cmd -> new BukkitTeleportCommandSettings(true, new HashMap<>(), Duration.ofSeconds(5), "eternalcore." + cmd + ".bypass")
        );
    }
}
