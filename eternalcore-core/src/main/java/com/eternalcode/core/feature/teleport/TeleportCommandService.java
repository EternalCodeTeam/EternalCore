package com.eternalcode.core.feature.teleport;

import com.eternalcode.core.feature.teleport.apiteleport.TeleportCommandSettings;
import com.eternalcode.core.feature.teleport.config.BukkitTeleportCommandSettings;
import com.eternalcode.core.feature.teleport.config.TeleportConfig;
import com.eternalcode.core.injector.annotations.Inject;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TeleportCommandService {

    private final TeleportConfig config;

    @Inject
    public TeleportCommandService(TeleportConfig config) {
        this.config = config;
    }

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

    public Duration getTeleportDelay(Player player) {
        return this.getTeleportDelay(player, "default");
    }

    public boolean hasInstantTeleport(Player player, String command) {
        return this.getTeleportDelay(player, command).isZero();
    }

    public TeleportCommandSettings getCommandSettings(String command) {
        return this.config.commands.getOrDefault(command.toLowerCase(), this.createDefaultCommandSettings(command));
    }

    private TeleportCommandSettings createDefaultCommandSettings(String command) {
        return new BukkitTeleportCommandSettings(true, new HashMap<>(), Duration.ofSeconds(5), "eternalcore."+ command +".bypass");
    }
}
