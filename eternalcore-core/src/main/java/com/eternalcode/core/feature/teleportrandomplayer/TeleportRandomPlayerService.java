package com.eternalcode.core.feature.teleportrandomplayer;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeleportRandomPlayerService {

    private final Server server;
    private final PluginConfiguration pluginConfiguration;

    private static final Map<UUID, Instant> TELEPORTATION_HISTORY = new HashMap<>();

    @Inject
    public TeleportRandomPlayerService(
        Server server,
        PluginConfiguration pluginConfiguration
    ) {
        this.server = server;
        this.pluginConfiguration = pluginConfiguration;
    }

    public List<Player> getEligiblePlayers() {
        return this.server.getOnlinePlayers().stream()
            .filter(target -> this.pluginConfiguration.teleport.includeOpPlayersInRandomTeleport || !target.isOp())
            .collect(Collectors.toList());
    }

    public Player findLeastRecentlyTeleportedPlayer() {
        List<Player> eligiblePlayers = getEligiblePlayers();
        
        if (eligiblePlayers.isEmpty()) {
            return null;
        }

        if (eligiblePlayers.size() == 1) {
            return eligiblePlayers.get(0);
        }

        return eligiblePlayers.stream()
            .min(Comparator.comparing(
                p -> TELEPORTATION_HISTORY.getOrDefault(p.getUniqueId(), Instant.EPOCH),
                Comparator.nullsLast(Instant::compareTo)
            ))
            .orElse(null);
    }

    public void updateTeleportationHistory(Player player) {
        TELEPORTATION_HISTORY.put(player.getUniqueId(), Instant.now());
    }
} 
