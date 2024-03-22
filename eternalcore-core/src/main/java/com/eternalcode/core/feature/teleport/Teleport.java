package com.eternalcode.core.feature.teleport;

import com.eternalcode.commons.bukkit.position.Position;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Teleport {
    private final UUID playerUniqueId;
    private final Position startLocation;
    private final Position destinationLocation;
    private final Instant teleportMoment;
    private final CompletableFuture<TeleportResult> result;

    public Teleport(UUID playerUniqueId, Position startLocation, Position destinationLocation, TemporalAmount time) {
        this.playerUniqueId = playerUniqueId;
        this.startLocation = startLocation;
        this.destinationLocation = destinationLocation;
        this.teleportMoment = Instant.now().plus(time);

        this.result = new CompletableFuture<>();
    }

    public CompletableFuture<TeleportResult> getResult() {
        return this.result;
    }

    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    public Position getStartLocation() {
        return this.startLocation;
    }

    public Position getDestinationLocation() {
        return this.destinationLocation;
    }

    public Instant getTeleportMoment() {
        return this.teleportMoment;
    }
}
