package com.eternalcode.core.feature.teleportrandomplayer;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

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

        List<Player> validTargets = this.server.getOnlinePlayers().stream()
            .filter(target -> !target.equals(sender))
            .filter(target -> this.randomPlayerSettings.teleportToOpPlayers() || !target.isOp())
            .collect(Collectors.toList());

        if (validTargets.isEmpty()) {
            return null;
        }

        return validTargets.stream()
            .min(Comparator.comparing(target -> this.getTeleportationHistory(target, senderId)))
            .orElse(null);
    }

    @Nullable
    public Player findLeastRecentlyTeleportedPlayerByY(Player sender, int minY, int maxY) {
        if (minY > maxY) {
            throw new IllegalArgumentException("MinY cannot be greater than maxY");
        }

        UUID senderId = sender.getUniqueId();

        List<Player> validTargets = this.server.getOnlinePlayers().stream()
            .filter(target -> !target.equals(sender))
            .filter(target -> this.randomPlayerSettings.teleportToOpPlayers() || !target.isOp())
            .filter(target -> this.isPlayerInYRange(target, minY, maxY))
            .collect(Collectors.toList());

        if (validTargets.isEmpty()) {
            return null;
        }

        return validTargets.stream()
            .min(Comparator.comparing(target -> this.getTeleportationHistory(target, senderId)))
            .orElse(null);
    }

    private boolean isPlayerInYRange(Player player, int minY, int maxY) {
        if (player == null) {
            return false;
        }
        else {
            player.getLocation();
        }

        double playerY = player.getLocation().getY();
        return playerY >= minY && playerY <= maxY;
    }

    private Instant getTeleportationHistory(Player target, UUID senderId) {
        if (target == null) {
            return Instant.EPOCH;
        }

        return this.teleportationHistory.get(
            new HistoryKey(senderId, target.getUniqueId()),
            key -> Instant.EPOCH
        );
    }

    public void updateTeleportationHistory(Player sender, Player target) {
        if (sender == null || target == null) {
            return;
        }

        this.teleportationHistory.put(
            new HistoryKey(sender.getUniqueId(), target.getUniqueId()),
            Instant.now()
        );
    }

    private record HistoryKey(UUID sender, UUID target) {
        public HistoryKey {
            if (sender == null || target == null) {
                throw new IllegalArgumentException("Sender and target UUIDs cannot be null");
            }
        }
    }
}
