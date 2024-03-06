package com.eternalcode.core.feature.jail;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Prisoner {

    private final UUID player;
    private final Instant detainedAt;
    private final Duration prisonTime;
    private final UUID lockedUpByWho;

    public Prisoner(@NotNull UUID player, @NotNull Instant detainedAt, @NotNull Duration prisonTime, @NotNull UUID lockedUpByWho) {
        this.player = player;
        this.detainedAt = detainedAt;
        this.prisonTime = prisonTime;
        this.lockedUpByWho = lockedUpByWho;
    }
    public UUID getUuid() {
        return this.player;
    }

    public Instant getDetainedAt() {
        return this.detainedAt;
    }

    public Duration getDuration() {
        return this.prisonTime;
    }

    public UUID getDetainedBy() {
        return this.lockedUpByWho;
    }

    public boolean isReleased() {
        return this.detainedAt.plus(this.prisonTime).isBefore(Instant.now());
    }

    public Duration getReleaseTime() {
        return Duration.between(Instant.now(), this.detainedAt.plus(this.prisonTime));
    }

    public Duration getRemainingTime() {
        return Duration.between(Instant.now(), this.detainedAt.plus(this.prisonTime));
    }
}
