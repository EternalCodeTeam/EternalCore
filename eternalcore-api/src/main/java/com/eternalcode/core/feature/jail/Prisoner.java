package com.eternalcode.core.feature.jail;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Prisoner {

    private final UUID player;
    private final String reason;
    private final Instant detainedAt;
    private final Duration prisonTime;
    private final UUID lockedUpByWho;

    public Prisoner(@NotNull UUID player, @NotNull String reason, @NotNull Instant detainedAt, @NotNull Duration prisonTime, @NotNull UUID lockedUpByWho) {
        this.player = player;
        this.reason = reason;
        this.detainedAt = detainedAt;
        this.prisonTime = prisonTime;
        this.lockedUpByWho = lockedUpByWho;
    }
    public UUID getUuid() {
        return this.player;
    }

    public String getReason() {
        return this.reason;
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
}
