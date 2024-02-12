package com.eternalcode.core.feature.jail;

import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class JailServiceImpl implements JailService {

    private Location jailLocation;
    private TeleportService teleportService;
    private SpawnService spawnService;

    private final Map<UUID, Prisoner> jailedPlayers = new ConcurrentHashMap<>();


    @Override
    public void setupJailArea(Location jailLocation) {
        this.jailLocation = jailLocation;
    }

    @Override
    public void detainPlayer(Player player, @Nullable String reason, @Nullable Player detainedBy) {
        if (isLocationSet()) {

        }

        JailDetainEvent jailDetainEvent = new JailDetainEvent(player, reason, detainedBy);

        if (jailDetainEvent.isCancelled()) {
            return;
        }

        this.jailedPlayers.put(player.getUniqueId(), new Prisoner(player.getUniqueId(), Instant.now(), reason, detainedBy.getUniqueId()));

        this.teleportService.teleport(player, this.jailLocation);
        // Detain the player
    }

    @Override
    public void releasePlayer(Player player) {

        JailReleaseEvent jailReleaseEvent = new JailReleaseEvent(player);

        if (jailReleaseEvent.isCancelled()) {
            return;
        }

        this.jailedPlayers.remove(player.getUniqueId());

        this.teleportService.teleport(player, spawnService.getSpawnLocation());
        // Release the player
    }

    @Override
    public boolean isLocationSet() {

        return this.jailLocation != null;
    }

}
