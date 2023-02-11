package com.eternalcode.core.teleport;

import com.eternalcode.core.shared.Position;
import panda.std.Option;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportTaskService {

    private final Map<UUID, Teleport> teleportMap = new HashMap<>();

    public void createTeleport(UUID uuid, Position startLocation, Position destinationLocation, TemporalAmount time) {
        Teleport teleport = new Teleport(uuid, startLocation, destinationLocation, time);

        this.teleportMap.put(uuid, teleport);
    }

    public void removeTeleport(UUID uuid) {
        this.teleportMap.remove(uuid);
    }

    public Option<Teleport> findTeleport(UUID uuid) {
        return Option.of(this.teleportMap.get(uuid));
    }

    public boolean inTeleport(UUID uuid) {
        Option<Teleport> teleportOption = this.findTeleport(uuid);

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

    public Collection<Teleport> getTeleports() {
        return Collections.unmodifiableCollection(this.teleportMap.values());
    }

}
