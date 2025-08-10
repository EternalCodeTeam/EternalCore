package com.eternalcode.core.feature.teleportrandomplayer;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.time.Instant;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@Service
public class TeleportRandomPlayerService {

    private final TeleportToRandomPlayerSettings randomPlayerSettings;
    private final Server server;

    private final Cache<HistoryKey, Instant> teleportationHistory = Caffeine.newBuilder()
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .build();

    @Inject
    public TeleportRandomPlayerService(
        TeleportToRandomPlayerSettings randomPlayerSettings,
        Server server
    ) {
        this.randomPlayerSettings = randomPlayerSettings;
        this.server = server;
    }

    @Nullable
    public Player findLeastRecentlyTeleportedPlayer(Player sender) {
        UUID senderId = sender.getUniqueId();
        return this.server.getOnlinePlayers().stream()
            .filter(target -> !target.equals(sender))
            .filter(target -> this.randomPlayerSettings.teleportToOpPlayers() || !target.isOp())
            .min(Comparator.comparing(target -> this.getTeleportationHistory(target, senderId)))
            .orElse(null);
    }

    @Nullable
    public Player findLeastRecentlyTeleportedPlayerByY(Player sender, int minY, int maxY) {
        UUID senderId = sender.getUniqueId();
        return this.server.getOnlinePlayers().stream()
            .filter(target -> !target.equals(sender))
            .filter(target -> this.pluginConfiguration.teleport.includeOpPlayersInRandomTeleport || !target.isOp())
            .filter(target -> this.isPlayerInYRange(target, minY, maxY))
            .min(Comparator.comparing(target -> this.getTeleportationHistory(target, senderId)))
            .orElse(null);
    }

    private boolean isPlayerInYRange(Player player, int minY, int maxY) {
        double playerY = player.getLocation().getY();
        return playerY >= minY && playerY <= maxY;
    }

    private Instant getTeleportationHistory(Player target, UUID senderId) {
        return this.teleportationHistory.get(new HistoryKey(senderId, target.getUniqueId()), key -> Instant.EPOCH);
    }

    public void updateTeleportationHistory(Player sender, Player target) {
        this.teleportationHistory.put(new HistoryKey(sender.getUniqueId(), target.getUniqueId()), Instant.now());
    }

    private record HistoryKey(UUID sender, UUID target) {
    }
}
