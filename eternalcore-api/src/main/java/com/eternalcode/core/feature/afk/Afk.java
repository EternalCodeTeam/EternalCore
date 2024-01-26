package com.eternalcode.core.feature.afk;

import java.time.Instant;
import java.util.UUID;

public class Afk {

    private final UUID player;
    private final AfkReason afkReason;
    private final Instant start;

    Afk(UUID player, AfkReason afkReason, Instant start) {
        this.player = player;
        this.afkReason = afkReason;
        this.start = start;
    }

    /**
     * Returns the afk reason.
     *
     * @return the afk reason
     */
    public AfkReason getAfkReason() {
        return this.afkReason;
    }

    /**
     * Returns the afk start time.
     *
     * @return the afk start time
     */
    public Instant getStart() {
        return this.start;
    }

    /**
     * Returns the player uniqueId.
     *
     * @return the player unique identifier
     */
    public UUID getPlayer() {
        return this.player;
    }

}
