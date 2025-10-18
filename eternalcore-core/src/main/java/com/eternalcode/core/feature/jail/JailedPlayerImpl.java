package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.bukkit.Location;

public class JailedPlayerImpl implements JailedPlayer {

    private final UUID playerUUID;
    private final Instant detainedAt;
    private final Duration prisonTime;
    private final String detainedBy;
    private final Position lastPosition;

    public JailedPlayerImpl(
        UUID playerUUID,
        Instant detainedAt,
        Duration prisonTime,
        String detainedBy,
        Location lastLocation) {
        if (playerUUID == null) {
            throw new IllegalArgumentException("Player UUID cannot be null");
        }
        if (detainedAt == null) {
            throw new IllegalArgumentException("Detained at cannot be null");
        }
        if (prisonTime == null || prisonTime.isNegative() || prisonTime.isZero()) {
            throw new IllegalArgumentException("Prison time must be positive");
        }
        if (detainedBy == null || detainedBy.isBlank()) {
            throw new IllegalArgumentException("Detained by cannot be null or blank");
        }

        this.playerUUID = playerUUID;
        this.detainedAt = detainedAt;
        this.prisonTime = prisonTime;
        this.detainedBy = detainedBy;
        this.lastPosition = lastLocation != null ? PositionAdapter.convert(lastLocation) : null;
    }

    @Override
    public UUID playerUniqueId() {
        return this.playerUUID;
    }

    @Override
    public Instant detainedAt() {
        return this.detainedAt;
    }

    @Override
    public Duration prisonTime() {
        return this.prisonTime;
    }

    @Override
    public String detainedBy() {
        return this.detainedBy;
    }

    @Override
    public Location lastLocation() {
        return this.lastPosition != null ? PositionAdapter.convert(this.lastPosition) : null;
    }

    @Override
    public boolean isPrisonExpired() {
        return this.detainedAt.plus(this.prisonTime).isBefore(Instant.now());
    }

    @Override
    public Instant releaseTime() {
        return this.detainedAt.plus(this.prisonTime);
    }

    @Override
    public Duration remainingTime() {
        return Duration.between(Instant.now(), this.detainedAt.plus(this.prisonTime));
    }
}
