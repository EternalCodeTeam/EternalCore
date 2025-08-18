package com.eternalcode.core.feature.teleport;

import com.eternalcode.commons.bukkit.position.Position;

import com.eternalcode.core.feature.teleport.config.TeleportMessages;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Teleport {

    private final UUID playerUniqueId;
    private final Position startLocation;
    private final Position destinationLocation;
    private final Instant teleportMoment;
    private final TeleportMessages messages;

    private final CompletableFuture<TeleportResult> result;

    Teleport(
        UUID playerUniqueId,
        Position startLocation,
        Position destinationLocation,
        TemporalAmount time,
        TeleportMessages messages
    ) {
        this.playerUniqueId = playerUniqueId;
        this.startLocation = startLocation;
        this.destinationLocation = destinationLocation;
        this.teleportMoment = Instant.now().plus(time);
        this.messages = messages;

        this.result = new CompletableFuture<>();
    }

    public CompletableFuture<TeleportResult> getResult() {
        return this.result;
    }

    public void completeResult(TeleportResult result) {
        if (this.result.isDone()) {
            throw new IllegalStateException("Teleport result already completed");
        }

        this.result.complete(result);
    }

    public boolean isCancelled() {
        return this.result.isDone() && this.result.getNow(null) != TeleportResult.SUCCESS;
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

    public TeleportMessages messages() {
        return this.messages;
    }
}
