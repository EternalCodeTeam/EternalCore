package com.eternalcode.core.feature.jail;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Prisoner {

    private final UUID player;
    private final Instant detainedAt;
    private final Duration prisonTime;
    private final String detainedBy;

    public Prisoner(
        @NotNull UUID player,
        @NotNull Instant detainedAt,
        @NotNull Duration prisonTime,
        @NotNull String lockedUpBy
    ) {
        this.player = player;
        this.detainedAt = detainedAt;
        this.prisonTime = prisonTime;
        this.detainedBy = lockedUpBy;
    }
    
    public UUID getPlayerUniqueId() {
        return this.player;
    }

    public Instant getDetainedAt() {
        return this.detainedAt;
    }

    public Duration getPrisonTime() {
        return this.prisonTime;
    }

    public String getDetainedBy() {
        return this.detainedBy;
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
