package com.eternalcode.core.feature.teleport;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeleportTaskService {

    private final Map<UUID, Teleport> teleports = new HashMap<>();

    public Teleport createTeleport(
        UUID uuid,
        Position startLocation,
        Position destinationLocation,
        TemporalAmount time) {
        Teleport teleport = new Teleport(uuid, startLocation, destinationLocation, time);

        this.teleports.put(uuid, teleport);

        return teleport;
    }

    public void removeTeleport(UUID uuid) {
        this.teleports.remove(uuid);
    }

    Optional<Teleport> findTeleport(UUID uuid) {
        return Optional.ofNullable(this.teleports.get(uuid));
    }

    public boolean isInTeleport(UUID uuid) {
        Optional<Teleport> teleportOption = this.findTeleport(uuid);

        if (teleportOption.isEmpty()) {
            return false;
        }

        Teleport teleport = teleportOption.get();

        if (Instant.now().isBefore(teleport.getTeleportMoment())) {
            return true;
        }

        this.removeTeleport(uuid);

        return false;
    }

    Collection<Teleport> getTeleports() {
        return Collections.unmodifiableCollection(this.teleports.values());
    }
}
