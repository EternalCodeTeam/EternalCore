package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class JailedPlayer {

    private final UUID player;
    private final Instant detainedAt;
    private final Duration prisonTime;
    private final String detainedBy;
    private final Position lastPosition;

    public JailedPlayer(UUID player, Instant detainedAt, Duration prisonTime, String detainedBy, Position lastPosition) {
        this.player = player;
        this.detainedAt = detainedAt;
        this.prisonTime = prisonTime;
        this.detainedBy = detainedBy;
        this.lastPosition = lastPosition;
    }
    
    public UUID getPlayerUniqueId() {
        return this.player;
    }

    public Instant getDetainedAt() {
        return this.detainedAt;
    }

    public String getDetainedBy() {
        return this.detainedBy;
    }

    public Duration getPrisonTime() {
        return this.prisonTime;
    }

    public Position getLastPosition() {
        return this.lastPosition;
    }

    public boolean isPrisonExpired() {
        return this.detainedAt.plus(this.prisonTime).isBefore(Instant.now());
    }

    public Instant getReleaseTime() {
        return Instant.now().plus(this.prisonTime);
    }

    public Duration getRemainingTime() {
        return Duration.between(Instant.now(), this.detainedAt.plus(this.prisonTime));
    }
}
