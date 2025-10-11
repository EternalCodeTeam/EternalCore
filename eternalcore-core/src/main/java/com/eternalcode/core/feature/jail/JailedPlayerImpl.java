package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import org.bukkit.Location;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class JailedPlayerImpl implements JailedPlayer {

    private final UUID playerUUID;
    private final Instant detainedAt;
    private final Duration prisonTime;
    private final String detainedBy;
    private final Position lastPosition;

    public JailedPlayerImpl(UUID playerUUID, Instant detainedAt, Duration prisonTime, String detainedBy, Location lastLocation) {
        this.playerUUID = playerUUID;
        this.detainedAt = detainedAt;
        this.prisonTime = prisonTime;
        this.detainedBy = detainedBy;
        this.lastPosition = PositionAdapter.convert(lastLocation);
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
        return PositionAdapter.convert(this.lastPosition);
    }

    @Override
    public boolean isPrisonExpired() {
        return this.detainedAt.plus(this.prisonTime).isBefore(Instant.now());
    }

    @Override
    public Instant releaseTime() {
        return Instant.now().plus(this.prisonTime);
    }

    @Override
    public Duration remainingTime() {
        return Duration.between(Instant.now(), this.detainedAt.plus(this.prisonTime));
    }
}
