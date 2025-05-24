package com.eternalcode.core.feature.teleportrandomplayer;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Comparator;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

@Service
public class TeleportRandomPlayerService {

    private final Server server;
    private final PluginConfiguration pluginConfiguration;

    private final Cache<HistoryKey, Instant> teleportationHistory = Caffeine.newBuilder()
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .build();

    @Inject
    public TeleportRandomPlayerService(Server server, PluginConfiguration pluginConfiguration) {
        this.server = server;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Nullable
    public Player findLeastRecentlyTeleportedPlayer(Player sender) {
        UUID senderId = sender.getUniqueId();
        return this.server.getOnlinePlayers().stream()
            .filter(target -> !target.equals(sender))
            .filter(target -> this.pluginConfiguration.teleport.includeOpPlayersInRandomTeleport || !target.isOp())
            .min(Comparator.comparing(target -> getTeleportationHistory(target, senderId)))
            .orElse(null);
    }

    private Instant getTeleportationHistory(Player target, UUID senderId) {
        return teleportationHistory.get(new HistoryKey(senderId, target.getUniqueId()), key -> Instant.EPOCH);
    }

    public void updateTeleportationHistory(Player sender, Player target) {
        teleportationHistory.put(new HistoryKey(sender.getUniqueId(), target.getUniqueId()), Instant.now());
    }

    private record HistoryKey(UUID sender, UUID target) {
    }

} 
